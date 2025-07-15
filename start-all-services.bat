@echo off
echo 启动购物商城微服务系统...
echo.

REM 检查是否在正确的目录
if not exist "pom.xml" (
    echo 错误: 请在 springboot 目录下运行此脚本
    pause
    exit /b 1
)

echo 1. 启动用户服务 (端口: 8081)
start "User Service" cmd /k "cd /d %~dp0 && mvn -pl user-service spring-boot:run"

echo 等待用户服务启动...
timeout /t 15 /nobreak > nul

echo 2. 启动商品服务 (端口: 8082)
start "Goods Service" cmd /k "cd /d %~dp0 && mvn -pl goods-service spring-boot:run"

echo 等待商品服务启动...
timeout /t 10 /nobreak > nul

echo 3. 启动订单服务 (端口: 8083)
start "Order Service" cmd /k "cd /d %~dp0 && mvn -pl order-service spring-boot:run"

echo 等待订单服务启动...
timeout /t 10 /nobreak > nul

echo 4. 启动商家服务 (端口: 8084)
start "Business Service" cmd /k "cd /d %~dp0 && mvn -pl business-service spring-boot:run"

echo 等待商家服务启动...
timeout /t 10 /nobreak > nul

echo 5. 启动网关服务 (端口: 8080)
start "Gateway Service" cmd /k "cd /d %~dp0 && mvn -pl gateway-service spring-boot:run"

echo.
echo 所有服务启动完成！
echo 等待服务完全启动...
timeout /t 20 /nobreak > nul

echo.
echo 检查服务状态:
netstat -ano | findstr :808

echo.
echo 网关地址: http://localhost:8080
echo 用户服务: http://localhost:8081
echo 商品服务: http://localhost:8082
echo 订单服务: http://localhost:8083
echo 商家服务: http://localhost:8084
echo.
pause 