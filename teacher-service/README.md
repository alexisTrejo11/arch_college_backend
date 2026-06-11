# Teacher Service

Lifecycle management for faculty: creation, validation of payroll numbers, and publication of teacher-related events. This service is part of the **Architecture College Platform** (deployed stack documented alongside `docker-compose.yml`).

## Table of contents

- [Overview](#overview)
- [Core capabilities](#core-capabilities)
- [Tech stack](#tech-stack)
- [API surface](#api-surface)
- [Security](#security)
- [Observability](#observability)
- [Run locally](#run-locally)
- [Docker Compose (platform)](#docker-compose-platform)
- [Documentation](#documentation)

## Overview

- **Service name:** `teacher-service`
- **Role:** Teacher aggregate backing scheduling and grading experiences.
- **Default port:** `8084` (see root `docker-compose.yml`)
- **Persistence:** PostgreSQL (`teacher_db`)
- **Integration:** Spring AMQP / RabbitMQ JWT validation

## Core capabilities

- Create and delete teachers
- Fetch teacher by id, account number, or batch ids
- Validate teacher account numbers

## Tech stack

- Java 17, Spring Boot 3.3.x, Spring Cloud 2023.0.3
- Spring Web, Spring Data JPA, Spring Security (as enabled in module)
- Netflix Eureka client + Spring Cloud Config client
- SpringDoc OpenAPI (`/swagger-ui.html`)
- Spring AMQP / RabbitMQ
- JWT validation

## API surface

Representative operations include: Create teacher, Delete teacher, Validate account number, Get teacher (note: base path lacks /v1/api prefix). The full contract is generated at runtime—use Swagger UI or [`docs/generated/APISchema.md`](docs/generated/APISchema.md) for portfolio alignment with `APISchema` in `docs-schema.ts`.

## Security

- JWT is issued by account-service; protected routes expect a valid bearer token where enforced.
- Jakarta Validation on command DTOs; Spring Security filters secure write-heavy paths.

## Observability

- Spring Boot Actuator endpoints (dev stack often exposes `management.endpoints.web.exposure.include=*` via config—narrow in production).
- Registers with **Spring Boot Admin** (`admin-service`, port `8081` in Compose).

## Run locally

```bash
cd teacher-service
./gradlew bootRun
./gradlew test
```

Ensure Config Server (`8888`), Eureka (`8761`), and backing databases match `SPRING_CONFIG_URI` / datasource env vars.

## Docker Compose (platform)

From repository root:

```bash
docker compose up -d --build
```

The `teacher-service` container receives datasource, discovery, and (if applicable) Rabbit/Mongo settings inline in Compose.

## Documentation

| Kind | Path |
|------|------|
| Generated (human-readable) | [docs/generated/](docs/generated/) — [ProjectOverview](docs/generated/ProjectOverview.md), [ProjectArchitecture](docs/generated/ProjectArchitecture.md), [ProjectMetadata](docs/generated/ProjectMetadata.md), [ProjectInfrastructure](docs/generated/ProjectInfrastructure.md), [ProjectFeature](docs/generated/ProjectFeature.md), [ProjectCodeShowCase](docs/generated/ProjectCodeShowCase.md), [APISchema](docs/generated/APISchema.md) |
| Source (YAML frontmatter) | [docs/source/](docs/source/) — edit here, then regenerate (see below) |

Regenerate from source:

```bash
python docs/yaml_to_markdown.py
```

Platform overview: [../README.md](../README.md).

---

Update this README whenever public API paths, ports, or infrastructure assumptions change.
