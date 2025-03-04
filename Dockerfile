FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/orleansstayAPI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
