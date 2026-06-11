---
layers:
  - name: "Edge & clients"
    description: "Admin tools, student and teacher apps, and peer services call Grade Service over HTTPS."
    color: "#4c566a"
    expanded: true
    components:
      - "Authenticated clients (JWT)"
      - "Internal microservice callers"
    responsibilities:
      - "Propagate bearer tokens issued by account-service"
    technologies:
      - "HTTPS"
      - "JWT"

  - name: "Application runtime"
    description: "Grade Service Spring Boot process"
    color: "#5e81ac"
    expanded: true
    components:
      - "Grade query/command controllers"
      - "Group and teacher grading controllers"
      - "Academic history controllers"
      - "Security, validation, rate limiting"
    responsibilities:
      - "Grades, groups, teacher workflows, student read models"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Data MongoDB"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "PostgreSQL grade_db, MongoDB grade_db, RabbitMQ"
    color: "#a3be8c"
    expanded: true
    components:
      - "Relational grades and relationships"
      - "Document projections where used"
      - "AMQP exchanges/queues for history refresh"
    responsibilities:
      - "Durable grades; async integration"
    technologies:
      - "PostgreSQL 16"
      - "MongoDB 7"
      - "RabbitMQ 3"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "grade_db (SQL + document URI) owned by this service; no cross-service table sharing."
    category: "Microservices"
    badge: "Core"
  - title: "CQSP-style splits"
    emoji: "API"
    description: "Separate query and command controllers for grades reduce accidental coupling on writes."
    category: "API"
    badge: "Layering"

scalabilityStrategies:
  - title: "Stateless HTTP tier"
    description: "Scale Boot instances; JWT carries identity; no server session affinity required."
  - title: "Tune brokers and DB pools"
    description: "Rabbit consumers and JDBC pool sizes scale with traffic separately."

securityStrategies:
  - title: "JWT validation"
    description: "Resource server validates tokens; teacher/student routes derive account from the JWT."
  - title: "Role-gated admin routes"
    description: "Academic history admin endpoint uses `@PreAuthorize(\"hasRole('ADMIN')\")`."

cacheStrategies:
  - name: "None by default"
    description: "Add Redis or CDN if read-heavy catalog or history endpoints need it."
    ttl: "n/a"
    coverage: "Future"

architectureFeatures:
  - title: "Spring Cloud discovery"
    emoji: "EU"
    description: "Eureka client registration from config."
  - title: "Centralized configuration"
    emoji: "CFG"
    description: "config-data/grade-service.yml via Config Server."

architectureDiagram:
  legendItems:
    - type: "service"
      label: "Microservice"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "SQL / document"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "Broker"
      color: "#ebcb8b"
      icon: "mail"
  nodes:
    - id: "client"
      label: "Clients"
      type: "client"
      x: 10
      y: 22
      status: "healthy"
      traffic: 100
    - id: "grade_service"
      label: "Grade Service"
      type: "service"
      x: 38
      y: 22
      status: "healthy"
      traffic: 85
    - id: "postgres"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 12
      status: "healthy"
    - id: "mongodb"
      label: "MongoDB"
      type: "database"
      x: 68
      y: 28
      status: "healthy"
    - id: "rabbit"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 44
      status: "healthy"
  connections:
    - id: "c1"
      from: "client"
      to: "grade_service"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "grade_service"
      to: "postgres"
      label: "JDBC"
      protocol: "SQL"
      isActive: true
    - id: "c3"
      from: "grade_service"
      to: "mongodb"
      label: "ODM"
      protocol: "Mongo"
      isActive: true
    - id: "c4"
      from: "grade_service"
      to: "rabbit"
      label: "AMQP"
      protocol: "AMQP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Authenticate"
      description: "Client presents JWT from account-service where required."
      icon: "shield"
    - number: 2
      title: "Invoke API"
      description: "Controller validates input and delegates to domain services."
      icon: "globe"
    - number: 3
      title: "Persist / project"
      description: "Writes hit PostgreSQL; Mongo or messaging may update read models asynchronously."
      icon: "database"
  eventFlow:
    - number: 1
      title: "Domain signal"
      description: "Grade or history changes may publish or consume AMQP messages."
      icon: "mail"
    - number: 2
      title: "Projection refresh"
      description: "Listeners update academic history or related aggregates."
      icon: "workflow"

techDecisions:
  decisions:
    - title: "Dual store"
      problem: "Some reads suit documents while grades stay relational."
      solution: "PostgreSQL for grades; Mongo URI configured for grade_db projections."
      outcome: "Flexibility without giving up transactional grade rows."
      icon: "layer"
      alternatives:
        - "Postgres-only JSON columns for all projections"
        - "Event sourcing with separate read stack"
    - title: "Netflix Eureka + Config"
      problem: "Platform already standardizes on Spring Cloud Netflix."
      solution: "Reuse Eureka and Config Server for discovery and YAML."
      outcome: "Operational consistency across college microservices."
      icon: "compass"
      alternatives:
        - "Kubernetes service DNS only"
        - "Consul"
---

# Architecture

**Grade Service** is a Spring Boot 3.3 module with **Eureka**, **Config Server**, **PostgreSQL**, **MongoDB**, and **RabbitMQ** as in Compose.

## Watch items

- **`AcademicHistoryController`** maps to `v1/api/academic-histories` (no leading `/`). Treat externally as **`/v1/api/academic-histories`** after gateway normalization.
- **Ownership of read models:** clarify which store is authoritative per query path when troubleshooting discrepancies between SQL and Mongo-backed views.
