# Project Overview

## Why Enrollment Service exists

Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path.

### Pain points

- Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path.

## How the service addresses it

- **Polyglot persistence** — PostgreSQL holds authoritative enrollment rows; MongoDB stores preload process state and auxiliary documents.
- **Dedicated REST surfaces** — Command vs finder controllers separate writes from reads; student self-service has its own routes.
- **Bulk preload APIs** — Students, subjects, schedules, and grades each expose preload + status + clear lifecycles for operators.

## Operational signals

- Default HTTP port 8087 in root docker-compose.yml.
- JDBC to PostgreSQL enrollment_db; Mongo URI for document collections.
- Swagger UI at /swagger-ui.html when SpringDoc is enabled.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/enrollment-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs with production captures when available.

### Enrollment Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-enrollment-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8087 | Host mapping in docker-compose |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Enrollment Service** coordinates **group enrollments**, **student self-service** enroll/drop, **lock-date** rules, and **bulk preload** operations with status tracked in MongoDB.

## Authoring notes

- Several controllers use `@RequestMapping("v1/api/...")` **without** a leading slash — document canonical paths as **`/v1/api/...`** at the gateway.
- Define which store is **source of truth** per aggregate (Postgres vs Mongo) in runbooks to avoid split-brain reads.

