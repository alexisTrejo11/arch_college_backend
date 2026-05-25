# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem statement

**Why Curriculum Service exists**

Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume.

- Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume.

## Solution

**How the service addresses it**

| Title | Description |
| --- | --- |
| Bounded REST resources | Controllers map to aggregates: careers, areas, professional lines, obligatory/elective subjects, subject series. |
| Single relational catalog | Postgres `curriculum_db` is the source of truth; no shared tables with other services. |
| Rich read shapes | Queries by area, career, semester, and series support admin tooling and downstream services. |

## Key metrics (narrative)

**Operational signals**

- Default HTTP port **8085** in root `docker-compose.yml`.  
- Swagger UI at `/swagger-ui.html` when SpringDoc is enabled.  
- Isolated database **`curriculum_db`** for all catalog entities.  

## Cover image

| Attribute | Value |
| --- | --- |
| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png |
| **alt** | Placeholder platform overview image |
| **credit** | Replace with deployment screenshot |

## Links

| Key | URL |
| --- | --- |
| **github** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **demo** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **documentation** | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/curriculum-service/README.md |
| **dockerHub** | _null_ |

## Media gallery

**Service visuals** — Replace URLs after capturing real assets.

| Field | Value |
| --- | --- |
| **type** | image |
| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-curriculum-service.png |
| **title** | Curriculum Service Swagger |
| **description** | SpringDoc UI |
| **alt** | Swagger UI |
| **category** | screenshot |

## Additional media items

- **type:** image  
- **url:** https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-curriculum-service.png  
- **title:** Eureka registration  
- **description:** Instance in discovery  
- **alt:** Eureka  
- **category:** architecture  

## Metrics (typed)

| Label | Value | Description | Icon | Trend |
| --- | --- | --- | --- | --- |
| Service port | 8085 | docker-compose host mapping | network | stable |

## Summary

**Curriculum Service** is the **academic catalog**: careers, areas, professional lines, subjects, and subject-series links for the Architecture College platform.

### Authoring notes

- Some controllers omit a leading `/` on `@RequestMapping` — normalize at an API gateway.  
- Align **`springdoc.packages-to-scan`** with `io.github.alexistrejo11...` in Config data so Swagger is complete.  
