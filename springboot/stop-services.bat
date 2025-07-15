@echo off
echo 停止购物商城微服务系统...
echo.

echo 正在停止所有Java进程...
taskkill /f /im java.exe

echo.
echo 所有服务已停止！
echo.
pause 