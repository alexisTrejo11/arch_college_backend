# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

| Aspect | Detail |
| --- | --- |
| **Compose key** | `teacher-service` |
| **Port** | **8084** (default) |
| **Deps** | PostgreSQL, RabbitMQ, Config Server, Eureka |
| **Secrets** | `JWT_SECRET_KEY` / `jwt.secret.key` aligned with **account-service** |

## Platform

Config :8888, Eureka :8761, Admin :8081.

## Data

- **teacher_db** (PostgreSQL)  
- **RabbitMQ**  

## Docker

Root **Dockerfile**, **`SERVICE_NAME=teacher-service`**.

## Notes

Never ship **default JWT** to prod; backup **teacher_db**.
