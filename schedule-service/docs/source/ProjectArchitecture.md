---
layers:
  - name: "Clients"
    description: "Admin scheduling tools, enrollment service, and internal consumers."
    color: "#4c566a"
    expanded: true
    components:
      - "Back-office UI"
      - "Enrollment and grade services"
    responsibilities:
      - "Create and query offerings; mutate rosters"
    technologies:
      - "HTTPS"
      - "JWT when secured"

  - name: "Application runtime"
    description: "Schedule Service Spring Boot application."
    color: "#5e81ac"
    expanded: true
    components:
      - "GroupCreationController"
      - "GroupFinderController"
      - "GroupUpdateController"
      - "GroupDeletionController"
      - "Domain services and JPA"
    responsibilities:
      - "Operational timetable and capacity management"
    technologies:
      - "Spring Web"
      - "Spring Data JPA"
      - "Spring AMQP"

  - name: "Data & messaging"
    description: "Relational state plus broker for integration."
    color: "#a3be8c"
    expanded: true
    components:
      - "PostgreSQL schedule_db"
      - "RabbitMQ"
      - "Eureka + Config Server clients"
    responsibilities:
      - "Durable groups; publish consumption-friendly events"
    technologies:
      - "PostgreSQL 16"
      - "RabbitMQ 3"
      - "Spring Cloud Netflix Eureka"
      - "Spring Cloud Config"

designPatterns:
  - title: "Database per service"
    emoji: "DB"
    description: "schedule_db contains scheduling aggregates only."
    category: "Microservices"
    badge: "Core"
  - title: "Command/query split"
    emoji: "CQRS"
    description: "/v1/api/groups vs /v1/api/finder/groups separates writes from read-optimized queries."
    category: "API"
    badge: "Read model"

scalabilityStrategies:
  - title: "Stateless API tier"
    description: "Scale replicas; offload heavy reporting with read replicas or cached projections if needed."
  - title: "Broker decoupling"
    description: "RabbitMQ absorbs peak fan-out to peers without synchronous chains."

securityStrategies:
  - title: "Role-aware mutations"
    description: "Restrict group mutations to operators; student-facing traffic should not hit dangerous routes."
  - title: "Least-privilege DB and broker creds"
    description: "Separate credentials per environment."

cacheStrategies:
  - name: "Optional catalog cache"
    description: "Cache hot finder responses (e.g. current term slice) if metrics justify it."
    ttl: "TBD"
    coverage: "Future"

architectureFeatures:
  - title: "Eureka + Config"
    emoji: "EU"
    description: "Discovery and externalized YAML from config-data/schedule-service.yml."
  - title: "AMQP integration"
    emoji: "MQ"
    description: "Spring AMQP templates/listeners as implemented in the module."

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
    - id: "schedule"
      label: "Schedule Service"
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
      to: "schedule"
      label: "REST"
      protocol: "HTTP"
      isActive: true
    - id: "c2"
      from: "schedule"
      to: "pg"
      label: "JDBC"
      protocol: "TCP"
      isActive: true
    - id: "c3"
      from: "schedule"
      to: "mq"
      label: "AMQP"
      protocol: "TCP"
      isActive: true

dataFlow:
  requestFlow:
    - number: 1
      title: "Invoke API"
      description: "Clients call creation, finder, or mutation endpoints under /v1/api/groups or /v1/api/finder/groups."
      icon: "globe"
    - number: 2
      title: "Validate and persist"
      description: "Controllers enforce rules; JPA commits to schedule_db."
      icon: "database"
    - number: 3
      title: "Integration signal"
      description: "Important changes may publish messages for enrollment or notifications."
      icon: "mail"
  eventFlow:
    - number: 1
      title: "Publish"
      description: "AMQP messages emitted on schedule/group lifecycle events (as wired in code)."
      icon: "send"
    - number: 2
      title: "Consume"
      description: "Peer services update projections or trigger workflows."
      icon: "workflow"

techDecisions:
  decisions:
    - title: "Separate finder base path"
      problem: "Read-heavy schedule queries should not compete with transactional endpoints."
      solution: "Expose /v1/api/finder/groups for lookups and slices."
      outcome: "Clearer caching and scaling story for reads."
      icon: "compass"
      alternatives:
        - "Single controller with mixed GET/PUT"
        - "GraphQL facade over groups"
---

# Architecture

Matches **`ProjectArchitectureModel`** in `docs-schema.ts`.

## Watch items

- **Concurrency** on capacity and roster fields — use transactions/version columns if conflicts appear in production.  
- **RabbitMQ** topology (exchanges, queues, DLQs) should be version-controlled or declarative.  
- **ddl-auto** in non-dev — prefer **Flyway/Liquibase** for `schedule_db`.  
