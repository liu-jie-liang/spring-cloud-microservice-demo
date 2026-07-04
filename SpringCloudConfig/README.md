# SpringCloudConfig - 配置仓库

Config Server 的本地配置数据源，按服务名和 Profile 组织配置文件。

## 目录结构

```
SpringCloudConfig/
├── client/                     # demo 模块作为 client 角色时的配置
│   ├── client-dev.yml          # 开发环境（服务版本、描述等元信息）
│   └── client-dev2.yml         # 备用开发环境配置
├── gateway/                    # gateway 模块的路由配置
│   ├── gateway-dev.yml         # 路由规则（URI 转发、路径重写、断路器、请求头注入）
│   └── gateway-dev2.yml        # 备用路由规则
└── service-provider/           # demo 模块作为 service-provider 角色时的配置
    ├── service-provider-provider1.yml    # provider1 实例配置（服务元信息）
    └── service-provider-provider2.yml    # provider2 实例配置
```

## 命名规则

Config Server 按 `{spring.application.name}-{spring.cloud.config.profile}.yml` 匹配配置文件。

例如，服务 `spring.application.name=client`，profile 为 `dev`，则匹配 `client/client-dev.yml`。
