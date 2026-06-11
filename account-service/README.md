# Account Service

Handles signup flows for students, teachers, and admins, JWT-based login, and authenticated profile access. This service is part of the **Architecture College Platform** (deployed stack documented alongside `docker-compose.yml`).

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

- **Service name:** `account-service`
- **Role:** Identity and access for the Architecture College platform.
- **Default port:** `8082` (see root `docker-compose.yml`)
- **Persistence:** PostgreSQL (`account_db`)
- **Integration:** JWT (HS256 shared secret) Spring Boot Admin client

## Core capabilities

- Student, teacher, and admin registration
- JWT authentication for API consumers
- Authenticated user profile retrieval
- Discovery registration via Eureka

## Tech stack

- Java 17, Spring Boot 3.3.x, Spring Cloud 2023.0.3
- Spring Web, Spring Data JPA, Spring Security (as enabled in module)
- Netflix Eureka client + Spring Cloud Config client
- SpringDoc OpenAPI (`/swagger-ui.html`)
- JWT (HS256 shared secret)
- Spring Boot Admin client

## API surface

Representative operations include: Register a student account, Register a teacher account, Register an admin account, Authenticate and obtain JWT. The full contract is generated at runtime—use Swagger UI or [`docs/generated/APISchema.md`](docs/generated/APISchema.md) for portfolio alignment with `APISchema` in `docs-schema.ts`.

## Security

- JWT is issued by account-service; protected routes expect a valid bearer token where enforced.
- Jakarta Validation on command DTOs; Spring Security filters secure write-heavy paths.
- HS256 secret must match every consumer that validates tokens.

## Observability

- Spring Boot Actuator endpoints (dev stack often exposes `management.endpoints.web.exposure.include=*` via config—narrow in production).
- Registers with **Spring Boot Admin** (`admin-service`, port `8081` in Compose).

## Run locally

```bash
cd account-service
./gradlew bootRun
./gradlew test
```

Ensure Config Server (`8888`), Eureka (`8761`), and backing databases match `SPRING_CONFIG_URI` / datasource env vars.

## Docker Compose (platform)

From repository root:

```bash
docker compose up -d --build
```

The `account-service` container receives datasource, discovery, and (if applicable) Rabbit/Mongo settings inline in Compose.

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
