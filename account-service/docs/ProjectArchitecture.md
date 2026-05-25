# Architecture

Structured counterpart: [obsidian/ProjectArchitecture.md](obsidian/ProjectArchitecture.md) (`ProjectArchitectureModel` in [docs-schema.ts](../../docs-schema.ts)).

## Layers

### Edge & clients

- **Description:** Clients and other services call this microservice over HTTP.  
- **Color:** `#4c566a`  
- **Expanded:** yes  

**Components**

- Admin consoles and future SPA  
- Other microservices (service-to-service REST)  

**Responsibilities**

- Expose JWT-protected REST APIs  

**Technologies**

- HTTPS  
- JWT Bearer tokens  

---

### Application runtime

- **Description:** Account Service Spring Boot process.  
- **Color:** `#5e81ac`  

**Components:** Controllers (Auth, User); Services & security configuration; JPA repositories  

**Responsibilities:** Identity and access for the Architecture College platform.  

**Technologies:** Spring Web; Spring Data JPA; Spring Security  

---

### Data & platform integration

- **Description:** Persistence and operational registration.  
- **Color:** `#a3be8c`  

**Components:** PostgreSQL `account_db`; Eureka client registration; Config Server client  

**Technologies:** PostgreSQL 16; Spring Cloud Netflix Eureka; Spring Cloud Config  

---

## Design patterns

| Title | Emoji | Badge | Category | Description |
| --- | --- | --- | --- | --- |
| Database per service | DB | Core | Microservices | Account data lives only in `account_db`. |
| Token-based API security | JWT | Identity | Security | Issue JWTs at login; peers validate the same key. |

## Scalability strategies

- **Stateless instances:** Scale replicas behind a load balancer.  
- **Dedicated database:** Tune `account_db` independently.  

## Security strategies

- **JWT propagation:** This service mints tokens; consumers validate signature, expiry, and roles.  
- **Secrets not in Git:** JWT secret and DB passwords from env / secret manager in production.  

## Cache strategies

| Name | TTL | Coverage | Description |
| --- | --- | --- | --- |
| No default application cache | n/a | None today | Optional Redis later for blocklists or rate limits. |

## Architecture features

- **Spring Cloud discovery (Eureka):** Registers for dynamic lookup.  
- **Externalized configuration:** `config-data/account-service.yml` via Config Server.  

## Architecture diagram

### Legend

| Type | Label | Color | Icon |
| --- | --- | --- | --- |
| service | Microservice | `#5e81ac` | hex |
| database | Database | `#a3be8c` | cylinder |

### Nodes (`DiagramNode`)

| id | label | type | x | y | status |
| --- | --- | --- | --- | --- | --- |
| client | Clients | client | 10 | 20 | healthy |
| account_service | Account Service | service | 45 | 20 | healthy |
| postgres | PostgreSQL (account_db) | database | 80 | 20 | healthy |

### Connections (`DiagramConnection`)

| id | from | to | label | protocol | active |
| --- | --- | --- | --- | --- | --- |
| c1 | client | account_service | REST | HTTP | yes |
| c2 | account_service | postgres | SQL | JDBC | yes |

## Data flow

### Request flow (`FlowStep[]`)

| # | Title | Description | Icon |
| --- | --- | --- | --- |
| 1 | Signup or login | Client posts to `/v1/api/auth/*`. | user |
| 2 | Validate and persist | Validates input, persists user; login returns JWT. | database |
| 3 | Authenticated calls | Clients pass JWT on protected routes. | shield |

### Event flow

| # | Title | Description |
| --- | --- | --- |
| 1 | No domain events in v1 | Focus on synchronous HTTP; optional outbox later. |

## Technical decisions (`TechDecisionModel[]`)

### Spring Cloud Netflix (Eureka + Config)

- **Problem:** Multiple JVM services need discovery and configuration.  
- **Solution:** Eureka + Spring Cloud Config with `config-data` YAML.  
- **Outcome:** Parity between local Compose and deployment.  
- **Alternatives:** Kubernetes DNS + ConfigMaps; Consul.  
- **icon:** compass  

## Watch items

- Rotate JWT signing keys per environment.  
- Narrow Actuator exposure in production.  
- Align password hashing with institutional policy.  
