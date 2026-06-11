# Project Overview

## Why Student Service exists

Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling.

### Pain points

- Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling.

## How the service addresses it

- **Command vs query split** — StudentCommandController handles writes; StudentQueryController optimizes reads.
- **Relational source of truth** — student_db stores canonical student aggregates under this service boundary.
- **AMQP integration** — RabbitMQ propagates events for downstream sync (as implemented in the module).

## Operational signals

- Default HTTP port 8083 in root docker-compose.yml.
- PostgreSQL student_db; RabbitMQ broker from compose env.
- Swagger UI at /swagger-ui.html when SpringDoc is enabled.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/student-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs when production assets exist.

### Student Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-student-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8083 | docker-compose host mapping |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Student Service** owns **student CRUD**, **lookups**, **professional line / modality**, and **semester completion** increments, with **messaging** for integration.

## Authoring notes

- Coordinate **professional line ids** with curriculum-service contract.  
- Document **exchange/queue** names if operators must troubleshoot Rabbit consumers.

