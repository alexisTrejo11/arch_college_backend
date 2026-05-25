---
layers:
  - name: "Clients"
    description: "Student portals, admins, and other services invoking enrollment flows."
    color: "#4c566a"
    expanded: true
    components:
      - "SPA / admin UI"
      - "Schedule, curriculum, student services (HTTP)"
    responsibilities:
      - "Submit enrollments, query state, trigger preloads"
    technologies:
      - "HTTPS"
      - "JWT (when secured)"

  - name: "Application runtime"
    description: "Enrollment Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "Enrollment command & finder controllers"
      - "Student enrollment controller"
      - "Preload controllers (student, subject, schedule, grade)"
      - "Domain services, repositories"
    responsibilities:
      - "Registration workflows bridging students and class groups"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Data MongoDB"

  - name: "Persistence"
    description: "Transactional store plus document store for job state."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL enrollment_db"
      - "MongoDB (preload / projection collections)"
      - "Eureka + Config Server clients"
    responsibilities:
      - "ACID enrollments; durable preload status and blobs"
    technologies:
      - "PostgreSQL 16"
      - "MongoDB 7"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "enrollment_db schema is owned here; no foreign keys to other services' tables."
    category: "Microservices"
    badge: "Core"
  - title: "Polyglot persistence"
    emoji: "DOC"
    description: "OLTP in Postgres; Mongo for flexible preload documents and progress tracking."
    category: "Data"
    badge: "CQRS-flavored"

scalabilityStrategies:
  - title: "Scale stateless API tier"
    description: "Horizontal replicas with sticky-less load balancing."
  - title: "Isolate bulk preload load"
    description: "Throttle or queue heavy preloads so OLTP enrollment latency stays predictable."

securityStrategies:
  - title: "Student vs admin routes"
    description: "Different route families for self-service vs operator preload; enforce with Spring Security + gateway."
  - title: "Secrets for both datastores"
    description: "RDS/Atlas credentials via environment or secret manager — never committed."

cacheStrategies:
  - name: "None by default"
    description: "Consider Redis for idempotency keys or rate limits on enrollment spikes."
    ttl: "n/a"
    coverage: "Future"

architectureFeatures:
  - title: "Discovery + external config"
    emoji: "EU"
    description: "Eureka registration; YAML from config-data/enrollment-service.yml."
  - title: "Bulk operation APIs"
    emoji: "JOB"
    description: "Preload controllers pattern for operational imports."

architectureDiagram:
  legendItems:
    - type: "service"
      label: "Service"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "SQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "database"
      label: "Mongo"
      color: "#d08770"
      icon: "leaf"
  nodes:
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 25
      status: "healthy"
      traffic: 100
    - id: "enrollment"
      label: "Enrollment Service"
      type: "service"
      x: 38
      y: 25
      status: "healthy"
      traffic: 90
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 72
      y: 18
      status: "healthy"
    - id: "mongo"
      label: "MongoDB"
      type: "database"
      x: 72
      y: 35
      status: "healthy"
  connections:
    - id: "c1"
      from: "client"
      to: "enrollment"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "enrollment"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "enrollment"
      to: "mongo"
      label: "Mongo driver"
      protocol: "TCP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Authorize request"
      description: "Gateway validates JWT/session before hitting enrollment APIs."
      icon: "shield"
    - number: 2
      title: "Execute use case"
      description: "Command or student controller validates business rules (capacity, lock date, duplicates)."
      icon: "globe"
    - number: 3
      title: "Persist"
      description: "Transactional writes to Postgres; preload metadata to Mongo when applicable."
      icon: "database"
  eventFlow:
    - number: 1
      title: "Preload lifecycle"
      description: "POST /preload starts work; GET /preload/{id}/status polls Mongo-backed state."
      icon: "mail"
    - number: 2
      title: "Downstream visibility"
      description: "Schedule/grade services read enrollment facts via HTTP or shared events (if added later)."
      icon: "workflow"

techDecisions:
  decisions:
    - title: "Postgres + Mongo in one service"
      problem: "Enrollments need ACID; bulk preload status needs flexible schema."
      solution: "JPA for enrollments; Mongo templates/repositories for process documents."
      outcome: "Operators get progress APIs without bloating relational tables."
      icon: "compass"
      alternatives:
        - "Single Postgres JSONB column for job state"
        - "External workflow engine (Temporal, Camunda)"
---

# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Consistency:** document which reads are **Postgres-authoritative** vs **Mongo** (preload only).  
- **Path normalization:** several mappings omit a leading `/` — enforce **`/v1/api/...`** externally.  
- **migrate vs ddl-auto:** prefer **Flyway/Liquibase** on Postgres outside dev.  
- **Mongo indexes** on preload query fields (`processId`, TTL if ephemeral jobs).  
