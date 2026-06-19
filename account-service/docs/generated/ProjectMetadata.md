# Account Service

Handles signup for students, teachers, and admins, JWT login, and authenticated profile access.

| Field | Value |
| --- | --- |
| Project ID | architecture-college-account |
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
- Spring Security
- SpringDoc OpenAPI
- PostgreSQL
- Docker / Docker Compose

## Additional notes

# Project metadata

Values above map to the **`Project`** interface in `docs-schema.ts` (top-level fields). Nested **`docs: ProjectDocsModel`** is documented across the other files in this folder.

This service uses **Spring Cloud Config**, registers with **Eureka**, and reports to **Spring Boot Admin**.

