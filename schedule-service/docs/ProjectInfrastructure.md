# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Compose service key | schedule-service | postgres + rabbitmq + config + eureka |

## Cloud / runtime

| Name | Purpose |
| --- | --- |
| Container | Spring Boot from Dockerfile (`SERVICE_NAME=schedule-service`) |

## Deployment layers

### Platform

- **Config Server** :8888  
- **Eureka** :8761  
- **Spring Boot Admin** :8081  

### Data & messaging

- **PostgreSQL** — `schedule_db`  
- **RabbitMQ** — `5672` (management `15672` in Compose)  

### Service

- **8086** default mapped port  
- **Actuator** — limit exposure in prod  

## Docker

Monorepo **Dockerfile** builds the schedule module via **`SERVICE_NAME`**.

## Notes

- Size **Rabbit** connections and **publisher** settings for peak registration.  
- Backup **schedule_db** around term changes.  
