# Spring Cloud Config 配置中心

基于 Spring Cloud Config Server 的集中配置管理服务。

## 配置来源

配置从本地 Git 仓库 (`../SpringCloudConfig/`) 拉取，按 `{application}-{profile}.yml` 规则匹配。

## 配置说明

| 配置文件 | 作用 |
|---------|------|
| `application.properties` | 通用配置（安全认证 `root/root`） |
| `application.yml` | Git 仓库配置 + Eureka 注册 |
| `application-bus.yml` | RabbitMQ 消息总线（配置热刷新） |
| `application-dev.yml` | 开发环境（端口 8081） |
| `application-dev2.yml` | 备用开发环境（端口 8082） |

## 配置刷新

修改 `SpringCloudConfig/` 下的配置文件后，通过 POST 请求触发刷新：

```bash
curl -X POST http://localhost:8080/actuator/busrefresh
```

或针对单个服务：

```bash
curl -X POST http://localhost:8080/actuator/busrefresh/demo:8081
```

## 启动

```bash
./mvnw spring-boot:run
```
