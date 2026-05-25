# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

1. **Clients** — admin, student, teacher apps and internal callers (HTTPS / JWT).  
2. **Runtime** — grade query/command, groups, teacher grading, academic history controllers.  
3. **Data & messaging** — **PostgreSQL**, **MongoDB**, **RabbitMQ**, **Eureka**, **Config Server**.  

## Patterns

- **Database per service**  
- **Separate query and command controllers** for grades  

## Diagram (conceptual)

`client` → **Grade Service** → **PostgreSQL**, **MongoDB**, **RabbitMQ**.

## Data flow

REST → validation → **JPA** / **Mongo** → optional **AMQP** for projections and history updates.

## Decisions

**Dual store** — relational grades with Mongo for selected read models; **Netflix Eureka + Config** for platform consistency.

## Watch items

- **Path prefix** on `AcademicHistoryController` (no leading `/` in code).  
- **Source of truth** when diagnosing SQL vs Mongo-backed reads.  
