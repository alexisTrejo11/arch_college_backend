# Architecture College Platform

Production-style **Spring Boot microservices** monorepo for an architecture faculty: identities, students, teachers, curriculum, schedules, enrollments, and grades. The stack is **documented as deployed**—`docker-compose.yml` mirrors the target environment; swap secrets and hostnames for your cloud provider.

## Table of contents

- [What is in this repository](#what-is-in-this-repository)
- [Service catalog](#service-catalog)
- [Platform components](#platform-components)
- [Documentation & schema](#documentation--schema)
- [Deployed topology (Compose)](#deployed-topology-compose)
- [Local development](#local-development)
- [Safety notes](#safety-notes)

## What is in this repository

| Area | Purpose |
| --- | --- |
| `*-service/` | Domain microservices (REST APIs, own databases, `docs/source/` + `docs/generated/`). |
| `config-data/` | Spring Cloud Config payloads (`https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/config-data`). |
| `docker-compose.yml` | Postgres, MongoDB, RabbitMQ, Config Server, Eureka, Admin, business services. |
| `docs-schema.ts` | Type definitions for a portfolio CMS (YAML frontmatter shape). |
| `common-classes/` | Shared library consumed by services. |
| `eureka-server/`, `config-server/`, `admin-service/` | Platform control plane modules. |

## Service catalog

| Service | Port | README | Responsibility |
| --- | ---: | --- | --- |
| `account-service` | 8082 | [account-service/README.md](account-service/README.md) | Identity and access for the Architecture College platform. |
| `student-service` | 8083 | [student-service/README.md](student-service/README.md) | Student aggregate and progression events for enrollment and grading workflows. |
| `teacher-service` | 8084 | [teacher-service/README.md](teacher-service/README.md) | Teacher aggregate backing scheduling and grading experiences. |
| `curriculum-service` | 8085 | [curriculum-service/README.md](curriculum-service/README.md) | Academic catalog source of truth consumed by enrollment and scheduling. |
| `schedule-service` | 8086 | [schedule-service/README.md](schedule-service/README.md) | Operational timetable and capacity management for offerings. |
| `enrollment-service` | 8087 | [enrollment-service/README.md](enrollment-service/README.md) | Registration workflows bridging students, groups, and operational preload jobs. |
| `grade-service` | 8088 | [grade-service/README.md](grade-service/README.md) | Grade and transcript domain with Postgres + Mongo + Rabbit integration. |

## Platform components

- **Spring Cloud Config** — centralises YAML; mounted from `config-data/`.
- **Netflix Eureka** — service discovery (`8761`).
- **Spring Boot Admin** — operational visibility (`8081`).
- **PostgreSQL** — one logical database per microservice.
- **MongoDB** — document projections for enrollment and grade flows.
- **RabbitMQ** — asynchronous integration (student, teacher, schedule, grade services).

## Documentation & schema

- Portfolio CMS types: [`docs/project/source/schema.ts`](docs/project/source/schema.ts).
- Each domain service keeps **source** YAML frontmatter in `{service}/docs/source/` and **generated** human-readable Markdown in `{service}/docs/generated/`.
- **Unified project source** (all services merged): [`docs/project/source/`](docs/project/source/) — run `python docs/project/merge_service_sources.py` after editing any service source file.
- Regenerate one service: `cd {service} && python docs/yaml_to_markdown.py` (requires PyYAML — use the repo `.venv` or `pip install pyyaml`).
- Regenerate all service docs **and** merge project source from the repo root: `./scripts/regenerate-docs.sh`

| Service | Generated docs | Source |
| --- | --- | --- |
| `account-service` | [docs/generated/](account-service/docs/generated/) | [docs/source/](account-service/docs/source/) |
| `student-service` | [docs/generated/](student-service/docs/generated/) | [docs/source/](student-service/docs/source/) |
| `teacher-service` | [docs/generated/](teacher-service/docs/generated/) | [docs/source/](teacher-service/docs/source/) |
| `curriculum-service` | [docs/generated/](curriculum-service/docs/generated/) | [docs/source/](curriculum-service/docs/source/) |
| `schedule-service` | [docs/generated/](schedule-service/docs/generated/) | [docs/source/](schedule-service/docs/source/) |
| `enrollment-service` | [docs/generated/](enrollment-service/docs/generated/) | [docs/source/](enrollment-service/docs/source/) |
| `grade-service` | [docs/generated/](grade-service/docs/generated/) | [docs/source/](grade-service/docs/source/) |

## Deployed topology (Compose)

```
Client / SPA ──► REST ──► domain microservices (8082-8088)
                              │
                              ├──► PostgreSQL (per DB)
                              ├──► MongoDB (enrollment, grade)
                              └──► RabbitMQ (events)
Platform: Config Server :8888, Eureka :8761, Admin :8081
```

## Local development

Requirements: **Java 17**, **Docker** with Compose plugin.

```bash
git clone {REPO}.git
cd architecture-college-plattform
docker compose up -d --build
```

Gradle per module:

```bash
cd account-service && ./gradlew bootRun
```

## Safety notes

- Replace default `JWT_SECRET_KEY`, database, and broker credentials before any shared environment.
- Several controllers omit a leading `/` or the `/v1/api` prefix—normalize at an API gateway for public traffic.
- `config-data/curriculum-service.yml` lists a legacy `springdoc.packages-to-scan` value—verify Swagger picks up the correct package during QA.

---
Maintainers: update service tables and ports when modules are added or renamed.
