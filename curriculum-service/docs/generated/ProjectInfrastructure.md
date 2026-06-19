# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | curriculum-service | Root docker-compose.yml: depends on postgres, config-server, eureka-server. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Spring Boot image from monorepo Dockerfile with SERVICE_NAME=curriculum-service. | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — Port 8888; config-data volume mount.
- **Netflix Eureka** — Registry 8761.
- **Spring Boot Admin** — Aggregates actuator on 8081.

### Data plane (this service)

- **PostgreSQL** — Database curriculum_db for all catalog entities.
- **MongoDB / RabbitMQ** — Not used by curriculum-service; other platform services may consume them.

### Curriculum Service runtime

- **Spring Boot container** — Listens on 8085 in default Compose.
- **Actuator** — Health and metrics; narrow exposure in production.

## Docker configuration

### curriculum-service (multi-stage)

Root Dockerfile; SERVICE_NAME selects Gradle module.

```yaml
Produces runnable JAR; see repository Dockerfile.
```

## Additional notes

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Curriculum runs as a **stateless** JVM service with **PostgreSQL**; platform **Config**, **Eureka**, and **Admin** are shared.

## Notes

- Tune **connection pool** and **Postgres resources** when catalog size grows.
- **TLS** and **secrets** belong at the ingress/orchestrator, not in Git.

