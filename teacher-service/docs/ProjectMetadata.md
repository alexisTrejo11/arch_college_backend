# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-teacher` |
| **featured** | No |
| **name** | Teacher Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Faculty lifecycle: create/delete, validate account numbers, batch lookups, JWT, RabbitMQ. |
| **status** | deployed |
| **createdAt** | 2026-01-01T00:00:00.000Z |
| **updatedAt** | 2026-05-11T00:00:00.000Z |

## Tech stack

Java 17, Spring Boot 3.3.x, Spring Cloud, Eureka, Config, Web, Data JPA, **AMQP**, **Security**, **JWT** (shared with account-service), SpringDoc, **PostgreSQL (`teacher_db`)**, Docker.

## Context

**`config-data/teacher-service.yml`**; **`JWT`** must match **account-service** signing key. **teacher_db** + **RabbitMQ**.
