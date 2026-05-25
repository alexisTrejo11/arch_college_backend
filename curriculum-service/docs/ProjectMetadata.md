# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-curriculum` |
| **featured** | No |
| **name** | Curriculum Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Academic catalog: careers, areas, professional lines, obligatory and elective subjects, and subject-series relationships. |
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
- Spring Security  
- SpringDoc OpenAPI  
- PostgreSQL  
- Docker / Docker Compose  

## Context

Configuration comes from **`config-data/curriculum-service.yml`** via the Config Server. The service registers with **Eureka** and is visible in **Spring Boot Admin**. **`ProjectDocsModel`** is documented in the sibling Markdown files.
