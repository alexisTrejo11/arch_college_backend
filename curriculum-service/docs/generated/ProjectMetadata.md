# Curriculum Service

Academic catalog: careers, areas, professional lines, obligatory and elective subjects, and subject-series relationships.

| Field | Value |
| --- | --- |
| Project ID | architecture-college-curriculum |
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
- Spring Security
- SpringDoc OpenAPI
- PostgreSQL
- Docker / Docker Compose

## Additional notes

# Project metadata

Values map to the **`Project`** interface in `docs-schema.ts`. **`ProjectDocsModel`** is split across the other files in this folder.

Configuration is loaded from **Spring Cloud Config** (`config-data/curriculum-service.yml`); the service registers with **Eureka** and reports to **Spring Boot Admin**.

