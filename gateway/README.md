# Spring Cloud Gateway 网关

基于 Spring Cloud Gateway 的 API 网关服务。

## 核心功能

- **路由转发**：根据路由规则将请求转发到对应的微服务
- **路径重写**：`/api/demo/**` → `/**`
- **负载均衡**：通过 Eureka 发现服务实例，自动负载均衡
- **断路器**：集成 Resilience4j，支持熔断降级
- **请求头注入**：可自定义添加请求头（如版本标记）

## 配置说明

| 配置文件 | 作用 |
|---------|------|
| `application.yml` | 主配置（Eureka 注册、服务发现） |
| `application-feign.yml` | Resilience4j 断路器配置 |
| `bootstrap.yml` | 引导配置（端口 8101/8102，Eureka/Config 地址） |

默认端口 8101，可通过 `bootstrap.yml` 切换 Profile 使用 8102。

## 路由规则

路由规则通过 Config Server 动态下发，配置文件位于 `SpringCloudConfig/gateway/gateway-dev.yml`，包括：

- URI 转发
- 路径重写（StripPrefix）
- 断路器配置
- 自定义过滤器（如 AddRequestHeader）

## 启动

```bash
cd gateway
./mvnw spring-boot:run
```
