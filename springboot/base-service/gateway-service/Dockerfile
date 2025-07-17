FROM openjdk:20-jdk
LABEL authors="lichx"
ARG SERVER_PORT=8080
ENV SERVER_PORT=${SERVER_PORT}
COPY target/gateway-service-1.0.0.jar /app.jar
EXPOSE ${SERVER_PORT}
ENTRYPOINT ["sh", "-c", "java -jar /app.jar --server.port=$SERVER_PORT"]