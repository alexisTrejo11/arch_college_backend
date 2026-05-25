# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Infrastructure metrics

| Label | Value | Icon | Description |
| --- | --- | --- | --- |
| Compose service key | account-service | ship | Defined in root `docker-compose.yml`; depends on postgres, config-server, eureka-server. |

## Cloud services

| Name | Purpose | Icon | Cost |
| --- | --- | --- | --- |
| Container runtime | Spring Boot image from monorepo Dockerfile (`SERVICE_NAME=account-service`). | docker | Environment-specific |

## Deployment layers

### Platform plane (`#eceff4`)

| Component | Icon | Description |
| --- | --- | --- |
| Spring Cloud Config | settings | Config server on port 8888. |
| Netflix Eureka | radar | Registry on 8761. |
| Spring Boot Admin | monitor | Actuator aggregation on 8081. |

### Data plane (`#d8dee9`)

| Component | Icon | Description |
| --- | --- | --- |
| PostgreSQL | database | Host database `account_db`. |
| Future MongoDB / RabbitMQ | n/a | Not used by account-service. |

### Account Service runtime (`#88c0d0`)

| Component | Icon | Description |
| --- | --- | --- |
| Spring Boot container | coffee | Port 8082 in default Compose. |
| Actuator | activity | Health/metrics — restrict in production. |

## Docker files (`DockerFile[]`)

| Service | Description | Content summary |
| --- | --- | --- |
| account-service (multi-stage) | Root Dockerfile; `SERVICE_NAME` selects Gradle module. | Executable JAR build — see repository `Dockerfile`. |

## Narrative

The service runs as a **deployed** container with PostgreSQL, and relies on the platform control plane (Config, **`Eureka`**, Admin).

### Operational notes

- Set CPU/memory limits in your orchestrator when leaving Compose.  
- Prefer managed PostgreSQL with backups in production.  
