# Spring Cloud 微服务全栈 Demo

基于 **Spring Cloud 2021.0.8** + **Spring Boot 2.7.x** + **Java 17** 构建的微服务全栈演示项目，涵盖服务注册发现、配置中心、API 网关、声明式调用、多数据源、多级缓存、操作日志、邮件服务等企业级常用技术栈。

## 项目结构

```
Zoom/
├── eureka/                  # 服务注册中心 (Eureka Server，支持双节点高可用)
├── config/                  # 配置中心 (Spring Cloud Config Server + Bus)
├── gateway/                 # API 网关 (Spring Cloud Gateway)
├── demo/                    # 核心业务模块 (可按不同 profile 启动为不同角色)
├── SpringCloudConfig/       # 配置中心的 Git 数据源（本地配置文件仓库）
├── .gitignore
├── LICENSE
└── README.md
```

## 架构图

```
                          ┌─────────────────┐
                          │  Eureka Server  │  (双节点: 8761 / 8762)
                          │ 服务注册与发现    │
                          └──────┬──────────┘
                                 │ 注册/发现
       ┌─────────────────────────┼─────────────────────────┐
       │                         │                         │
       ▼                         ▼                         ▼
┌──────────────┐       ┌──────────────┐        ┌──────────────┐
│  Config      │       │   Gateway    │        │   Demo       │
│  Server      │       │   网关        │        │  业务服务     │
│  (配置中心)   │       │  (路由转发)   │        │  (service/    │
│              │       │              │        │   client)    │
└──────┬───────┘       └──────┬───────┘        └──────┬───────┘
       │                      │                       │
       ▼                      ▼                       ▼
┌──────────────┐       ┌──────────────┐        ┌──────────────┐
│ SpringCloud  │       │  Resilience  │        │  MySQL/MariaDB│
│ Config Repo  │       │  4j 断路器    │        │  Redis/      │
│ (Git 本地)    │       │              │        │  Caffeine    │
└──────────────┘       └──────────────┘        │  RabbitMQ    │
                                                └──────────────┘
```

## 技术栈

| 技术 | 说明 | 版本 |
|------|------|------|
| Spring Boot | 基础框架 | 2.7.15 / 2.7.17 |
| Spring Cloud | 微服务框架 | 2021.0.8 |
| Netflix Eureka | 服务注册与发现 | - |
| Spring Cloud Config | 集中配置管理 | - |
| Spring Cloud Gateway | API 网关 | - |
| Spring Cloud OpenFeign | 声明式 HTTP 客户端 | - |
| Spring Cloud LoadBalancer | 客户端负载均衡 | - |
| Spring Cloud Bus | 消息总线（配置刷新 + 缓存同步）| - |
| Resilience4j | 断路器 / 限流 | - |
| MyBatis + MyBatis-Plus | ORM 框架 | 3.5.3.2 |
| Druid | 数据库连接池 | 1.2.18 |
| MySQL / MariaDB | 关系型数据库 | - |
| Redis (Lettuce) | 分布式缓存 | - |
| Caffeine | 本地缓存 | 3.1.5 |
| RabbitMQ | 消息队列 | - |
| SpringDoc OpenAPI | API 文档 (Swagger) | 1.7.0 |
| Log4j2 | 日志框架 | - |
| Spring Boot Mail + Thymeleaf | 邮件发送 | - |
| Mica XSS | XSS 防护 | 3.0.7 |
| Fastjson2 | JSON 序列化 | 2.0.42 |
| Jasypt | 配置文件加密 | 3.0.5 |

## 模块说明

### eureka - 服务注册中心

Eureka Server，支持双节点高可用部署：

- **节点1**: 端口 8761，hostname=eureka1
- **节点2**: 端口 8762，hostname=eureka2
- 两个节点互相注册，形成集群
- 启用了 Spring Security 基础认证

详见 [eureka/README.md](eureka/README.md)

### config - 配置中心

Spring Cloud Config Server，从本地 Git 仓库 (`SpringCloudConfig/`) 拉取配置：

- 支持 Bus + RabbitMQ 配置热刷新
- 注册到 Eureka，可通过服务名访问
- 启用了 Spring Security 基础认证

详见 [config/README.md](config/README.md)

### gateway - API 网关

Spring Cloud Gateway，统一入口路由转发：

- 支持路径重写（如 `/api/demo/**` → `/**`）
- 集成 Resilience4j 断路器
- 从 Eureka 做负载均衡
- 可注入自定义请求头

详见 [gateway/README.md](gateway/README.md)

### demo - 核心业务模块

项目最核心的模块，通过不同 Spring Profile 启动为不同角色（client 或 service-provider）。

