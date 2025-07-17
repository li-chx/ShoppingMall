FROM openjdk:20-jdk-alpine
LABEL authors="lichx"
COPY target/gateway-service-1.0.0.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]