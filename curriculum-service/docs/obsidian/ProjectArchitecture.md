---
layers:
  - name: "Clients"
    description: "Admin tools, internal services (enrollment, schedule) calling the catalog over REST."
    color: "#4c566a"
    expanded: true
    components:
      - "Back-office UIs"
      - "Other microservices (Feign/RestTemplate/WebClient)"
    responsibilities:
      - "Consume stable catalog identifiers and relationships"
    technologies:
      - "HTTPS"
      - "Optional JWT from account-service"

  - name: "Application runtime"
    description: "Curriculum Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "REST controllers (career, area, professional line, subjects, series)"
      - "Domain services and validation"
      - "JPA repositories"
    responsibilities:
      - "Academic catalog source of truth for the platform"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Jakarta Validation"

  - name: "Persistence & platform"
    description: "Datastore and cloud-native hooks."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL curriculum_db"
      - "Eureka client"
      - "Config Server client"
    responsibilities:
      - "Persist normalized curriculum graph; register with discovery"
    technologies:
      - "PostgreSQL 16"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "Only curriculum_db holds catalog tables; no cross-schema joins with enrollment or schedule."
    category: "Microservices"
    badge: "Core"
  - title: "Aggregate-oriented APIs"
    emoji: "API"
    description: "One controller family per aggregate reduces coupling and keeps URLs predictable."
    category: "API"
    badge: "DDD-friendly"

scalabilityStrategies:
  - title: "Stateless horizontal scale"
    description: "Read-heavy catalog scales with replica count and DB read tuning."
  - title: "Optional read caching later"
    description: "CDN or Redis for hot GET paths (e.g. full career trees) if metrics justify it."

securityStrategies:
  - title: "Gateway + service auth"
    description: "Protect mutating routes in production; align with institutional RBAC."
  - title: "Least-privilege DB role"
    description: "DB user limited to curriculum_db only."

cacheStrategies:
  - name: "Not enabled by default"
    description: "Add application or edge cache when profiling shows catalog hot spots."
    ttl: "TBD"
    coverage: "Future"

architectureFeatures:
  - title: "Eureka registration"
    emoji: "EU"
    description: "Service instances advertise health to peers."
  - title: "Externalized YAML"
    emoji: "CFG"
    description: "Behavior tuned via config-data/curriculum-service.yml."

architectureDiagram:
  legendItems:
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
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
    - id: "curriculum_service"
      label: "Curriculum Service"
      type: "service"
      x: 45
      y: 20
      status: "healthy"
      traffic: 85
    - id: "postgres"
      label: "PostgreSQL (curriculum_db)"
      type: "database"
      x: 80
      y: 20
      status: "healthy"
  connections:
    - id: "c1"
      from: "client"
      to: "curriculum_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "curriculum_service"
      to: "postgres"
      label: "SQL"
      protocol: "JDBC"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Resolve catalog request"
      description: "Client calls GET/POST/PUT/DELETE on /v1/api/careers, /areas, /subjects/*, etc."
      icon: "globe"
    - number: 2
      title: "Validate and persist"
      description: "Controllers validate payloads and persist via JPA to curriculum_db."
      icon: "database"
    - number: 3
      title: "Downstream consumption"
      description: "Enrollment and schedule services use returned ids and keys in their own data."
      icon: "workflow"
  eventFlow:
    - number: 1
      title: "Synchronous integration"
      description: "This service does not publish AMQP by default; peers integrate via REST."
      icon: "link"
    - number: 2
      title: "Future eventing"
      description: "Optional domain events (e.g. catalog change) can be added with an outbox pattern."
      icon: "mail"

techDecisions:
  decisions:
    - title: "Relational model for curriculum graph"
      problem: "Careers, areas, and subjects need constraints and joins for integrity."
      solution: "PostgreSQL + JPA entities with explicit relationships."
      outcome: "Enrollment and scheduling can trust foreign keys and uniqueness in curriculum_db."
      icon: "compass"
      alternatives:
        - "Document store for entire catalog tree"
        - "Graph database for prerequisite chains"
---

# Architecture

Aligns with **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **`SubjectSeriesController`** uses `@RequestMapping("v1/api/...")` without a leading slash — align with `/v1/api/...` at the gateway.
- **`config-data/curriculum-service.yml`** may still reference a legacy `springdoc.packages-to-scan` value — confirm Swagger lists all controllers before release.
- Planning **migrations** (Flyway/Liquibase) is safer than relying on `ddl-auto` in non-dev environments.
