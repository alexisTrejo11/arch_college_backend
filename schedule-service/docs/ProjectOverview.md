# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem statement

**Why Schedule Service exists**

Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows.

- Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows.

## Solution

| Title | Description |
| --- | --- |
| Dedicated REST controllers | Creation, finder, update, and deletion flows are split for clarity. |
| Capacity and roster operations | Endpoints adjust spots and assign or remove teachers. |
| Event-friendly runtime | RabbitMQ supports notifying peers when groups or schedules change. |

## Key metrics

- HTTP port **8086** (default Compose).  
- **PostgreSQL** `schedule_db`.  
- **RabbitMQ** broker (see `SPRING_RABBITMQ_*` in Compose).  
- **Swagger** at `/swagger-ui.html` when enabled.  

## Cover image

| **url** | https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png |
| **alt** | Placeholder platform overview |
| **credit** | Replace with deployment screenshot |

## Links

| Key | Value |
| --- | --- |
| **github** | https://github.com/alexisTrejo11/architecture-college-plattform |
| **demo** | https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack |
| **documentation** | https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/schedule-service/README.md |
| **dockerHub** | _null_ |

## Media

- **Gallery:** Swagger screenshot — `docs/assets/swagger-schedule-service.png` (see Obsidian for full `ProjectMediaItem` fields).  
- **Extra item:** Eureka — `docs/assets/eureka-schedule-service.png`.  

## Metrics (typed)

| Label | Value | Description |
| --- | --- | --- |
| Service port | 8086 | docker-compose host mapping |

## Summary

**Schedule Service** manages **class groups**, **schedules**, **teachers**, and **capacity**, with **finder** APIs and **AMQP** integration.

### Authoring notes

- Review **concurrency** on capacity updates.  
- Version **Rabbit** topology (exchanges, queues, DLQs).  
