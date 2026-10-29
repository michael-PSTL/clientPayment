
# Etapa 1: Compilar o projeto
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .

COPY src ./src

RUN mvn clean package -DskipTests

# Etapa 2: Executar o Spring Boot
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/Clientes-Ecommerce-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
