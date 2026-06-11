# Project Overview

## Why Curriculum Service exists

Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume.

### Pain points

- Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume.

## How the service addresses it

- **Bounded REST resources** — Controllers map to aggregates: careers, areas, professional lines, obligatory/elective subjects, subject series.
- **Single relational catalog** — Postgres curriculum_db is the source of truth; no shared tables with other services.
- **Rich read shapes** — Queries by area, career, semester, and series support admin tooling and downstream services.

## Operational signals

- Default HTTP port 8085 in root docker-compose.yml.
- Swagger UI at /swagger-ui.html when SpringDoc is enabled.
- Isolated database curriculum_db for all catalog entities.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/curriculum-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs after capturing real assets.

### Curriculum Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-curriculum-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8085 | docker-compose host mapping |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Curriculum Service** is the **academic catalog** for the platform: careers, areas, professional lines, subjects, and how series link offerings together.

## Authoring notes

- Some controllers omit a leading `/` on `@RequestMapping` — normalize at an API gateway for public docs.
- Verify **`springdoc.packages-to-scan`** in `config-data/curriculum-service.yml` matches `io.github.alexistrejo11...` so Swagger discovers all controllers.

