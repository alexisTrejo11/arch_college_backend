# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Highlights

| Aspect | Detail |
| --- | --- |
| **Compose service** | `grade-service` |
| **Dependencies** | PostgreSQL, MongoDB, RabbitMQ, Config Server, Eureka |
| **Port** | **8088** (default) |
| **Config** | `config-data/grade-service.yml` |

## Layers

- **Platform:** Config **8888**, Eureka **8761**, Admin **8081**  
- **Data:** **`grade_db`** (Postgres + Mongo URI), **RabbitMQ**  
- **Runtime:** Spring Boot + Actuator  

## Docker

Root **Dockerfile** with **`SERVICE_NAME=grade-service`**.

## Notes

Backup **`grade_db`** on both engines if both hold production data; tune **Rabbit** consumers for history refresh latency.
