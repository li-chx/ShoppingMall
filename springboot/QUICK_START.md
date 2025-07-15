# 快速启动指南

## 前置条件

1. 安装Java 8+
2. 安装Maven 3.6+
3. 安装Docker和Docker Compose（可选）

## 方式一：使用Docker Compose（推荐）

### 1. 启动基础服务

```bash
# 在springboot目录下执行
docker-compose up -d
```

这将启动：
- MySQL数据库 (端口: 3306)
- Nacos服务注册中心 (端口: 8848)
- Sentinel控制台 (端口: 8080)
<!-- - Seata事务协调器 (端口: 8091) -->

### 2. 启动微服务

```bash
# 方式1：使用启动脚本
start-services.bat

# 方式2：手动启动
mvn -pl user-service spring-boot:run
mvn -pl goods-service spring-boot:run
mvn -pl order-service spring-boot:run
mvn -pl business-service spring-boot:run
mvn -pl gateway-service spring-boot:run
```

## 方式二：手动启动

### 1. 启动Nacos

下载Nacos并启动：
```bash
# 下载Nacos 2.2.3
# 解压后进入bin目录
startup.cmd -m standalone
```

### 2. 启动Sentinel

下载Sentinel Dashboard并启动：
```bash
# 下载sentinel-dashboard-1.8.6.jar
java -Dserver.port=8080 -jar sentinel-dashboard-1.8.6.jar
```

### 3. 启动Seata

下载Seata并启动：
```bash
# 下载seata-server-1.6.1
# 配置registry.conf和file.conf
seata-server.bat
```

<!-- ### 3. 启动Seata

下载Seata并启动：
```bash
# 下载seata-server-1.6.1
# 配置registry.conf和file.conf
seata-server.bat
```
-->

### 4. 启动微服务

按照上述方式启动各个微服务。

## 验证服务

### 1. 检查服务注册

访问Nacos控制台：http://localhost:8848/nacos
- 用户名：nacos
- 密码：nacos

查看服务列表，应该能看到：
- user-service
- goods-service
- order-service
- business-service
- gateway-service

### 2. 检查网关

访问网关：http://localhost:8080

### 3. 检查Sentinel

访问Sentinel控制台：http://localhost:8080
- 用户名：sentinel
- 密码：sentinel

## 接口测试

所有接口通过网关访问：

```bash
# 用户相关
GET http://localhost:8080/user/selectAll

# 商品相关
GET http://localhost:8080/goods/selectAll

# 分类相关
GET http://localhost:8080/category/selectAll

# 订单相关
GET http://localhost:8080/orders/selectAll

# 购物车相关
GET http://localhost:8080/cart/selectAll

# 商家相关
GET http://localhost:8080/business/selectAll
```

## 停止服务

```bash
# 停止微服务
stop-services.bat

# 停止Docker服务
docker-compose down
```

## 常见问题

1. **端口冲突**：确保8080、8081、8082、8083、8084、8848端口未被占用
2. **数据库连接失败**：检查MySQL服务是否启动，用户名密码是否正确
3. **服务注册失败**：检查Nacos服务是否正常启动
4. **网关路由失败**：确保所有微服务都已启动并注册到Nacos 