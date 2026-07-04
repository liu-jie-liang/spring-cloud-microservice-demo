# Demo 核心业务模块

Spring Cloud 微服务全栈 Demo 的核心业务模块，集成了缓存、多数据源、服务调用、日志、邮件等功能。

## 核心功能

- **双缓存策略**：Caffeine 本地缓存 + Redis 分布式缓存，通过 `@DoubleCache` 注解驱动
- **缓存同步**：基于 Spring Cloud Bus + RabbitMQ 的集群缓存失效广播
- **双数据源**：MySQL + MariaDB 独立数据源，各自完整的 Druid 连接池和 MyBatis SqlSessionFactory
- **服务调用**：OpenFeign 声明式调用 + Resilience4j 断路器 + LoadBalanced RestTemplate
- **AOP 操作日志**：`@Log` 注解自动记录方法调用到 `syslog` 表
- **邮件发送**：支持纯文本、MIME（内嵌图片+附件）、Thymeleaf 模板三种模式
- **全局异常处理**：统一拦截 `InternalErrorException` 和 `NotLoginException`
- **请求计时**：`RequestTimingInterceptor` 记录每个请求耗时
- **XSS 防护**：Mica-XSS 自动转义输入
- **配置加密**：Jasypt 加密敏感配置
- **Jackson 序列化**：时区感知的 Date/Long 自定义序列化器
- **自定义枚举校验**：`@Enum` 注解校验字段值是否在枚举范围内
- **API 文档**：SpringDoc OpenAPI（Swagger UI）

---

## 双缓存策略（Caffeine + Redis + Bus）

这是本模块最核心的技术实现，通过注解驱动 + AOP 切面实现本地缓存与分布式缓存的无缝协作。

### 架构

```
请求 → @DoubleCache 注解方法
         ↓
    CacheAspect 切面拦截
         ↓
    ┌─ FULL（读）：Caffeine → Redis → DB → 回写 Redis
    ├─ PUT（写） ：写 Redis → Bus 广播 → 各节点清除本地 Caffeine
    └─ DELETE（删）：延迟双删 Redis → Bus 广播 → 1s 后再删 → 再广播
```

### 三种缓存类型

| 类型 | 说明 | 流程 |
|------|------|------|
| `FULL` | 读写穿透 | 先查 Caffeine → 未命中查 Redis → 未命中查 DB 并回写 Redis |
| `PUT` | 写入缓存 | 写入 Redis → 通过 Bus 广播事件 → 集群所有节点清空对应 Caffeine key |
| `DELETE` | 延迟双删 | 删除 Redis → Bus 广播失效 → 等待 1 秒 → 再次删除 Redis → 再次广播 |

### `@DoubleCache` 注解

```java
@DoubleCache(prefix = "student", key = "#sno", expire = 300, type = CacheType.FULL)
public Student getStudent(String sno) {
    return studentMapper.selectBySno(sno);
}
```

**参数说明：**

| 参数 | 说明 | 示例 |
|------|------|------|
| `prefix` | 缓存 key 前缀，用于区分不同业务 | `"student"` |
| `key` | 缓存 key，支持 SpringEL 表达式 | `"#sno"`、`"#student.sno"` |
| `expire` | Redis 过期时间（秒），默认 300 | `600` |
| `type` | 缓存操作类型 | `FULL` / `PUT` / `DELETE` |

### 缓存同步机制（Spring Cloud Bus）

当缓存发生写入或删除时，通过 RabbitMQ 消息总线广播 `LocalCacheSyncEvent` 事件：

1. **写操作（PUT）**：写入 Redis 后，构造 `LocalCacheSyncEvent(key, value)` 广播给所有节点
2. **删操作（DELETE）**：删除 Redis 后，广播 `LocalCacheSyncEvent(key, null)` → 各节点将 value 为 null 的 key 从本地 Caffeine 清除

**为什么用延迟双删？** 避免并发场景下的缓存不一致：先删缓存 → 等待数据库主从同步（1s） → 再删一次，确保从库的旧数据不会因并发读而被重新写入缓存。

### 手动操作

```bash
# 手动触发本地缓存同步
curl -X POST http://localhost:8081/syncLocalCache

# 查看本地缓存内容
curl http://localhost:8081/getLocalCache
```

### 配置开关

| 配置项 | 配置文件 | 说明 |
|--------|---------|------|
| `cache.caffeine.enable=true` | `CaffeineConfig` 条件装配 | 开启 Caffeine 本地缓存（128 初始容量，1024 最大，60s 访问过期） |
| `cache.redis.enable=true` | `application-redis.properties` | 开启 Spring Cache Redis |
| `cache.redisbak.enable=true` | `application-redisbak.yml` | 开启独立 RedisTemplate（Fastjson2 序列化，提供 Value/Hash/List/Set/ZSet 操作） |

