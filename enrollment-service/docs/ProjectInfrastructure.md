# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | enrollment-service | Depends on postgres, mongodb, config-server, eureka-server |

## Cloud services

| Name | Purpose |
| --- | --- |
| Container runtime | Runnable JAR from monorepo Dockerfile (`SERVICE_NAME=enrollment-service`) |

## Deployment layers

### Platform

- **Config Server** (8888)  
- **Eureka** (8761)  
- **Spring Boot Admin** (8081)  

### Data

- **PostgreSQL** — `enrollment_db`  
- **MongoDB** — document store for preload/status (Compose URI `mongodb://mongodb:27017/enrollment_db`)  

### Runtime

- **Spring Boot** on **8087** (default Compose)  
- **Actuator** — limit exposure in production  

## Docker

Root **Dockerfile** multi-stage build; **SERVICE_NAME** selects the enrollment module.

## Notes

- Backup **both** databases around registration deadlines.  
- Tune **Mongo** pool size for bulk preload independently of JDBC.  