核心功能包括：双缓存策略（Caffeine + Redis + Bus）、多数据源（MySQL + MariaDB）、AOP 操作日志、Feign + 断路器、邮件发送、XSS 防护等。

详见 [demo/README.md](demo/README.md)

### SpringCloudConfig - 配置仓库

Config Server 的配置数据源，按服务名分类存放各模块的配置：

```
SpringCloudConfig/
├── client/                 # demo 模块作为 client 角色时的配置
├── gateway/                # gateway 模块的配置（路由规则等）
└── service-provider/       # demo 模块作为 service-provider 角色时的配置
```

---

## 快速开始

### 环境要求

| 组件 | 版本要求 | 说明 |
|------|---------|------|
| JDK | 17+ | 编译和运行 |
| Maven | 3.8+ | 构建工具（项目自带 Maven Wrapper，可不安装） |
| MySQL | 5.7+ | 主数据库（端口 3308） |
| MariaDB | 10.x+ | 辅助数据库（端口 3306） |
| Redis | 6.x+ | 分布式缓存 |
| RabbitMQ | 3.x+ | 消息总线 |

### 1. 克隆项目

```bash
git clone https://github.com/liu-jie-liang/spring-cloud-microservice-demo.git
cd spring-cloud-microservice-demo
```

### 2. 配置环境变量

项目中的敏感信息（数据库密码、邮箱等）已全部使用占位符。启动前需要配置以下环境变量，或在配置文件中直接修改默认值：

| 环境变量 | 默认值 | 说明 |
|---------|--------|------|
| `MYSQL_PASSWORD` | `your_password` | MySQL 数据库密码 |
| `MYSQL_USERNAME` | `root` | MySQL 用户名 |
| `MYSQL_HOST` | `127.0.0.1` | MySQL 主机地址 |
| `MYSQL_PORT` | `3308` | MySQL 端口 |
| `MYSQL_DATABASE` | `test` | MySQL 数据库名 |
| `MARIADB_PASSWORD` | `your_password` | MariaDB 密码 |
| `MARIADB_USERNAME` | `root` | MariaDB 用户名 |
| `MARIADB_HOST` | `127.0.0.1` | MariaDB 主机地址 |
| `MARIADB_PORT` | `3306` | MariaDB 端口 |
| `MYSQL_BAK_USERNAME` | `root` | MySQL 备用数据源用户名 |
| `MYSQL_BAK_PASSWORD` | `your_password` | MySQL 备用数据源密码 |
| `DRUID_USERNAME` | `druid` | Druid 监控面板用户名 |
| `DRUID_PASSWORD` | `your_password` | Druid 监控面板密码 |
| `QQ_EMAIL` | `your_email@qq.com` | 发件人 QQ 邮箱 |
| `QQ_SMTP_PASSWORD` | `your_smtp_password` | QQ 邮箱 SMTP 授权码 |
| `RABBITMQ_USERNAME` | `guest` | RabbitMQ 用户名 |
| `RABBITMQ_PASSWORD` | `guest` | RabbitMQ 密码 |

**方式一：设置系统环境变量**

```bash
export MYSQL_PASSWORD=your_mysql_password
export MARIADB_PASSWORD=your_mariadb_password
# ... 其他变量
```

**方式二：直接在配置文件中修改默认值**

例如在 `demo/src/main/resources/application-mysql.yml` 中将 `${MYSQL_PASSWORD:your_password}` 改为 `${MYSQL_PASSWORD:你的真实密码}`。

### 3. 初始化数据库

执行 SQL 脚本创建表结构和测试数据：

```bash
# MySQL
mysql -u root -p < demo/src/main/resources/mapper/mysql/sql/20230915.sql

# MariaDB
mysql -u root -p < demo/src/main/resources/mapper/mariadb/sql/20230915.sql
```

### 4. 配置 hosts（Eureka 高可用需要）

如果使用 Eureka 双节点模式，需要配置 hosts：

```bash
# /etc/hosts
127.0.0.1 eureka1
127.0.0.1 eureka2
```

单节点模式不需要此步骤。

### 5. 启动顺序

按以下顺序依次启动各个模块：

```bash
# 1. 启动 Eureka Server（注册中心必须先启动）
cd eureka
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev1
# 双节点模式：再开一个终端
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev2

# 2. 启动 Config Server
cd ../config
./mvnw spring-boot:run

# 3. 启动 Gateway
cd ../gateway
./mvnw spring-boot:run

# 4. 启动 Demo（核心业务模块）
cd ../demo
# 作为 client 角色启动
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev,mariadb,redis,bus,feign,mail
# 另一个终端：作为 service-provider 角色启动（provider1 实例）
./mvnw spring-boot:run -Dspring-boot.run.profiles=provider1,mariadb,redis,bus,feign,mail
# 再一个终端：作为 service-provider 角色启动（provider2 实例）
./mvnw spring-boot:run -Dspring-boot.run.profiles=provider2,mariadb,redis,bus,feign,mail
```

