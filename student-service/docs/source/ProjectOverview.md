---
problemStatement:
  problemTitle: "Why Student Service exists"
  problemDescription: "Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling."
  problemList:
    - "Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Command vs query split"
      description: "StudentCommandController handles writes; StudentQueryController optimizes reads."
    - title: "Relational source of truth"
      description: "student_db stores canonical student aggregates under this service boundary."
    - title: "AMQP integration"
      description: "RabbitMQ propagates events for downstream sync (as implemented in the module)."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8083 in root docker-compose.yml."
    - "PostgreSQL student_db; RabbitMQ broker from compose env."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/student-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs when production assets exist."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-student-service.png"
      thumbnail: ""
      title: "Student Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-student-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8083"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Student Service** owns **student CRUD**, **lookups**, **professional line / modality**, and **semester completion** increments, with **messaging** for integration.

## Authoring notes

- Coordinate **professional line ids** with curriculum-service contract.  
- Document **exchange/queue** names if operators must troubleshoot Rabbit consumers.
