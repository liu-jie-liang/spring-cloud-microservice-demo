# Eureka 服务注册中心

基于 Spring Cloud Netflix Eureka 的服务注册与发现中心。

## 双节点高可用

| 节点 | 端口 | Hostname | Profile |
|------|------|----------|---------|
| eureka1 | 8761 | eureka1 | dev1 |
| eureka2 | 8762 | eureka2 | dev2 |

两个节点互相注册，形成集群。客户端只需注册到其中任意一个节点即可。

自我保护模式已关闭（`eureka.server.enable-self-preservation=false`），适用于开发环境。

## 安全认证

基于 Spring Security 基础认证，用户名/密码：`root/root`。WebSecurityConfig 已禁用 CSRF 保护，避免对 `/eureka/**` 路径的请求被拦截。

## 配置说明

| 配置文件 | 作用 |
|---------|------|
| `application.properties` | 通用配置（安全认证、自保护模式） |
| `application-dev1.properties` | 节点1 配置（端口 8761） |
| `application-dev2.properties` | 节点2 配置（端口 8762） |

## 启动

双节点模式需要先配置 hosts：

```bash
# /etc/hosts
127.0.0.1 eureka1
127.0.0.1 eureka2
```

```bash
# 单节点
./mvnw spring-boot:run

# 双节点（需先配置 hosts）
./mvnw spring-boot:run -Dspring.profiles.active=dev1  # 终端1
./mvnw spring-boot:run -Dspring.profiles.active=dev2  # 终端2
```

启动后访问 http://localhost:8761 查看 Eureka Dashboard。