### 6. 验证服务

启动后访问以下地址验证：

| 服务 | 地址 | 说明 |
|------|------|------|
| Eureka Dashboard | http://localhost:8761 | 用户名/密码: root/root |
| Druid 监控 | http://localhost:8081/web/druid | 用户名/密码: druid/your_password |
| Swagger 文档 | http://localhost:8081/web/swagger-ui | API 在线文档 |
| 网关路由测试 | http://localhost:8101/api/demo/test | 通过网关访问 demo 服务 |

---

## API 接口

### 通过网关访问

网关监听端口 8101/8102，路由规则通过 Config Server 下发：

```
http://localhost:8101/api/demo/**  →  demo 服务的 /** （路径重写）
```

各模块的完整 API 详见子模块 README：demo 模块接口速查见 [demo/README.md](demo/README.md)。

---

## Spring Profile 说明

### Demo 模块支持的 Profile

#### 运行角色

| Profile | 端口 | 角色 | 应用名 |
|---------|------|------|--------|
| `dev` | 8081 | client（配置客户端） | client |
| `dev2` | 8082 | client（配置客户端） | client |
| `provider1` | 8083 | service-provider（服务提供者） | service-provider |
| `provider2` | 8084 | service-provider（服务提供者） | service-provider |

#### 功能 Profile

| Profile | 对应配置文件 | 作用 |
|---------|-------------|------|
| `mysql` | `application-mysql.yml` | 启用 MySQL 数据源 |
| `mariadb` | `application-mariadb.yml` | 启用 MariaDB 数据源 |
| `mysqlbak` | `application-mysqlbak.properties` | 启用 MySQL 备用数据源 |
| `redis` | `application-redis.properties` | 启用 Redis 缓存 |
| `redisbak` | `application-redisbak.yml` | 启用 Redis 备用缓存 |
| `bus` | `application-bus.yml` | 启用消息总线 (RabbitMQ) |
| `feign` | `application-feign.yml` | 启用 Feign + Resilience4j 断路器 |
| `mail` | `application-mail.properties` | 启用邮件发送 |
| `mybatis` | `application-mybatis.yml` | MyBatis 驼峰命名配置 |
| `mybatisplus` | `application-mybatisplus.yml` | MyBatis-Plus 日志配置 |
| `xss` | `application-xss.yml` | 启用 XSS 防护 |
| `timezone` | `application-timezone.yml` | 时区配置 (默认 Asia/Shanghai) |
| `prod` | `application-prod.properties` | 生产环境配置 |

### 组合示例

```bash
# 最小启动（仅 MariaDB + Redis，client 角色）
./mvnw spring-boot:run -Dspring.profiles.active=dev,mariadb,redis

# 全功能启动（client 角色）
./mvnw spring-boot:run -Dspring.profiles.active=dev,mariadb,mysql,redis,bus,feign,mail,xss,timezone

# 服务提供者启动
./mvnw spring-boot:run -Dspring.profiles.active=provider1,mariadb,redis,bus,feign,mail
```

---

## 缓存策略说明

### @DoubleCache 注解

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DoubleCache {
    String prefix();                    // 缓存 key 前缀，区分不同业务
    String key();                       // 缓存 key（支持 SpringEL 表达式）
    long expire() default 3600L;        // Redis 过期时间（秒）
    CacheType type() default FULL;      // 缓存操作类型
}
```

### CacheType 枚举

| 类型 | 说明 |
|------|------|
| `FULL` | 完整读写：查询优先读缓存，写入/删除同时操作缓存和数据库 |
| `PUT` | 仅写入：执行方法后更新 Redis 和 Caffeine 缓存 |
| `DELETE` | 延迟双删：先删缓存，执行方法，延迟后再次删除缓存 |

---

## 注意事项

1. **Eureka 自保护模式已关闭**：`eureka.server.enable-self-preservation=false`，适用于开发环境
2. **日志路径**：默认输出到 `logs/` 目录（使用 Log4j2）
3. **时区**：统一使用 `Asia/Shanghai`
4. **数据库密码**：所有密码已替换为占位符，启动前请务必将环境变量或配置文件中的默认值改为真实密码
5. **QQ 邮箱 SMTP**：如使用邮件功能，需要在 [QQ 邮箱设置](https://mail.qq.com/) 中开启 SMTP 服务并获取授权码
6. **Jasypt 加密**：项目已引入 Jasypt 依赖，如需对配置文件中的密码进行加密，可使用 Jasypt Maven Plugin

---

## License

[MIT](LICENSE)
