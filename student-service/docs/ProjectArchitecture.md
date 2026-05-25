# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

1. **Clients** — internal services, admin tools (HTTPS / JWT).  
2. **Runtime** — command + query controllers, JPA, validation.  
3. **Persistence & messaging** — **PostgreSQL `student_db`**, **RabbitMQ**, **Eureka**, **Config Server**.  

## Patterns

- **Database per service**  
- **HTTP CQRS-style split** (separate controllers)  

## Diagram (conceptual)

`client` → **Student Service** → `PostgreSQL` and **RabbitMQ**.

## Data flow

REST → business rules → **JPA** → optional **AMQP** publish.

## Decisions

**Separate query controller** to evolve read models independently of writes.

## Watch items

- **Idempotency** on progression endpoints.  
- **Rabbit** DLQ / retry policy.  
- **Migrations** vs `ddl-auto` in prod.  
