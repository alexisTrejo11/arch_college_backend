# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem

Grades must be **auditable** and **authorizable**, while **teachers** and **students** need reliable APIs for **pending work**, **history**, and **current enrollments**.

## Solution (summary)

| Approach | Detail |
| --- | --- |
| Audience-specific routes | Admin-grade search, teacher group grading, student JWT-scoped reads. |
| Dual persistence | **PostgreSQL** for transactional grades; **MongoDB** for document projections where configured. |
| Messaging | **RabbitMQ** for async academic history / integration paths. |

## Key metrics (narrative)

- Port **8088** (default Compose).  
- **PostgreSQL** `grade_db`, **MongoDB** `grade_db`, **RabbitMQ**.  
- Swagger **`/swagger-ui.html`**.  

## Links & media

See Obsidian front matter for **`ProjectLinks`**, **`ProjectCoverImage`**, **`MediaGallerySection`**, and **`ProjectMetric[]`**.

## Summary

**Grade Service** covers **grade queries and lifecycle**, **groups**, **teacher grading**, **student academic views**, and **ADMIN academic history** access.

### Notes

- Normalize **`AcademicHistoryController`** paths to a leading **`/v1/api`** at the gateway.  
- Keep **JWT** signing consistent with **account-service**.  
