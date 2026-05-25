# Infrastructure

Structured counterpart: [obsidian/ProjectInfrastructure.md](obsidian/ProjectInfrastructure.md) (`InfrastructureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Highlights

| Aspect | Detail |
| --- | --- |
| **Compose service** | `student-service` |
| **Dependencies** | PostgreSQL, RabbitMQ, Config Server, Eureka |
| **Port** | **8083** (default) |
| **Config** | `config-data/student-service.yml` |

## Layers

- **Platform:** Config **8888**, Eureka **8761**, Admin **8081**  
- **Data:** **`student_db`**, **RabbitMQ**  
- **Runtime:** Spring Boot + Actuator  

## Docker

Root **Dockerfile** with **`SERVICE_NAME=student-service`**.

## Notes

Backup **student_db**; monitor **consumer lag** on Rabbit during peaks.
