# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem statement

**Why Account Service exists**

Academic platforms need distinct onboarding paths per persona while sharing a single identity model.

- Academic platforms need distinct onboarding paths per persona while sharing a single identity model.

## Solution

**How the service addresses it**

| Title | Description |
| --- | --- |
| Segmented signup | Dedicated endpoints for student, teacher, and admin registration with role-aware payloads. |
| Centralized login | Single `/login` path issuing JWT tokens consumed by downstream services. |
| Profile surface | `/my-profile` for authenticated users without leaking cross-tenant data. |

## Key metrics (narrative)

**Operational signals**

- Runs as a stateless Spring Boot instance (default port **8082** in `docker-compose`).
- OpenAPI / Swagger UI at `/swagger-ui.html`.
- PostgreSQL database **`account_db`** for identity persistence.

## Cover image

| Attribute | Value |
| --- | --- |
| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png |
| **alt** | Placeholder platform overview image |
| **credit** | Replace with a deployment screenshot |

## Links

| Key | URL |
| --- | --- |
| **github** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **demo** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **documentation** | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/account-service/README.md |
| **dockerHub** | _null_ |

## Media gallery

**Service visuals** — Replace URLs after capturing real deployment assets.

### Gallery item

| Field | Value |
| --- | --- |
| **type** | image |
| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-account-service.png |
| **title** | Account Service Swagger |
| **description** | SpringDoc UI |
| **alt** | Swagger UI |
| **category** | screenshot |

## Additional media items

### Item 1

- **type:** image  
- **url:** https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-account-service.png  
- **title:** Eureka registration  
- **description:** Instance registered in discovery  
- **alt:** Eureka  
- **category:** architecture  

## Metrics (typed)

| Label | Value | Description | Icon | Trend |
| --- | --- | --- | --- | --- |
| Service port | 8082 | Host mapping in root docker-compose.yml | network | stable |

## Summary

**Account Service** owns signup, JWT authentication, and read access to the current user’s profile for the Architecture College platform.

### Authoring notes

- `liveDemoUrl` may remain a repository anchor until a public API base URL exists.
- Media URLs are placeholders.
