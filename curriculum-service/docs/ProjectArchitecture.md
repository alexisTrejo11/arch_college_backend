# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

### Clients

- **Description:** Admin tools and internal services (enrollment, schedule) calling the catalog over REST.  
- **Color:** `#4c566a`  
- **Components:** Back-office UIs; other microservices (Feign/RestTemplate/WebClient).  
- **Responsibilities:** Consume stable catalog identifiers and relationships.  
- **Technologies:** HTTPS; optional JWT from account-service.  

### Application runtime

- **Description:** Curriculum Service Spring Boot application.  
- **Color:** `#5e81ac`  
- **Components:** REST controllers; domain services; JPA repositories.  
- **Responsibilities:** Academic catalog source of truth.  
- **Technologies:** Spring Web; Spring Data JPA; Jakarta Validation.  

### Persistence & platform

- **Description:** Datastore and discovery/config clients.  
- **Color:** `#a3be8c`  
- **Components:** PostgreSQL `curriculum_db`; Eureka client; Config Server client.  
- **Technologies:** PostgreSQL 16; Spring Cloud Netflix Eureka; Spring Cloud Config.  

## Design patterns

| Title | Emoji | Badge | Category | Description |
| --- | --- | --- | --- | --- |
| Database per service | DB | Core | Microservices | Only `curriculum_db` holds catalog tables. |
| Aggregate-oriented APIs | API | DDD-friendly | API | One controller family per aggregate. |

## Scalability strategies

- **Stateless horizontal scale:** Read-heavy catalog scales with replicas and DB tuning.  
- **Optional read caching:** CDN or Redis for hot GET paths when justified.  

## Security strategies

- **Gateway + service auth:** Protect mutating routes in production.  
- **Least-privilege DB role:** DB user limited to `curriculum_db`.  

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| Not enabled by default | TBD | Future | Add cache when profiling shows hot paths. |

## Architecture features

- **Eureka registration:** Instances advertise health.  
- **Externalized YAML:** `config-data/curriculum-service.yml`.  

## Architecture diagram

### Legend

| Type | Label | Color |
| --- | --- | --- |
| service | Microservice | `#5e81ac` |
| database | PostgreSQL | `#a3be8c` |

### Nodes

| id | label | type | x | y | status |
| --- | --- | --- | --- | --- | --- |
| client | Clients | client | 10 | 20 | healthy |
| curriculum_service | Curriculum Service | service | 45 | 20 | healthy |
| postgres | PostgreSQL (curriculum_db) | database | 80 | 20 | healthy |

### Connections

| id | from → to | label | protocol |
| --- | --- | --- | --- |
| c1 | client → curriculum_service | REST | HTTP |
| c2 | curriculum_service → postgres | SQL | JDBC |

## Data flow

### Request flow

| # | Title | Description |
| --- | --- | --- |
| 1 | Resolve catalog request | Calls to `/v1/api/careers`, `/areas`, `/subjects/*`, etc. |
| 2 | Validate and persist | JPA writes to `curriculum_db`. |
| 3 | Downstream consumption | Enrollment/schedule use returned ids in their domains. |

### Event flow

| # | Title | Description |
| --- | --- | --- |
| 1 | Synchronous integration | Default integration is REST, not AMQP. |
| 2 | Future eventing | Optional outbox for catalog-change events. |

## Technical decisions

### Relational model for curriculum graph

- **Problem:** Careers, areas, and subjects need integrity constraints.  
- **Solution:** PostgreSQL + JPA with explicit relationships.  
- **Outcome:** Peers trust keys and uniqueness in `curriculum_db`.  
- **Alternatives:** Document tree store; graph DB for prerequisites.  

## Watch items

- Normalize **`SubjectSeriesController`** paths to **`/v1/api/...`** at the gateway.  
- Fix or verify **`springdoc.packages-to-scan`** in Config Server YAML.  
- Prefer **Flyway/Liquibase** over `ddl-auto` outside dev.  
