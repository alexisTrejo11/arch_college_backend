---
problemStatement:
  problemTitle: "Why Teacher Service exists"
  problemDescription: "Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens."
  problemList:
    - "Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Explicit validation endpoint"
      description: "Pre-validate teacher account numbers before expensive downstream flows."
    - title: "Command vs query APIs"
      description: "Writes under /v1/api/teachers; reads under /teachers (legacy path — normalize at gateway)."
    - title: "Broker-backed integration"
      description: "RabbitMQ keeps schedule/grade consumers loosely coupled (as implemented)."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8084 in docker-compose.yml."
    - "PostgreSQL teacher_db; RabbitMQ from compose."
    - "JWT secret must match account-service signing key in every environment."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/teacher-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs when assets exist."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-teacher-service.png"
      thumbnail: ""
      title: "Teacher Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-teacher-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8084"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Teacher Service** is the **faculty aggregate**: **create/delete**, **validate** account numbers, **batch get**, with **JWT** validation and **RabbitMQ**.

## Authoring notes

- **`TeacherQueryController`** maps to **`/teachers`** while commands use **`/v1/api/teachers`** — unify at **API gateway** (e.g. `/v1/api/teachers/...` everywhere).  
- **Rotate JWT secrets** with account-service in lockstep.  
