# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-schedule` |
| **featured** | No |
| **name** | Schedule Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Class groups (obligatory/elective): create, query, assign teachers, adjust capacity, and integrate via RabbitMQ. |
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
- PostgreSQL (`schedule_db`)  
- Docker / Docker Compose  

## Context

**Config Server**, **Eureka**, and **Spring Boot Admin** are shared platform components. **PostgreSQL** holds schedule data; **RabbitMQ** carries integration traffic as wired in the module.
