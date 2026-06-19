# Architecture

## Clients

Admin tools, internal services (enrollment, schedule) calling the catalog over REST.

### Components

- Back-office UIs
- Other microservices (Feign/RestTemplate/WebClient)

### Responsibilities

- Consume stable catalog identifiers and relationships

### Technologies

- HTTPS
- Optional JWT from account-service

## Application runtime

Curriculum Service Spring Boot application.

### Components

- REST controllers (career, area, professional line, subjects, series)
- Domain services and validation
- JPA repositories

### Responsibilities

- Academic catalog source of truth for the platform

### Technologies

- Spring Web
- Spring Data JPA
- Jakarta Validation

## Persistence & platform

Datastore and cloud-native hooks.

### Components

- PostgreSQL curriculum_db
- Eureka client
- Config Server client

### Responsibilities

- Persist normalized curriculum graph; register with discovery

### Technologies

- PostgreSQL 16
- Spring Cloud Netflix Eureka
- Spring Cloud Config

## Design patterns

| Pattern | Category | Description |
| --- | --- | --- |
| DB Database per service | Microservices | Only curriculum_db holds catalog tables; no cross-schema joins with enrollment or schedule. |
| API Aggregate-oriented APIs | API | One controller family per aggregate reduces coupling and keeps URLs predictable. |

## Scalability strategies

- **Stateless horizontal scale** — Read-heavy catalog scales with replica count and DB read tuning.
- **Optional read caching later** — CDN or Redis for hot GET paths (e.g. full career trees) if metrics justify it.

## Security strategies

- **Gateway + service auth** — Protect mutating routes in production; align with institutional RBAC.
- **Least-privilege DB role** — DB user limited to curriculum_db only.

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| Not enabled by default | TBD | Future | Add application or edge cache when profiling shows catalog hot spots. |

## Architecture highlights

### EU Eureka registration

Service instances advertise health to peers.

### CFG Externalized YAML

Behavior tuned via config-data/curriculum-service.yml.

## Architecture diagram

### Legend

| Type | Label |
| --- | --- |
| service | Microservice |
| database | PostgreSQL |

### Nodes

| ID | Label | Type | Status |
| --- | --- | --- | --- |
| client | Clients | client | healthy |
| curriculum_service | Curriculum Service | service | healthy |
| postgres | PostgreSQL (curriculum_db) | database | healthy |

### Connections

| From | To | Label | Protocol |
| --- | --- | --- | --- |
| client | curriculum_service | REST | HTTP |
| curriculum_service | postgres | SQL | JDBC |

### Mermaid overview

```mermaid
flowchart LR
    client([Clients])
    curriculum_service[Curriculum Service]
    postgres[(PostgreSQL (curriculum_db))]
    client -->|REST| curriculum_service
    curriculum_service -->|SQL| postgres
```

## Data flow

### Request flow

1. **Resolve catalog request** — Client calls GET/POST/PUT/DELETE on /v1/api/careers, /areas, /subjects/*, etc.
2. **Validate and persist** — Controllers validate payloads and persist via JPA to curriculum_db.
3. **Downstream consumption** — Enrollment and schedule services use returned ids and keys in their own data.

### Event flow

1. **Synchronous integration** — This service does not publish AMQP by default; peers integrate via REST.
2. **Future eventing** — Optional domain events (e.g. catalog change) can be added with an outbox pattern.

## Technical decisions

### Relational model for curriculum graph

**Problem:** Careers, areas, and subjects need constraints and joins for integrity.

**Solution:** PostgreSQL + JPA entities with explicit relationships.

**Outcome:** Enrollment and scheduling can trust foreign keys and uniqueness in curriculum_db.

#### Alternatives considered

- Document store for entire catalog tree
- Graph database for prerequisite chains

## Additional notes

# Architecture

Aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **`SubjectSeriesController`** uses `@RequestMapping("v1/api/...")` without a leading slash — align with `/v1/api/...` at the gateway.
- **`config-data/curriculum-service.yml`** may still reference a legacy `springdoc.packages-to-scan` value — confirm Swagger lists all controllers before release.
- Planning **migrations** (Flyway/Liquibase) is safer than relying on `ddl-auto` in non-dev environments.

