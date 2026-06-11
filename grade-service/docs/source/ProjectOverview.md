---
problemStatement:
  problemTitle: "Why Grade Service exists"
  problemDescription: "Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work."
  problemList:
    - "Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Split controllers by audience"
      description: "Admin-grade queries, teacher group grading, and student self-service reads each have focused routes."
    - title: "Postgres + Mongo"
      description: "Transactional grade rows in PostgreSQL; Mongo supports projections and document-oriented reads where configured."
    - title: "RabbitMQ"
      description: "Async hooks (e.g. academic history refresh) decouple heavy updates from the request path."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8088 in root docker-compose.yml."
    - "PostgreSQL grade_db; MongoDB grade_db; RabbitMQ from compose env."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/grade-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs when production assets exist."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-grade-service.png"
      thumbnail: ""
      title: "Grade Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-grade-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8088"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Grade Service** owns **grade queries and lifecycle** (authorize, soft delete), **teacher grading** for groups, **student-facing academic history** endpoints, and **admin academic history** access, with **Postgres**, **MongoDB**, and **RabbitMQ** in the deployed stack.

## Authoring notes

- **`AcademicHistoryController`** uses `@RequestMapping("v1/api/academic-histories")` without a leading slash—normalize at the gateway to **`/v1/api/...`** for public docs.
- Keep **JWT signing** aligned with **account-service** for resource-server validation.
