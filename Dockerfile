FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

COPY . .

ARG SERVICE_NAME

RUN chmod +x gradlew \
    && ./gradlew :${SERVICE_NAME}:bootJar --no-daemon -x test \
    && mkdir -p /build \
    && find ${SERVICE_NAME}/build/libs -name "*.jar" ! -name "*-plain*" -type f \
       | head -1 | xargs -I{} cp {} /build/app.jar

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=builder /build/app.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
