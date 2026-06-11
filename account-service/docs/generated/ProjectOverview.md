# Project Overview

## Why Account Service exists

Academic platforms need distinct onboarding paths per persona while sharing a single identity model.

### Pain points

- Academic platforms need distinct onboarding paths per persona while sharing a single identity model.

## How the service addresses it

- **Segmented signup** — Dedicated endpoints for student, teacher, and admin registration with role-aware payloads.
- **Centralized login** — Single /login path issuing JWT tokens consumed by downstream services.
- **Profile surface** — /my-profile for authenticated users without leaking cross-tenant data.

## Operational signals

- Runs as a stateless Spring Boot instance (default port 8082 in docker-compose).
- OpenAPI/Swagger UI at /swagger-ui.html.
- PostgreSQL database account_db for identity persistence.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/account-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs after capturing real deployment assets.

### Account Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-account-service.png)

## Additional media

### Eureka registration

Instance registered in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8082 | Host mapping in root docker-compose.yml |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Account Service** owns signup, authentication (JWT), and basic profile read for the Architecture College platform.

## Authoring notes

- `liveDemoUrl` may point at the repo anchor until a public API base URL exists.
- Media URLs are placeholders.

