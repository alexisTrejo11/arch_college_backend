# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | enrollment-service | docker-compose: postgres, mongodb, config-server, eureka-server (no RabbitMQ for this module by default). |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Spring Boot image with SERVICE_NAME=enrollment-service. | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — 8888; serves config-data/enrollment-service.yml.
- **Netflix Eureka** — 8761.
- **Spring Boot Admin** — 8081.

### Data plane

- **PostgreSQL** — enrollment_db — transactional enrollments.
- **MongoDB** — Document DB (e.g. enrollment_db on cluster) for preload/status.

### Enrollment Service runtime

- **Spring Boot** — Port 8087 in default Compose.
- **Actuator** — Narrow endpoint exposure in production.

## Docker configuration

### enrollment-service (multi-stage)

Root Dockerfile; SERVICE_NAME selects Gradle module.

```yaml
Runnable JAR; see repository Dockerfile.
```

## Additional notes

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Enrollment is **deployed** with **two datastores** (Postgres + Mongo) plus shared platform services.

## Notes

- Back up **both** Postgres and Mongo with RPO/RTO appropriate for registration periods.  
- Tune **Mongo connection pool** independently when preload traffic spikes.

