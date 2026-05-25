# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-enrollment` |
| **featured** | No |
| **name** | Enrollment Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Group and student enrollments, enrollment lock rules, and Mongo-backed bulk preload jobs alongside Postgres transactions. |
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
- Spring Data MongoDB  
- Spring Security  
- SpringDoc OpenAPI  
- PostgreSQL (`enrollment_db`)  
- MongoDB  
- Docker / Docker Compose  

## Context

Configuration is served from **`config-data/enrollment-service.yml`**. The service registers with **Eureka** and appears in **Spring Boot Admin**. **`ProjectDocsModel`** is documented in the other files in this folder.