---

## 双数据源（MySQL + MariaDB）

项目同时连接 MySQL 和 MariaDB，两个数据源完全独立，各自拥有完整的 Druid 连接池、事务管理器和 MyBatis SqlSessionFactory。

### 架构

```
                    ┌─────────────────────────┐
                    │     DemoApplication      │
                    │  (排除自动数据源装配)      │
                    └──────────┬──────────────┘
                               │
              ┌────────────────┴────────────────┐
              │                                  │
    ┌─────────▼──────────┐          ┌───────────▼─────────┐
    │ MysqlDatasource     │          │ MariadbDatasource    │
    │   Config             │          │   Config              │
    │ ┌─────────────────┐ │          │ ┌───────────────────┐ │
    │ │ DataSource       │ │          │ │ DataSource         │ │
    │ │ (Druid, :3308)   │ │          │ │ (Druid, :3306)     │ │
    │ ├─────────────────┤ │          │ ├───────────────────┤ │
    │ │ TransactionMgr   │ │          │ │ TransactionMgr     │ │
    │ ├─────────────────┤ │          │ ├───────────────────┤ │
    │ │ SqlSessionFactory│ │          │ │ SqlSessionFactory  │ │
    │ └────────┬────────┘ │          │ └─────────┬─────────┘ │
    └──────────┼──────────┘          └───────────┼───────────┘
               │                                  │
    ┌──────────▼──────────┐          ┌───────────▼──────────┐
    │ Mapper: mysql/       │          │ Mapper: mariadb/      │
    │ StudentMysqlMapper   │          │ StudentMariadbMapper  │
    │                      │          │ SysLogMapper          │
    │                      │          │ TestMapper            │
    └─────────────────────┘          └──────────────────────┘
```

### 隔离方式

| 隔离层面 | 说明 |
|---------|------|
| **Mapper 包路径** | `com.example.demo.mapper.mysql` 和 `com.example.demo.mapper.mariadb` 分别扫描 |
| **XML 路径** | `classpath:mapper/mysql/*.xml` 和 `classpath:mapper/mariadb/*.xml` |
| **事务管理器** | 各自独立的 `DataSourceTransactionManager`，不可混用 |
| **条件装配** | `@ConditionalOnProperty(name = "druid.mysql.enable")` / `druid.mariadb.enable` |

### 配置开关

| 数据源 | Profile | 配置文件 | 端口 | 开关属性 |
|--------|---------|---------|------|---------|
| MariaDB | `mariadb` | `application-mariadb.yml` | 3306 | `druid.mariadb.enable=true` |
| MySQL | `mysql` | `application-mysql.yml` | 3308 | `druid.mysql.enable=true` |
| MySQL 备 | `mysqlbak` | `application-mysqlbak.properties` | - | `druid.mysqlbak.enable=true` |

### 数据库表

| 数据源 | 表名 | 说明 |
|--------|------|------|
| MariaDB | `student` | 学生表（sno, sname, ssex） |
| MariaDB | `test` | 测试计数表 |
| MariaDB | `syslog` | 操作日志表 |
| MySQL | `student` | 学生表（同上结构） |
| MySQL | `test` | 测试计数表 |

---

## Feign 服务调用 + 断路器

演示三种微服务间调用方式，配合 Resilience4j 实现熔断降级。

### 调用方式对比

| 方式 | 实现类 | 调用路径 | 断路器 |
|------|--------|---------|--------|
| Feign 直连 | `ServerInfoClient` | `demo → service-provider`（直连） | 无 |
| Feign 经网关 | `ServerInfo2Client` | `demo → gateway → service-provider` | 有 fallback |
| RestTemplate | `LoadBalancerConfig` | 通过 `@LoadBalanced RestTemplate` 按服务名调用 | 无 |

### ServerInfo2Client 断路器

```java
@FeignClient(name = "gateway", path = "/service-provider", fallback = ServerInfo2ClientFallback.class)
public interface ServerInfo2Client {
    @GetMapping("/getPort")
    String getPort();
}

@Component
class ServerInfo2ClientFallback implements ServerInfo2Client {
    @Override
    public String getPort() {
        return "circuit breaker feign";  // 熔断时返回降级文案
    }
}
```

断路器配置在 `application-feign.yml`，基于 Resilience4j，支持超时和熔断规则。

### API 示例

```bash
# 测试 Feign 直连
curl http://localhost:8081/server-info/port

# 测试 Feign 经网关（含断路器）
curl http://localhost:8081/server-info-gateway/port

# 测试 RestTemplate 调用
curl http://localhost:8081/rest-template/port
```

