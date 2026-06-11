# Project Overview

## Why Schedule Service exists

Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows.

### Pain points

- Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows.

## How the service addresses it

- **Dedicated REST controllers** — Creation, finder, update, and deletion flows are split for clarity and evolution.
- **Capacity and roster operations** — Endpoints adjust spots and assign or remove teachers with explicit paths.
- **Event-friendly runtime** — RabbitMQ supports notifying peers when groups or schedules change.

## Operational signals

- Default HTTP port 8086 in root docker-compose.yml.
- PostgreSQL schedule_db for group and schedule persistence.
- RabbitMQ broker for AMQP (see compose env SPRING_RABBITMQ_*).

## Links

| Resource | URL |
| --- | --- |
| Github | https://github.com/alexisTrejo11/architecture-college-plattform |
| Demo | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| Documentation | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/schedule-service/README.md |
| Dockerhub | None |

## Service visuals

Replace URLs when production assets exist.

### Schedule Service Swagger

SpringDoc UI

- **Type:** image | **Category:** screenshot
- ![Swagger UI](https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-schedule-service.png)

## Additional media

### Eureka registration

Instance in discovery

## Metrics

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8086 | docker-compose host mapping |

## Additional notes

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Schedule Service** owns **class groups**, **meeting patterns**, **teacher assignment**, **capacity**, and **lookup** APIs used by enrollment and operations.

## Authoring notes

- Concurrent `increase-spot` / `decrease-spot` calls may need **optimistic locking** or DB constraints — confirm in implementation.
- Define **Rabbit** exchanges/queues as code or IaC so environments match.

