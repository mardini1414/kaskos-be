FROM maven:3.8.4-openjdk-17 AS builder


WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17.0.9_9-jre-ubi9-minimal

WORKDIR /app

COPY --from=builder /app/target/kaskos-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