---

## API 速查

| 功能 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 学生分页 | GET | `/test/students?page=1&size=10` | 分页查询学生列表 |
| 按学号查询 | GET | `/test/student/{sno}` | 单条查询（走双缓存） |
| 新增学生 | POST | `/test/student` | JSON Body: `{"sno":"001","sname":"张三","ssex":"M"}` |
| 更新学生 | PUT | `/test/student` | JSON Body 同上 |
| 删除学生 | DELETE | `/test/student/{sno}` | 按学号删除（走延迟双删） |
| 操作日志 | GET | `/test/syslog?page=1&size=10` | 分页查询操作日志 |
| 发送文本邮件 | POST | `/mail/sendSimple?to=xxx@qq.com&subject=test` | 纯文本邮件 |
| 发送 MIME 邮件 | POST | `/mail/sendMime?to=xxx@qq.com&subject=test` | 带图片+附件的邮件 |
| 发送模板邮件 | POST | `/mail/sendTemplate?to=xxx@qq.com&subject=test` | Thymeleaf 模板邮件 |
| 缓存同步 | POST | `/syncLocalCache` | 手动广播缓存失效事件 |
| 查看本地缓存 | GET | `/getLocalCache` | 查看 Caffeine 缓存内容 |
| 服务端口 | GET | `/server-info/port` | Feign 直连获取端口 |
| 服务端口(网关) | GET | `/server-info-gateway/port` | Feign 经网关获取端口 |
| Swagger UI | GET | `/web/swagger-ui` | API 在线文档 |

---

## 配置文件

### Profile 激活关系

`application.properties` 中默认激活的 Profile：

```
dev,mariadb,mysqlbak,redisbak,timezone,mail,xss,bus,feign,redis
```

### 配置全量表

#### 基础配置

| 文件 | 作用 |
|------|------|
| `application.properties` | Profile 激活、Spring Boot Admin、Thymeleaf、日志级别 |
| `application.yml` | Eureka 注册、Config 配置、Druid 监控、SpringDoc、Actuator |
| `bootstrap.yml` | 引导配置（端口、应用名、Eureka/Config 地址），含 4 套 Profile（dev/dev2/provider1/provider2） |

#### 数据源

| 文件 | Profile | 作用 |
|------|---------|------|
| `application-mariadb.yml` | `mariadb` | MariaDB Druid 连接池（端口 3306） |
| `application-mysql.yml` | `mysql` | MySQL Druid 连接池（端口 3308） |
| `application-mysqlbak.properties` | `mysqlbak` | MySQL 备用数据源 |
| `application-mybatis.yml` | `mybatis` | MyBatis 驼峰命名等配置（默认未激活） |
| `application-mybatisplus.yml` | `mybatisplus` | MyBatis-Plus SQL 日志配置（默认未激活） |

#### 缓存

| 文件 | Profile | 作用 |
|------|---------|------|
| `application-redis.properties` | `redis` | Spring Cache Redis（基于 `@Cacheable` 等注解） |
| `application-redisbak.yml` | `redisbak` | 独立 RedisTemplate（Fastjson2 序列化，提供各数据类型操作） |

#### 消息与服务调用

| 文件 | Profile | 作用 |
|------|---------|------|
| `application-bus.yml` | `bus` | RabbitMQ 消息总线（缓存同步、配置刷新） |
| `application-feign.yml` | `feign` | Feign + Resilience4j 断路器配置 |

#### 功能扩展

| 文件 | Profile | 作用 |
|------|---------|------|
| `application-mail.properties` | `mail` | QQ SMTP 邮件配置 |
| `application-xss.yml` | `xss` | Mica-XSS 防护配置 |
| `application-timezone.yml` | `timezone` | 时区配置 |
| `application-prod.properties` | `prod` | 生产环境端口 8081（默认未激活） |

#### 日志

| 文件 | 作用 |
|------|------|
| `log4j2-spring.xml` | Log4j2 配置（控制台彩色输出 + 按级别滚动文件） |

---

## 启动

> 启动前请确保 Eureka、数据库、Redis、RabbitMQ 等基础服务已就绪，详见根 README。

### 典型场景

```bash
cd demo

# 场景1：最小启动（仅 Eureka 客户端，不含数据库和缓存）
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# 场景2：MySQL + Redis 缓存
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,mysql,redis

# 场景3：全功能启动（默认所有 Profile）
./mvnw spring-boot:run
```

启动后访问：
- 应用：http://localhost:8081
- Swagger UI：http://localhost:8081/web/swagger-ui
- Druid 监控：http://localhost:8081/web/druid
