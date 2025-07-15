# Multi-stage build для gateway
FROM maven:3.8.4-openjdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY gateway/pom.xml ./gateway/
COPY server/pom.xml ./server/
RUN mvn dependency:go-offline

COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jre-slim
WORKDIR /app
COPY --from=build /app/gateway/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
