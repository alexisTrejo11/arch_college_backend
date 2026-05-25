# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem

Student profiles and progression must stay **consistent** while **peer services** react—preferably without **chatty synchronous** coupling.

## Solution (summary)

| Approach | Detail |
| --- | --- |
| Command / query split | `StudentCommandController` vs `StudentQueryController`. |
| Single DB boundary | **`student_db`** as source of truth. |
| Messaging | **RabbitMQ** for cross-service notifications (as wired). |

## Key metrics (narrative)

- Port **8083** (default Compose).  
- **PostgreSQL** + **RabbitMQ**.  
- Swagger **`/swagger-ui.html`**.  

## Links & media

See Obsidian front matter for **`ProjectLinks`**, **`ProjectCoverImage`**, **`MediaGallerySection`**, and **`ProjectMetric[]`**.

## Summary

**Student Service** provides **CRUD**, **lookups** (`/by`, account number, id), **professional line / modality**, and **semester increment**, with **AMQP** integration.

### Notes

- Align **professional line ids** with **curriculum-service**.  
- Document **Rabbit** topology for operators.  
