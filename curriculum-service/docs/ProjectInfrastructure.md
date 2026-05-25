# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Infrastructure metrics

| Label | Value | Icon | Description |
| --- | --- | --- | --- |
| Compose service key | curriculum-service | ship | `docker-compose.yml`: postgres, config-server, eureka-server. |

## Cloud services

| Name | Purpose | Icon | Cost |
| --- | --- | --- | --- |
| Container runtime | Spring Boot image (`SERVICE_NAME=curriculum-service`) | docker | Environment-specific |

## Deployment layers

### Platform plane

| Component | Icon | Description |
| --- | --- | --- |
| Spring Cloud Config | settings | Port 8888; `config-data` mount. |
| Netflix Eureka | radar | Registry 8761. |
| Spring Boot Admin | monitor | Actuator aggregation 8081. |

### Data plane (this service)

| Component | Description |
| --- | --- |
| PostgreSQL | Database `curriculum_db`. |
| MongoDB / RabbitMQ | Not used by curriculum-service. |

### Curriculum Service runtime

| Component | Description |
| --- | --- |
| Spring Boot container | Port **8085** in default Compose. |
| Actuator | Health/metrics — restrict in production. |

## Docker files

| Service | Description |
| --- | --- |
| curriculum-service (multi-stage) | Root `Dockerfile`; `SERVICE_NAME` selects module → runnable JAR. |

## Notes

- Size **connection pools** and **Postgres** for large catalogs.  
- **TLS** and secrets at ingress/orchestrator.  
