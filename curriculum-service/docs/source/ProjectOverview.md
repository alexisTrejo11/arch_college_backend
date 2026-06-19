---
problemStatement:
  problemTitle: "Why Curriculum Service exists"
  problemDescription: "Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume."
  problemList:
    - "Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Bounded REST resources"
      description: "Controllers map to aggregates: careers, areas, professional lines, obligatory/elective subjects, subject series."
    - title: "Single relational catalog"
      description: "Postgres curriculum_db is the source of truth; no shared tables with other services."
    - title: "Rich read shapes"
      description: "Queries by area, career, semester, and series support admin tooling and downstream services."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8085 in root docker-compose.yml."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."
    - "Isolated database curriculum_db for all catalog entities."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview image"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/curriculum-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs after capturing real assets."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-curriculum-service.png"
      thumbnail: ""
      title: "Curriculum Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-curriculum-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8085"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Curriculum Service** is the **academic catalog** for the platform: careers, areas, professional lines, subjects, and how series link offerings together.

## Authoring notes

- Some controllers omit a leading `/` on `@RequestMapping` — normalize at an API gateway for public docs.
- Verify **`springdoc.packages-to-scan`** in `config-data/curriculum-service.yml` matches `io.github.alexistrejo11...` so Swagger discovers all controllers.
