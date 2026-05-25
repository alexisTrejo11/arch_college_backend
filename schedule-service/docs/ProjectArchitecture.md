# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

### Clients

Scheduling admin, **enrollment**, and other services over **HTTPS** (JWT when required).

### Application runtime

- **GroupCreationController** — `POST /v1/api/groups/obligatory|elective`  
- **GroupFinderController** — `GET /v1/api/finder/groups/...`  
- **GroupUpdateController** — schedule, teachers, capacity  
- **GroupDeletionController** — roster patch + delete  

**Stack:** Spring Web, JPA, **Spring AMQP**.

### Data & messaging

- **PostgreSQL** `schedule_db`  
- **RabbitMQ**  
- **Eureka** + **Config Server**  

## Design patterns

| Title | Description |
| --- | --- |
| Database per service | Only `schedule_db` for this bounded context. |
| Command/query split | Writes under `/v1/api/groups`; reads under `/v1/api/finder/groups`. |

## Scalability

- Stateless **horizontal** scaling of the API tier.  
- **Broker** decouples notify fan-out from request latency.  

## Security

- Protect **mutations** (operator roles).  
- Separate credentials for **DB** and **Rabbit**.  

## Diagram summary

| Node | Type | Note |
| --- | --- | --- |
| client | client | Callers |
| schedule | service | Schedule Service |
| pg | database | PostgreSQL |
| mq | queue | RabbitMQ |

**Connections:** client → schedule (HTTP); schedule → pg (JDBC); schedule → mq (AMQP).

## Data flow

1. REST **invoke** (create/finder/update).  
2. **Persist** to Postgres.  
3. **Publish** integration messages when applicable.  

## Technical decision

**Dedicated finder base path** — isolates read-heavy traffic from transactional `/groups` routes.

## Watch items

- **Optimistic locking** / concurrency for spots and rosters.  
- **Rabbit** topology as code.  
- **Migrations** instead of risky `ddl-auto` in production.  
