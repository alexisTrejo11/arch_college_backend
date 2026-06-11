# Curriculum Service

Model careers, areas, professional lines, obligatory/elective subjects, and subject series relationships. This service is part of the **Architecture College Platform** (deployed stack documented alongside `docker-compose.yml`).

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

- **Service name:** `curriculum-service`
- **Role:** Academic catalog source of truth consumed by enrollment and scheduling.
- **Default port:** `8085` (see root `docker-compose.yml`)
- **Persistence:** PostgreSQL (`curriculum_db`)
- **Integration:** Standard Spring Cloud client libraries

## Core capabilities

- CRUD on careers, areas, professional lines
- Manage obligatory and elective subjects
- Maintain subject-series graph

## Tech stack

- Java 17, Spring Boot 3.3.x, Spring Cloud 2023.0.3
- Spring Web, Spring Data JPA, Spring Security (as enabled in module)
- Netflix Eureka client + Spring Cloud Config client
- SpringDoc OpenAPI (`/swagger-ui.html`)

## API surface

Representative operations include: List careers, List areas, Professional lines, Obligatory subjects. The full contract is generated at runtime—use Swagger UI or [`docs/generated/APISchema.md`](docs/generated/APISchema.md) for portfolio alignment with `APISchema` in `docs-schema.ts`.

## Security

- JWT is issued by account-service; protected routes expect a valid bearer token where enforced.
- Jakarta Validation on command DTOs; Spring Security filters secure write-heavy paths.

## Observability

- Spring Boot Actuator endpoints (dev stack often exposes `management.endpoints.web.exposure.include=*` via config—narrow in production).
- Registers with **Spring Boot Admin** (`admin-service`, port `8081` in Compose).

## Run locally

```bash
cd curriculum-service
./gradlew bootRun
./gradlew test
```

Ensure Config Server (`8888`), Eureka (`8761`), and backing databases match `SPRING_CONFIG_URI` / datasource env vars.

## Docker Compose (platform)

From repository root:

```bash
docker compose up -d --build
```

The `curriculum-service` container receives datasource, discovery, and (if applicable) Rabbit/Mongo settings inline in Compose.

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
