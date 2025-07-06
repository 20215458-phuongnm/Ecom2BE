# Stage 1: build jar bằng Maven
FROM maven:3.8.4-openjdk-11 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: dùng openjdk để chạy app
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8085
CMD ["java", "-jar", "app.jar"]
