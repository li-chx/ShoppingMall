FROM openjdk:20-jdk
LABEL authors="lichx"

# 声明构建参数
ARG SERVER_PORT=8080
ARG NACOS_HOST=localhost
ARG SENTINEL_HOST=localhost
ARG SERVICE_IP=localhost

# 设置环境变量
ENV SERVER_PORT=${SERVER_PORT}
ENV NACOS_HOST=${NACOS_HOST}
ENV SENTINEL_HOST=${SENTINEL_HOST}
ENV SERVICE_IP=${SERVICE_IP}

COPY target/gateway-service-1.0.0.jar /app.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["sh", "-c", "java -jar /app.jar --server.port=$SERVER_PORT"]