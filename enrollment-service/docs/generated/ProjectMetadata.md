# Enrollment Service

Group and student enrollments, enrollment lock rules, and Mongo-backed bulk preload jobs alongside Postgres transactions.

| Field | Value |
| --- | --- |
| Project ID | architecture-college-enrollment |
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
- Spring Data MongoDB
- Spring Security
- SpringDoc OpenAPI
- PostgreSQL (enrollment_db)
- MongoDB
- Docker / Docker Compose

## Additional notes

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. **`ProjectDocsModel`** is split across sibling Obsidian files.

Uses **Spring Cloud Config**, **Eureka**, and **Spring Boot Admin**. Persistence is **polyglot**: **PostgreSQL** for transactional enrollments and **MongoDB** for preload/job artifacts (as implemented in the codebase).

