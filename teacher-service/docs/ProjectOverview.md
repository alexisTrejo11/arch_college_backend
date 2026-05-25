# Overview

Structured counterpart: [obsidian/ProjectOverview.md](obsidian/ProjectOverview.md) (`ProjectOverview` in [docs-schema.ts](../../docs-schema.ts)).

## Problem

Authoritative **faculty** data for scheduling/grading, with **duplicate-safe** account validation and **JWT**-aligned security.

## Solution

| Item | Detail |
| --- | --- |
| Validation endpoint | `GET .../validate` before expensive flows. |
| Command vs query | Writes **`/v1/api/teachers`**; reads **`/teachers`** (normalize externally). |
| Messaging | **RabbitMQ** for peer integration. |

## Key metrics

- Port **8084**  
- **teacher_db** + **RabbitMQ**  
- **JWT** secret **synchronized** with account-service  

## Links & media

Full **`ProjectLinks`**, gallery, and **`metrics[]`** are in the Obsidian file.

## Summary

**Teacher Service** provides **create/delete**, **validate**, **get by id / account / ids**, with **Spring Security + JWT** and **AMQP**.

### Notes

- **Gateway:** expose a single **`/v1/api/teachers/...`** surface.  
- **Rotate JWT** with account-service together.  
