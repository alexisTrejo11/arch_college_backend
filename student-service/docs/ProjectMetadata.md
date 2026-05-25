# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-student` |
| **featured** | No |
| **name** | Student Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Student master data: CRUD, lookups, professional line assignment, semester progression, and RabbitMQ integration for domain events. |
| **status** | deployed |
| **createdAt** | 2026-01-01T00:00:00.000Z |
| **updatedAt** | 2026-05-11T00:00:00.000Z |

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
- PostgreSQL (`student_db`)  
- Docker / Docker Compose  

## Context

**`config-data/student-service.yml`** via Config Server. **Eureka** + **Spring Boot Admin**. **RabbitMQ** for cross-service messaging per Compose.
