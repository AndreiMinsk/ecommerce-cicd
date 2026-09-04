FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY order-service/pom.xml order-service/
COPY product-service/pom.xml product-service/
COPY eureka-server/pom.xml eureka-server/
RUN mvn dependency:go-offline -B
COPY . .
RUN mvn clean package -DskipTests -pl eureka-server -am

FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/eureka-server/target/*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]