# Project metadata

Structured counterpart: [obsidian/ProjectMetadata.md](obsidian/ProjectMetadata.md) (`Project` in [docs-schema.ts](../../docs-schema.ts)).

| Field | Value |
| --- | --- |
| **projectId** | `architecture-college-account` |
| **featured** | No |
| **name** | Account Service |
| **language** | Java |
| **category** | backend |
| **framework** | Spring Boot + Spring Cloud Netflix |
| **version** | 1.0.0 |
| **repositoryUrl** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **liveDemoUrl** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **description** | Handles signup for students, teachers, and admins, JWT login, and authenticated profile access. |
| **status** | deployed |
| **createdAt** | 2026-01-01T00:00:00.000Z |
| **updatedAt** | 2026-05-11T00:00:00.000Z |

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

## Context

This module uses **Spring Cloud Config**, registers with **Eureka**, and reports to **Spring Boot Admin**. The **`docs`** object in `Project` (`ProjectDocsModel`) is split across the other documents in this directory.
