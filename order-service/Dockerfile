FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY order-service/pom.xml order-service/
COPY product-service/pom.xml product-service/
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests -pl order-service -am

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/order-service/target/order-service.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]