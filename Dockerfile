FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -DskipTests
FROM eclipse-temurin:17-alpine
COPY --from=build /target/credeefy-0.0.1-SNAPSHOT.jar credeefy.jar
ENTRYPOINT ["java","-Dspring.profiles.active=render","-jar","credeefy.jar"]
EXPOSE 9191