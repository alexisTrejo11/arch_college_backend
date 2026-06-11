# Schedule Service

Class groups (obligatory/elective): create, query, attach teachers, adjust capacity, and emit scheduling-related integration events over RabbitMQ.

| Field | Value |
| --- | --- |
| Project ID | architecture-college-schedule |
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
- PostgreSQL (schedule_db)
- Docker / Docker Compose

## Additional notes

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. **`ProjectDocsModel`** is split across sibling Obsidian files.

Uses **Spring Cloud Config**, **Eureka**, **Spring Boot Admin**, **PostgreSQL** for `schedule_db`, and **RabbitMQ** for asynchronous integration (per platform wiring).

