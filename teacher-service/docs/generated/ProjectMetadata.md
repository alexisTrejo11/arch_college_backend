# Teacher Service

Faculty lifecycle: create and delete teachers, validate account numbers, batch lookups, and RabbitMQ integration. JWT validation aligned with account-service.

| Field | Value |
| --- | --- |
| Project ID | architecture-college-teacher |
| Version | 1.0.0 |
| Language | Java |
| Framework | Spring Boot + Spring Cloud Netflix |
| Category | backend |
| Status | deployed |
| Featured | No |
| Repository | https://github.com/alexisTrejo11/architecture-college-plattform |
| Live demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Created | 2026-01-01T00:00:00.000Z |
| Updated | 2026-05-11T00:00:00.000Z |

## Tech stack

- Java 17
- Spring Boot 3.3.x
- Spring Cloud 2023.0.3
- Spring Cloud Netflix Eureka
- Spring Cloud Config
- Spring Web
- Spring Data JPA
- Spring AMQP / RabbitMQ
- Spring Security
- SpringDoc OpenAPI
- JWT (shared secret with account-service)
- PostgreSQL (teacher_db)
- Docker / Docker Compose

## Additional notes

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. Nested **`docs`** are split across companion Obsidian files.

**Config Server**, **Eureka**, **Spring Boot Admin**, **PostgreSQL** (`teacher_db`), **RabbitMQ**, and **JWT** configuration (see `config-data/teacher-service.yml` and Compose) apply to this module.

