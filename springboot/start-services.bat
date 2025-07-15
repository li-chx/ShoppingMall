@echo off
echo 启动购物商城微服务系统...
echo.

echo 1. 启动用户服务 (端口: 8081)
start "User Service" cmd /k "cd /d %~dp0 && mvn -pl user-service spring-boot:run"

echo 2. 启动商品服务 (端口: 8082)
start "Goods Service" cmd /k "cd /d %~dp0 && mvn -pl goods-service spring-boot:run"

echo 3. 启动订单服务 (端口: 8083)
start "Order Service" cmd /k "cd /d %~dp0 && mvn -pl order-service spring-boot:run"

echo 4. 启动商家服务 (端口: 8084)
start "Business Service" cmd /k "cd /d %~dp0 && mvn -pl business-service spring-boot:run"

echo 5. 启动网关服务 (端口: 8080)
timeout /t 10 /nobreak > nul
start "Gateway Service" cmd /k "cd /d %~dp0 && mvn -pl gateway-service spring-boot:run"

echo.
echo 所有服务启动完成！
echo 网关地址: http://localhost:8080
echo.
pause 