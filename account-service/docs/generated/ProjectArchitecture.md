# Architecture

## Edge & clients

Clients and other services call this microservice over HTTP.

### Components

- Admin consoles and future SPA
- Other microservices (service-to-service REST)

### Responsibilities

- Expose JWT-protected REST APIs

### Technologies

- HTTPS
- JWT Bearer tokens

## Application runtime

Account Service Spring Boot process.

### Components

- Controllers (Auth, User)
- Services & security configuration
- JPA repositories

### Responsibilities

- Identity and access for the Architecture College platform.

### Technologies

- Spring Web
- Spring Data JPA
- Spring Security

## Data & platform integration

Persistence and operational registration.

### Components

- PostgreSQL account_db
- Eureka client registration
- Config Server client

### Responsibilities

- Store users and credentials; register healthy instances

### Technologies

- PostgreSQL 16
- Spring Cloud Netflix Eureka
- Spring Cloud Config

## Design patterns

| Pattern | Category | Description |
| --- | --- | --- |
| DB Database per service | Microservices | Account data lives only in account_db; no shared user tables with other services. |
| JWT Token-based API security | Security | Issue JWTs at login; other services validate the same signing key. |

## Scalability strategies

- **Stateless instances** — Scale out multiple Account Service replicas behind a load balancer; no server session affinity.
- **Dedicated database** — Tune account_db independently from curriculum or enrollment workloads.

## Security strategies

- **JWT propagation** — This service mints tokens; consumers must validate signature, expiry, and roles.
- **Secrets not in Git** — JWT secret and DB passwords come from environment / secret manager in real deployment.

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| No default application cache | n/a | None today | Optional Redis if token blocklists or rate limiting are added later. |

## Architecture highlights

### Eureka Spring Cloud discovery

Registers with Eureka for dynamic lookup.

### Config Externalized configuration

Uses config-data/account-service.yml via Config Server.

## Architecture diagram

### Legend

| Type | Label |
| --- | --- |
| service | Microservice |
| database | Database |

### Nodes

| ID | Label | Type | Status |
| --- | --- | --- | --- |
| client | Clients | client | healthy |
| account_service | Account Service | service | healthy |
| postgres | PostgreSQL (account_db) | database | healthy |

### Connections

| From | To | Label | Protocol |
| --- | --- | --- | --- |
| client | account_service | REST | HTTP |
| account_service | postgres | SQL | JDBC |

### Mermaid overview

```mermaid
flowchart LR
    client([Clients])
    account_service[Account Service]
    postgres[(PostgreSQL (account_db))]
    client -->|REST| account_service
    account_service -->|SQL| postgres
```

## Data flow

### Request flow

1. **Signup or login** — Client posts credentials or registration payload to /v1/api/auth/*.
2. **Validate and persist** — Service validates input, persists user, and for login returns JWT.
3. **Authenticated calls** — Clients pass JWT to this or other services for protected routes.

### Event flow

1. **No domain events in v1** — Account Service focuses on synchronous HTTP; optional outbox can be added later.

## Technical decisions

### Spring Cloud Netflix (Eureka + Config)

**Problem:** Multiple JVM services need discovery and consistent configuration.

**Solution:** Use Eureka registration and Spring Cloud Config with config-data YAML.

**Outcome:** Operational parity between local Compose and target deployment.

#### Alternatives considered

- Kubernetes-only DNS + ConfigMaps
- Consul for discovery and KV config

## Additional notes

# Architecture

Narrative above aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- Rotate **JWT** signing keys per environment; never ship default keys.
- Narrow **Actuator** endpoint exposure in production (`management.endpoints.web.exposure`).
- Keep **password hashing** and validation rules in sync with institutional policy.

