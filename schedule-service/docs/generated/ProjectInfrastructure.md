# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | schedule-service | docker-compose: postgres, rabbitmq, config-server, eureka-server. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Spring Boot image with SERVICE_NAME=schedule-service. | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — 8888; config-data/schedule-service.yml.
- **Netflix Eureka** — 8761.
- **Spring Boot Admin** — 8081.

### Data & messaging

- **PostgreSQL** — schedule_db — groups, slots, assignments.
- **RabbitMQ** — AMQP5672; management UI 15672 in Compose.

### Schedule Service runtime

- **Spring Boot** — Port 8086 host mapping by default.
- **Actuator** — Tighten exposure in production.

## Docker configuration

### schedule-service (multi-stage)

Root Dockerfile with SERVICE_NAME build arg.

```yaml
Fat JAR; see repository Dockerfile.
```

## Additional notes

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Schedule relies on **PostgreSQL** plus **RabbitMQ** alongside shared platform services.

## Notes

- Tune **Rabbit** prefetch and **publisher confirms** under load.  
- Back up **schedule_db** before term rollover or bulk migrations.

