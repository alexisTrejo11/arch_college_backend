---
layers:
  - name: "Clients"
    description: "Admin portals, enrollment, grading, and other services."
    color: "#4c566a"
    expanded: true
    components:
      - "Internal REST clients"
      - "Future BFF / gateway"
    responsibilities:
      - "Read and mutate student records within auth policy"
    technologies:
      - "HTTPS"
      - "JWT when enforced"

  - name: "Application runtime"
    description: "Student Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "StudentCommandController"
      - "StudentQueryController"
      - "Services and JPA repositories"
    responsibilities:
      - "Student aggregate for the Architecture College platform"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Persistence plus broker."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL student_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Durable students; publish consumption-friendly signals"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "student_db is owned only by this service."
    category: "Microservices"
    badge: "Core"
  - title: "CQRS-style HTTP split"
    emoji: "CQRS"
    description: "Separate controllers for commands and queries."
    category: "API"
    badge: "Separation"

scalabilityStrategies:
  - title: "Scale stateless replicas"
    description: "Horizontal scaling behind load balancer."
  - title: "Tune broker consumers"
    description: "Prefetch and listener concurrency for peak registration periods."

securityStrategies:
  - title: "Authenticated mutations"
    description: "Align write routes with Spring Security and gateway policies."
  - title: "PII handling"
    description: "Student data — minimize logging; encrypt at rest where required."

cacheStrategies:
  - name: "Optional read cache"
    description: "Cache filter-heavy GET /by responses if profiling demands it."
    ttl: "TBD"
    coverage: "Future"

architectureFeatures:
  - title: "Discovery + config"
    emoji: "EU"
    description: "Eureka + config-data/student-service.yml."
  - title: "AMQP integration"
    emoji: "MQ"
    description: "Spring AMQP for cross-service workflows."

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
    - id: "student"
      label: "Student Service"
      type: "service"
      x: 36
      y: 28
      status: "healthy"
      traffic: 88
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
      to: "student"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "student"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "student"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "REST call"
      description: "Clients hit /v1/api/students for reads or writes."
      icon: "globe"
    - number: 2
      title: "Business rules"
      description: "Validation, professional line rules, semester increments."
      icon: "check"
    - number: 3
      title: "Persist and notify"
      description: "JPA commit; optional message publish to Rabbit."
      icon: "database"
  eventFlow:
    - number: 1
      title: "Emit"
      description: "Student lifecycle events on Rabbit (as wired)."
      icon: "send"
    - number: 2
      title: "Consume"
      description: "Peers refresh projections or trigger workflows."
      icon: "workflow"

techDecisions:
  decisions:
    - title: "Command/query controllers"
      problem: "Write models differ from read traffic patterns."
      solution: "StudentCommandController vs StudentQueryController."
      outcome: "Clearer evolution of validation and query optimization."
      icon: "compass"
      alternatives:
        - "Single controller class"
        - "GraphQL student API"
---

# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Idempotency** on progression endpoints if retried by clients.  
- **Rabbit** topology and dead-letter strategy for failed consumers.  
- **ddl-auto** vs migrations outside dev.  
