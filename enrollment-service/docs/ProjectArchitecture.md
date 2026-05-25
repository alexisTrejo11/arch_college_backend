# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

### Clients

Student portals, admins, and peer services over **HTTPS** (with **JWT** when secured).

### Application runtime

- **Enrollment command & finder** APIs  
- **Student enrollment** (self-service)  
- **Preload** controllers (students, subjects, schedules, grades)  
- **Spring Web** + **JPA** + **Mongo** repositories  

### Persistence

- **PostgreSQL** `enrollment_db` — transactional enrollments  
- **MongoDB** — preload / status documents  
- **Eureka** + **Config Server** clients  

## Design patterns

| Title | Category | Description |
| --- | --- | --- |
| Database per service | Microservices | `enrollment_db` owned only by this service. |
| Polyglot persistence | Data | OLTP in Postgres; flexible documents in Mongo. |

## Scalability

- Scale **stateless** API tier horizontally.  
- **Isolate** bulk preload from hot enrollment paths (rate limits, async workers).  

## Security

- Separate **student** vs **admin/operator** routes.  
- **Secrets** for Postgres and Mongo from environment / vault.  

## Cache

Not enabled by default; optional **Redis** for idempotency or throttling.

## Diagram summary (`ArchitectureDiagramModel`)

| Node | Type | Role |
| --- | --- | --- |
| client | client | Callers |
| enrollment | service | Enrollment Service |
| pg | database | PostgreSQL |
| mongo | database | MongoDB |

**Connections:** client → enrollment (HTTP); enrollment → pg (JDBC); enrollment → mongo (driver).

## Data flow

1. **Authorize** at gateway.  
2. **Business logic** in command/student/preload controllers.  
3. **Persist** to Postgres and/or Mongo.  

**Events:** Preload **POST** starts work; **GET status** polls Mongo state; peers may integrate via HTTP (or future messaging).

## Technical decision highlight

**Postgres + Mongo together** — ACID for enrollments, document model for preload job metadata (alternatives: JSONB only; external workflow engine).

## Watch items

- Clarify **authoritative reads** (relational vs document).  
- **Path normalization** for mappings without leading `/`.  
- **Migrations** on Postgres; **indexes/TTL** on Mongo for preload.  
