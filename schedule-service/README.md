# Schedule Service

Creates and mutates class groups (obligatory/elective), assigns teachers, adjusts capacity, and answers schedule queries. This service is part of the **Architecture College Platform** (deployed stack documented alongside `docker-compose.yml`).

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

- **Service name:** `schedule-service`
- **Role:** Operational timetable and capacity management for offerings.
- **Default port:** `8086` (see root `docker-compose.yml`)
- **Persistence:** PostgreSQL (`schedule_db`)
- **Integration:** Spring AMQP / RabbitMQ

## Core capabilities

- Create obligatory and elective groups
- Attach/detach teachers, adjust schedules
- Query groups by id, key, filters, or current term slices

## Tech stack

- Java 17, Spring Boot 3.3.x, Spring Cloud 2023.0.3
- Spring Web, Spring Data JPA, Spring Security (as enabled in module)
- Netflix Eureka client + Spring Cloud Config client
- SpringDoc OpenAPI (`/swagger-ui.html`)
- Spring AMQP / RabbitMQ

## API surface

Representative operations include: Create obligatory group, Create elective group, Find group, Update meeting pattern. The full contract is generated at runtime—use Swagger UI or `docs/APISchema.md` for portfolio alignment with `APISchema` in `docs-schema.ts`.

## Security

- JWT is issued by account-service; protected routes expect a valid bearer token where enforced.
- Jakarta Validation on command DTOs; Spring Security filters secure write-heavy paths.

## Observability

- Spring Boot Actuator endpoints (dev stack often exposes `management.endpoints.web.exposure.include=*` via config—narrow in production).
- Registers with **Spring Boot Admin** (`admin-service`, port `8081` in Compose).

## Run locally

```bash
cd schedule-service
./gradlew bootRun
./gradlew test
```

Ensure Config Server (`8888`), Eureka (`8761`), and backing databases match `SPRING_CONFIG_URI` / datasource env vars.

## Docker Compose (platform)

From repository root:

```bash
docker compose up -d --build
```

The `schedule-service` container receives datasource, discovery, and (if applicable) Rabbit/Mongo settings inline in Compose.

## Documentation

| Kind | Path |
|------|------|
| Human-readable | [docs/ProjectOverview.md](docs/ProjectOverview.md), [docs/ProjectArchitecture.md](docs/ProjectArchitecture.md), [docs/APISchema.md](docs/APISchema.md), … |
| Obsidian / structured | [docs/obsidian/](docs/obsidian/) (YAML front matter aligned with `docs-schema.ts`) |

Platform-wide narrative: [../docs/ProjectOverview.md](../docs/ProjectOverview.md).

---

Update this README whenever public API paths, ports, or infrastructure assumptions change.
