# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem statement

**Why Enrollment Service exists**

Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path.

- Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path.

## Solution

| Title | Description |
| --- | --- |
| Polyglot persistence | PostgreSQL holds authoritative enrollment rows; MongoDB stores preload process state and auxiliary documents. |
| Dedicated REST surfaces | Command vs finder controllers separate writes from reads; student self-service has its own routes. |
| Bulk preload APIs | Students, subjects, schedules, and grades each expose preload + status + clear lifecycles for operators. |

## Key metrics

- Default HTTP port **8087** in root `docker-compose.yml`.  
- **JDBC** to PostgreSQL `enrollment_db`; **Mongo** URI for document collections.  
- **Swagger UI** at `/swagger-ui.html` when SpringDoc is enabled.  

## Cover image

| Attribute | Value |
| --- | --- |
| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png |
| **alt** | Placeholder platform overview |
| **credit** | Replace with deployment screenshot |

## Links

| Key | Value |
| --- | --- |
| **github** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **demo** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **documentation** | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/enrollment-service/README.md |
| **dockerHub** | _null_ |

## Media gallery

| Field | Value |
| --- | --- |
| **title** | Service visuals |
| **description** | Replace URLs with production captures when available. |
| **item type** | image |
| **item url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-enrollment-service.png |
| **category** | screenshot |

## Additional media item

Eureka registration screenshot: `docs/assets/eureka-enrollment-service.png` (see Obsidian front matter for full fields).

## Metrics (typed)

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8087 | Host mapping in docker-compose |

## Summary

**Enrollment Service** covers **group enrollments**, **student self-service** enroll/drop, **lock-date**, and **bulk preload** flows with **Mongo**-backed job state.

### Authoring notes

- Normalize paths to **`/v1/api/...`** at the gateway where Java mappings omit a leading slash.  
- Document **source of truth** per read (Postgres vs Mongo) in runbooks.  
