# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | student-service | docker-compose: postgres, rabbitmq, config-server, eureka-server. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Spring Boot image with SERVICE_NAME=student-service. | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — 8888; config-data/student-service.yml.
- **Netflix Eureka** — 8761.
- **Spring Boot Admin** — 8081.

### Data & messaging

- **PostgreSQL** — student_db.
- **RabbitMQ** — AMQP; Compose defaults guest unless overridden.

### Student Service runtime

- **Spring Boot** — Port 8083 default.
- **Actuator** — Tighten in production.

## Docker configuration

### student-service (multi-stage)

Root Dockerfile; SERVICE_NAME selects module.

```yaml
Fat JAR; see repository Dockerfile.
```

## Additional notes

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **Backup student_db** for compliance and DR.  
- Monitor **Rabbit** consumer lag during registration peaks.

