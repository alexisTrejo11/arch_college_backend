# Project Overview

## Why Grade Service exists

Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work.

### Pain points

- Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work.

## How the service addresses it

- **Split controllers by audience** — Admin-grade queries, teacher group grading, and student self-service reads each have focused routes.
- **Postgres + Mongo** — Transactional grade rows in PostgreSQL; Mongo supports projections and document-oriented reads where configured.
- **RabbitMQ** — Async hooks (e.g. academic history refresh) decouple heavy updates from the request path.

## Operational signals

- Default HTTP port 8088 in root docker-compose.yml.
- PostgreSQL grade_db; MongoDB grade_db; RabbitMQ from compose env.
- Swagger UI at /swagger-ui.html when SpringDoc is enabled.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/grade-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs when production assets exist.

### Grade Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-grade-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8088 | docker-compose host mapping |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Grade Service** owns **grade queries and lifecycle** (authorize, soft delete), **teacher grading** for groups, **student-facing academic history** endpoints, and **admin academic history** access, with **Postgres**, **MongoDB**, and **RabbitMQ** in the deployed stack.

## Authoring notes

- **`AcademicHistoryController`** uses `@RequestMapping("v1/api/academic-histories")` without a leading slash—normalize at the gateway to **`/v1/api/...`** for public docs.
- Keep **JWT signing** aligned with **account-service** for resource-server validation.

