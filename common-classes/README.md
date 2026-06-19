# [Service Name] Service

[Service Name] Service is an internal microservice of the [Platform Name] monorepo.  
It owns [brief domain responsibility] with secure, validated, and observable APIs for both [user type] self-service and [admin type] operations.
## Table of Contents
- [Overview](#overview)
- [Core Capabilities](#core-capabilities)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [API Surface](#api-surface)
- [Security and Business Rules](#security-and-business-rules)
- [Observability](#observability)
- [Nginx Reverse Proxy and Load Balancer](#nginx-reverse-proxy-and-load-balancer) <!-- Omit if not used -->
- [Run Locally](#run-locally)
- [Docker and Full Local Stack](#docker-and-full-local-stack)
- [Testing](#testing)
- [Documentation Navigation](#documentation-navigation)
## Overview
- **Service name:** `[service-name]`
- **Role in platform:** [One sentence describing the service's role].
- **Main responsibility:** [Primary responsibility, e.g., "Create, read, update, delete X by user role"].
- **Protocol:** REST over HTTPS (or gRPC, GraphQL, etc.).
- **Persistence:** [Database name] with [migration tool, e.g., Flyway].
## Core Capabilities
<!-- List the main features this service provides -->
- [Capability 1, e.g., "Multi-country postal code validation"].
- [Capability 2, e.g., "Role-based limits (CUSTOMER and EMPLOYEE constraints)"].
- [Capability 3, e.g., "Default X management (single default per user)"].
- [Capability 4, e.g., "User vs Admin API separation through dedicated controllers"].
- [Capability 5, e.g., "Soft delete strategy for records"].
- [Capability 6, e.g., "JWT-authenticated endpoints with role-based authorization"].
- [Capability 7, e.g., "Redis-backed rate limiting with different sensitivity profiles"].
## Tech Stack
- Java [version]
- Spring Boot [version]
- [Spring modules used, e.g., Spring Web, Spring Security, Spring Data JPA]
- [Database, e.g., PostgreSQL 15]
- [Cache, e.g., Redis 7]
- [Migration tool, e.g., Flyway]
- [API docs, e.g., Springdoc OpenAPI]
- [Observability, e.g., Actuator + Micrometer + Prometheus, Loki4j + Loki + Grafana]
- [Reverse proxy, e.g., Nginx 1.27] <!-- Omit if not used -->
- Docker / Docker Compose
## Project Structure
```text
[service-name]/
├── src/
│   ├── main/
│   │   ├── java/[package-path]/
│   │   │   ├── controller/        # User and admin REST controllers
│   │   │   ├── service/           # Business orchestration
│   │   │   ├── repository/        # Data repositories
│   │   │   ├── entity/            # Persistence models
│   │   │   ├── config/            # Security, OpenAPI, rate-limit configs
│   │   │   └── utils/             # DTOs, mappers, validators
│   │   └── resources/
│   │       ├── application.yml
│   │       ├── application-docker.yml
│   │       ├── logback-spring.xml
│   │       └── db/migration/
│   └── test/
├── nginx/                          # Omit if not using Nginx
│   ├── nginx.conf
│   └── ssl/
│       ├── generate-certs.sh
│       ├── .gitignore
├── observability/                  # Omit if not using observability stack
│   ├── prometheus/
│   └── grafana/provisioning/datasources/
├── docs/                           # Per-service only (see each *-service/docs/)
│   ├── source/                    # YAML frontmatter sources
│   └── generated/                 # Human-readable output (yaml_to_markdown.py)
├── Dockerfile
├── docker-compose.yml
├── build.gradle                    # or pom.xml, package.json, etc.
└── README.md

```

## API Surface

- **User base path:** `/api/v2/user/[resource]`
    
- **Admin base path:** `/api/v2/[resource]/admin`
    
- [Brief description of main operations: CRUD, default controls, admin management]
    
- OpenAPI UI available at runtime through Swagger configuration.
## Security and Business Rules

- JWT Bearer authentication required for protected routes.
    
- Authorization rules separated by route groups (user vs admin).
    
- Input validation with Jakarta Validation + domain rules.
    
- [Domain-specific validation rules, e.g., "Postal code format validation by country"]
    
- Rate limiting with Redis (profiles: `STANDARD`, `SENSITIVE`, etc.).
    
- [Deletion strategy, e.g., "Soft delete: records are marked inactive, not hard deleted"]
## Observability
<!-- Omit sections not applicable -->

- Actuator endpoints expose health, info, and Prometheus metrics.
    
- Common metrics tags configured for easier dashboard filtering.
    
- Logback forwards logs to Loki in non-test profiles.
    
- Tracing sampling configured for full visibility.
    
- Grafana and Prometheus are provisioned through Docker Compose.


## Nginx Reverse Proxy and Load Balancer

<!-- Omit this entire section if the service does not use Nginx -->

All Docker traffic reaches the service through **Nginx** — the Spring Boot port `[port]` is no longer bound to the host.

### Architecture

```
text

Client
  │
  ├─ HTTP  :80  ──► Nginx ──► 301 redirect to HTTPS
  │
  └─ HTTPS :443 ──► Nginx (TLS termination)
                      │   least_conn load balancing
                      ├──► [service-name] replica 1 :[port]
                      ├──► [service-name] replica 2 :[port]
                      └──► [service-name] replica N :[port]
```
### Files

| Path                          | Purpose                                                          |
| ----------------------------- | ---------------------------------------------------------------- |
| `nginx/nginx.conf`            | Worker config, upstream block, HTTP→HTTPS redirect, HTTPS proxy. |
| `nginx/ssl/nginx.crt`         | TLS certificate (self-signed dev, gitignored).                   |
| `nginx/ssl/nginx.key`         | Private key (gitignored).                                        |
| `nginx/ssl/generate-certs.sh` | Script to generate self-signed cert.                             |
| `nginx/ssl/.gitignore`        | Prevents committing key material.                                |

### Generate the dev TLS certificate (first-time setup)

bash

cd nginx/ssl
chmod +x generate-certs.sh
./generate-certs.sh

### Scale the service (load balancing)

bash

docker compose up -d --scale [service-name]=[N]
docker compose ps

## Run Locally

Requirements:

- [Runtime, e.g., Java 23]
    
- Docker + Docker Compose (recommended for full stack)
    

Application only:

bash

./gradlew bootRun   # or mvn spring-boot:run, npm run dev, etc.

Run tests:

bash

./gradlew test

## Docker and Full Local Stack
From `[service-name]/`:

```bash

# 1. Generate Nginx TLS cert (first time only, if using Nginx)
cd nginx/ssl && ./generate-certs.sh && cd ../..
# 2. Build and start the full stack (single replica)
docker compose up -d --build
# 3. Or start with N replicas for load balancing (if using Nginx)
docker compose up -d --build --scale [service-name]=[N]
```

Key endpoints (all external traffic via Nginx if used, otherwise direct):

| Endpoint              | URL                                        |
| --------------------- | ------------------------------------------ |
| API (via Nginx HTTPS) | `https://localhost/api/v2/user/[resource]` |
| Actuator health       | `https://localhost/actuator/health`        |
| Prometheus metrics    | `https://localhost/actuator/prometheus`    |
| Prometheus UI         | `http://localhost:9090`                    |
| Loki ready            | `http://localhost:3100/ready`              |
| Grafana UI            | `http://localhost:3000`                    |

> **Note:** `[service-name]:[port]` is no longer exposed to the host if using Nginx. All API access goes through Nginx on port `443`. Adjust accordingly if not using Nginx.

## Testing

- Unit and integration tests are under `src/test/`.
    
- `application-test.yml` provides test profile configuration.
    
- Recommended: run tests before opening PRs in the monorepo.
    

## Documentation Navigation

This library has no local `docs/` folder. Service documentation lives under each microservice:

| Service | Generated docs |
| --- | --- |
| `account-service` | [../account-service/docs/generated/](../account-service/docs/generated/) |
| `student-service` | [../student-service/docs/generated/](../student-service/docs/generated/) |
| `teacher-service` | [../teacher-service/docs/generated/](../teacher-service/docs/generated/) |
| `curriculum-service` | [../curriculum-service/docs/generated/](../curriculum-service/docs/generated/) |
| `schedule-service` | [../schedule-service/docs/generated/](../schedule-service/docs/generated/) |
| `enrollment-service` | [../enrollment-service/docs/generated/](../enrollment-service/docs/generated/) |
| `grade-service` | [../grade-service/docs/generated/](../grade-service/docs/generated/) |

Platform overview: [../README.md](../README.md#documentation--schema).
    

---

If this service changes its API contract, domain rules, or observability setup, update the relevant service `docs/source/` files and this `README.md` in the same PR.