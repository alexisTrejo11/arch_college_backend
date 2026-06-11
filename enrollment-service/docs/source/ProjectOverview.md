---
problemStatement:
  problemTitle: "Why Enrollment Service exists"
  problemDescription: "Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path."
  problemList:
    - "Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Polyglot persistence"
      description: "PostgreSQL holds authoritative enrollment rows; MongoDB stores preload process state and auxiliary documents."
    - title: "Dedicated REST surfaces"
      description: "Command vs finder controllers separate writes from reads; student self-service has its own routes."
    - title: "Bulk preload APIs"
      description: "Students, subjects, schedules, and grades each expose preload + status + clear lifecycles for operators."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8087 in root docker-compose.yml."
    - "JDBC to PostgreSQL enrollment_db; Mongo URI for document collections."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/enrollment-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs with production captures when available."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-enrollment-service.png"
      thumbnail: ""
      title: "Enrollment Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-enrollment-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8087"
    description: "Host mapping in docker-compose"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Enrollment Service** coordinates **group enrollments**, **student self-service** enroll/drop, **lock-date** rules, and **bulk preload** operations with status tracked in MongoDB.

## Authoring notes

- Several controllers use `@RequestMapping("v1/api/...")` **without** a leading slash — document canonical paths as **`/v1/api/...`** at the gateway.
- Define which store is **source of truth** per aggregate (Postgres vs Mongo) in runbooks to avoid split-brain reads.
