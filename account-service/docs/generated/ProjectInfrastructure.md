# Infrastructure

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | account-service | Defined in root docker-compose.yml; depends on postgres, config-server, eureka-server. |

## Cloud services

| Service | Purpose | Est. cost |
| --- | --- | --- |
| Container runtime | Runs the Spring Boot image built from the monorepo Dockerfile (SERVICE_NAME=account-service). | Environment-specific |

## Deployment layers

### Platform plane

- **Spring Cloud Config** — Serves config-data on port 8888.
- **Netflix Eureka** — Registry on port 8761.
- **Spring Boot Admin** — Aggregates actuator endpoints on port 8081.

### Data plane

- **PostgreSQL** — Host database account_db for this service.
- **Future MongoDB / RabbitMQ** — Not used by account-service; other platform services may use them.

### Account Service runtime

- **Spring Boot container** — Listens on 8082 in the default Compose file.
- **Actuator** — Health and metrics; restrict in production.

## Docker configuration

### account-service (multi-stage)

Root Dockerfile; build arg SERVICE_NAME selects the Gradle module.

```yaml
See repository Dockerfile — produces an executable JAR.
```

## Additional notes

# Infrastructure

Front matter matches **`InfrastructureModel`** in `docs-schema.ts`.

This service is **deployed** as one container in the platform stack, with Postgres for persistence and Config/Eureka/Admin for cross-cutting control.

## Operational notes

- Map real **CPU/memory** limits in orchestrator manifests when moving off Compose.
- Use managed **PostgreSQL** in cloud with automated backups.

