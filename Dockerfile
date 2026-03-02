FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:17-jre-alpine3.18
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
  CMD java -jar app.jar --status || exit 1

ENTRYPOINT ["java", \
  "-Dlogback.statusListenerClass=ch.qos.logback.core.status.NopStatusListener", \
  "-jar", "app.jar"]