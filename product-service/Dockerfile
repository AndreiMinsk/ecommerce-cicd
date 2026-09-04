FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY product-service/pom.xml product-service/
COPY order-service/pom.xml order-service/
COPY eureka-server/pom.xml eureka-server/
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests -pl product-service -am

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/product-service/target/product-service.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]