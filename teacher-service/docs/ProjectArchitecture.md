# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

1. **Clients** — schedule, grade, admin (HTTPS, **JWT**).  
2. **Runtime** — **TeacherCommandController**, **TeacherQueryController**, Security, JPA.  
3. **Persistence & messaging** — **`teacher_db`**, **RabbitMQ**, Eureka, Config.  

## Patterns

- **Database per service**  
- **Path split** — commands versioned; queries legacy `/teachers` (gateway should unify).  

## Diagram (conceptual)

Clients → **Teacher Service** → **PostgreSQL** and **RabbitMQ**.

## Decision highlight

**TechDecision:** intentional **split base paths** for backward compatibility; **prefer gateway rewrite** to **`/v1/api/teachers`** for all routes.

## Watch items

- **JWT** key mismatch → auth failures.  
- **Rabbit** DLQ/retry.  
- **Public path** consistency in Swagger.  
