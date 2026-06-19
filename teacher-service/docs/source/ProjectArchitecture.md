---
layers:
  - name: "Clients"
    description: "Schedule, grade, admin, and account-aligned callers."
    color: "#4c566a"
    expanded: true
    components:
      - "Internal microservices"
      - "Authenticated gateways"
    responsibilities:
      - "Manage faculty with JWT-protected routes"
    technologies:
      - "HTTPS"
      - "JWT Bearer"

  - name: "Application runtime"
    description: "Teacher Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "TeacherCommandController"
      - "TeacherQueryController"
      - "Security configuration"
      - "JPA repositories"
    responsibilities:
      - "Teacher aggregate backing scheduling and grading"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring Security"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Relational store and broker."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL teacher_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Persist teachers; integration events"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "teacher_db owned solely by this service."
    category: "Microservices"
    badge: "Core"
  - title: "Strangler path normalization"
    emoji: "GW"
    description: "Gateway maps legacy /teachers reads to versioned public paths."
    category: "Integration"
    badge: "Gateway"

scalabilityStrategies:
  - title: "Stateless API replicas"
    description: "Scale JVMs; no session stickiness required."
  - title: "Rabbit consumer tuning"
    description: "Scale listeners or partition workloads by routing key if needed."

securityStrategies:
  - title: "Shared JWT signing key"
    description: "Must match account-service secret; never commit production values."
  - title: "Least privilege DB credentials"
    description: "teacher_db role only."

cacheStrategies:
  - name: "None default"
    description: "Optional cache for validate-account hot path."
    ttl: "TBD"
    coverage: "Future"

architectureFeatures:
  - title: "Discovery + config"
    emoji: "EU"
    description: "Eureka + config-data/teacher-service.yml."
  - title: "AMQP"
    emoji: "MQ"
    description: "Integration with peer services."

architectureDiagram:
  legendItems:
    - type: "service"
      label: "Service"
      color: "#5e81ac"
      icon: "hex"
    - type: "database"
      label: "PostgreSQL"
      color: "#a3be8c"
      icon: "cylinder"
    - type: "queue"
      label: "RabbitMQ"
      color: "#d08770"
      icon: "queue"
  nodes:
    - id: "client"
      label: "Clients"
      type: "client"
      x: 8
      y: 28
      status: "healthy"
      traffic: 100
    - id: "teacher"
      label: "Teacher Service"
      type: "service"
      x: 36
      y: 28
      status: "healthy"
      traffic: 85
    - id: "pg"
      label: "PostgreSQL"
      type: "database"
      x: 68
      y: 20
      status: "healthy"
    - id: "mq"
      label: "RabbitMQ"
      type: "queue"
      x: 68
      y: 38
      status: "healthy"
  connections:
    - id: "c1"
      from: "client"
      to: "teacher"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "teacher"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "teacher"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Authenticate"
      description: "JWT validated for protected routes."
      icon: "shield"
    - number: 2
      title: "Invoke teacher API"
      description: "Commands /v1/api/teachers or queries /teachers."
      icon: "globe"
    - number: 3
      title: "Persist / integrate"
      description: "JPA to teacher_db; optional AMQP publish."
      icon: "database"
  eventFlow:
    - number: 1
      title: "Teacher lifecycle signal"
      description: "Messages on create/delete or other events (per wiring)."
      icon: "send"
    - number: 2
      title: "Downstream reaction"
      description: "Schedule/grade adjust projections."
      icon: "workflow"

techDecisions:
  decisions:
    - title: "Split command and query base paths"
      problem: "Legacy read API used /teachers without version prefix."
      solution: "TeacherCommandController under /v1/api/teachers; TeacherQueryController under /teachers."
      outcome: "Backward compatibility internally; gateway should unify externally."
      icon: "compass"
      alternatives:
        - "Refactor query controller to /v1/api/teachers (breaking change)"
        - "Single controller with explicit versioning headers"
---

# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Unify public paths** for Swagger and clients (`/v1/api/teachers` recommended).  
- **JWT key drift** between account-service and teacher-service causes **intermittent 401s**.  
- **Rabbit** topology as code; monitor DLQs.  
