# 购物商城微服务架构

## 项目概述

本项目是基于Spring Cloud Alibaba的微服务架构购物商城系统，将原有的单体Spring Boot应用拆分为多个微服务。

## 技术栈

- **Spring Boot 2.6.13** - 基础框架
- **Spring Cloud 2021.0.5** - 微服务框架
- **Spring Cloud Alibaba 2021.0.4.0** - 阿里微服务组件
- **Nacos** - 服务注册与配置中心
- **Sentinel** - 流量控制与熔断降级
- <!-- **Seata** - 分布式事务管理 -->
- **Spring Cloud Gateway** - API网关
- **OpenFeign** - 服务间调用
- **MyBatis Plus** - ORM框架
- **MySQL** - 数据库

## 项目结构

```
springboot/
├── pom.xml                    # 父项目配置
├── README.md                  # 架构说明
├── QUICK_START.md            # 快速启动指南
├── start-services.bat        # 启动脚本
├── stop-services.bat         # 停止脚本
├── docker-compose.yml        # Docker编排文件
├── .gitignore               # Git忽略文件
├── common-service/           # 公共模块
│   ├── pom.xml
│   └── src/main/java/com/example/common/
├── user-service/            # 用户服务 (端口: 8081)
│   ├── pom.xml
│   ├── src/main/java/com/example/user/
│   └── src/main/resources/
├── goods-service/           # 商品服务 (端口: 8082)
│   ├── pom.xml
│   ├── src/main/java/com/example/goods/
│   └── src/main/resources/
├── order-service/           # 订单服务 (端口: 8083)
│   ├── pom.xml
│   ├── src/main/java/com/example/order/
│   └── src/main/resources/
├── business-service/        # 商家服务 (端口: 8084)
│   ├── pom.xml
│   ├── src/main/java/com/example/business/
│   └── src/main/resources/
└── gateway-service/         # 网关服务 (端口: 8080)
    ├── pom.xml
    ├── src/main/java/com/example/gateway/
    └── src/main/resources/
```

## 微服务架构

### 服务列表

1. **gateway-service (8080)** - API网关服务
   - 统一入口
   - 路由转发
   - 负载均衡
   - 限流熔断

2. **user-service (8081)** - 用户服务
   - 用户管理
   - 认证授权
   - 地址管理
   - 文件上传
   - 通知管理
   - 管理员功能

3. **goods-service (8082)** - 商品服务
   - 商品管理
   - 分类管理

4. **order-service (8083)** - 订单服务
   - 订单管理
   - 购物车管理
   - 分布式事务

5. **business-service (8084)** - 商家服务
   - 商家管理

6. **common-service** - 公共模块
   - 公共组件
   - 工具类
   - 异常处理

## 启动顺序

1. 启动Nacos服务注册中心
2. 启动Sentinel控制台
<!-- 3. 启动Seata事务协调器 -->
3. 启动各个微服务（顺序不限）
4. 启动Gateway网关服务

## 接口路由

所有接口通过网关统一访问，端口为8080：

- 用户相关：`http://localhost:8080/user/**`
- 商品相关：`http://localhost:8080/goods/**`
- 分类相关：`http://localhost:8080/category/**`
- 订单相关：`http://localhost:8080/orders/**`
- 购物车相关：`http://localhost:8080/cart/**`
- 商家相关：`http://localhost:8080/business/**`
- 地址相关：`http://localhost:8080/address/**`
- 文件上传：`http://localhost:8080/file/**`
- 通知相关：`http://localhost:8080/notice/**`
- 管理员相关：`http://localhost:8080/admin/**`
- 基础接口：`http://localhost:8080/login`, `/register`, `/updatePassword`

## 配置说明

### Nacos配置
- 服务注册地址：`localhost:8848`
- 配置中心地址：`localhost:8848`

### Sentinel配置
- 控制台地址：`localhost:8080`
- 限流规则：通过Nacos配置中心动态配置

<!-- ### Seata配置
- 事务组：`my_test_tx_group`
- 注册中心：Nacos
- 配置中心：Nacos -->

## 数据库

所有服务共享同一个MySQL数据库：`shopping_mall`

## 部署说明

1. 确保MySQL、Nacos、Sentinel<!-- 、Seata -->服务正常运行
2. 修改各服务的配置文件中的数据库连接信息
3. 按顺序启动各个微服务
4. 通过网关访问系统

## 注意事项

1. 确保Nacos服务正常运行，否则服务无法注册
2. 网关服务需要最后启动，因为依赖其他服务
<!-- 3. 分布式事务需要Seata支持 -->
3. 限流熔断需要Sentinel支持

## 迁移说明

本项目已从单体Spring Boot应用成功迁移到微服务架构：

- ✅ 保持原有功能完整性
- ✅ 保持原有接口路径不变
- ✅ 前端无需修改
- ✅ 支持独立部署和扩展
- ✅ 集成现代化微服务技术栈