# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | teacher-service | docker-compose: postgres, rabbitmq, config-server, eureka-server; JWT env from Compose. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Spring Boot image SERVICE_NAME=teacher-service. | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — 8888; config-data/teacher-service.yml + jwt.secret.key.
- **Netflix Eureka** — 8761.
- **Spring Boot Admin** — 8081.

### Data & messaging

- **PostgreSQL** — teacher_db.
- **RabbitMQ** — AMQP broker.

### Teacher Service runtime

- **Spring Boot** — Port 8084 default.
- **Actuator** — Restrict in production.

## Docker configuration

### teacher-service (multi-stage)

Root Dockerfile; SERVICE_NAME selects module.

```yaml
Fat JAR; see repository Dockerfile.
```

## Additional notes

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **`JWT_SECRET_KEY`** (or config `jwt.secret.key`) must be **identical** to **account-service** token signing.  
- Backup **teacher_db**; tune **Rabbit** for faculty import peaks if applicable.

