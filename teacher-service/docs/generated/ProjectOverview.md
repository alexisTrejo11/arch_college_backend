# Project Overview

## Why Teacher Service exists

Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens.

### Pain points

- Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens.

## How the service addresses it

- **Explicit validation endpoint** — Pre-validate teacher account numbers before expensive downstream flows.
- **Command vs query APIs** — Writes under /v1/api/teachers; reads under /teachers (legacy path — normalize at gateway).
- **Broker-backed integration** — RabbitMQ keeps schedule/grade consumers loosely coupled (as implemented).

## Operational signals

- Default HTTP port 8084 in docker-compose.yml.
- PostgreSQL teacher_db; RabbitMQ from compose.
- JWT secret must match account-service signing key in every environment.

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/teacher-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs when assets exist.

### Teacher Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-teacher-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8084 | docker-compose host mapping |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Teacher Service** is the **faculty aggregate**: **create/delete**, **validate** account numbers, **batch get**, with **JWT** validation and **RabbitMQ**.

## Authoring notes

- **`TeacherQueryController`** maps to **`/teachers`** while commands use **`/v1/api/teachers`** — unify at **API gateway** (e.g. `/v1/api/teachers/...` everywhere).  
- **Rotate JWT secrets** with account-service in lockstep.

