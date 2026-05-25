---
layers:
  - name: "Edge & clients"
    description: "Clients and other services call this microservice over HTTP."
    color: "#4c566a"
    expanded: true
    components:
      - "Admin consoles and future SPA"
      - "Other microservices (service-to-service REST)"
    responsibilities:
      - "Expose JWT-protected REST APIs"
    technologies:
      - "HTTPS"
      - "JWT Bearer tokens"

  - name: "Application runtime"
    description: "Account Service Spring Boot process."
    color: "#5e81ac"
    expanded: true
    components:
      - "Controllers (Auth, User)"
      - "Services & security configuration"
      - "JPA repositories"
    responsibilities:
      - "Identity and access for the Architecture College platform."
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Security"

  - name: "Data & platform integration"
    description: "Persistence and operational registration."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL account_db"
      - "Eureka client registration"
      - "Config Server client"
    responsibilities:
      - "Store users and credentials; register healthy instances"
    technologies:
      - "PostgreSQL 16"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "Account data lives only in account_db; no shared user tables with other services."
    category: "Microservices"
    badge: "Core"
  - title: "Token-based API security"
    emoji: "JWT"
    description: "Issue JWTs at login; other services validate the same signing key."
    category: "Security"
    badge: "Identity"

scalabilityStrategies:
  - title: "Stateless instances"
    description: "Scale out multiple Account Service replicas behind a load balancer; no server session affinity."
  - title: "Dedicated database"
    description: "Tune account_db independently from curriculum or enrollment workloads."

securityStrategies:
  - title: "JWT propagation"
    description: "This service mints tokens; consumers must validate signature, expiry, and roles."
  - title: "Secrets not in Git"
    description: "JWT secret and DB passwords come from environment / secret manager in real deployment."

cacheStrategies:
  - name: "No default application cache"
    description: "Optional Redis if token blocklists or rate limiting are added later."
    ttl: "n/a"
    coverage: "None today"

architectureFeatures:
  - title: "Spring Cloud discovery"
    emoji: "Eureka"
    description: "Registers with Eureka for dynamic lookup."
  - title: "Externalized configuration"
    emoji: "Config"
    description: "Uses config-data/account-service.yml via Config Server."

architectureDiagram:
  legendItems:
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "Database"
      color: "#a3be8c"
      icon: "cylinder"
  nodes:
    - id: "client"
      label: "Clients"
      type: "client"
      x: 10
      y: 20
      status: "healthy"
      traffic: 100
    - id: "account_service"
      label: "Account Service"
      type: "service"
      x: 45
      y: 20
      status: "healthy"
      traffic: 80
    - id: "postgres"
      label: "PostgreSQL (account_db)"
      type: "database"
      x: 80
      y: 20
      status: "healthy"
  connections:
    - id: "c1"
      from: "client"
      to: "account_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "account_service"
      to: "postgres"
      label: "SQL"
      protocol: "JDBC"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Signup or login"
      description: "Client posts credentials or registration payload to /v1/api/auth/*."
      icon: "user"
    - number: 2
      title: "Validate and persist"
      description: "Service validates input, persists user, and for login returns JWT."
      icon: "database"
    - number: 3
      title: "Authenticated calls"
      description: "Clients pass JWT to this or other services for protected routes."
      icon: "shield"
  eventFlow:
    - number: 1
      title: "No domain events in v1"
      description: "Account Service focuses on synchronous HTTP; optional outbox can be added later."
      icon: "mail"

techDecisions:
  decisions:
    - title: "Spring Cloud Netflix (Eureka + Config)"
      problem: "Multiple JVM services need discovery and consistent configuration."
      solution: "Use Eureka registration and Spring Cloud Config with config-data YAML."
      outcome: "Operational parity between local Compose and target deployment."
      icon: "compass"
      alternatives:
        - "Kubernetes-only DNS + ConfigMaps"
        - "Consul for discovery and KV config"
---

# Architecture

Narrative above aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- Rotate **JWT** signing keys per environment; never ship default keys.
- Narrow **Actuator** endpoint exposure in production (`management.endpoints.web.exposure`).
- Keep **password hashing** and validation rules in sync with institutional policy.
