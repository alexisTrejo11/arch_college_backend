---
problemStatement:
  problemTitle: "Why Account Service exists"
  problemDescription: "Academic platforms need distinct onboarding paths per persona while sharing a single identity model."
  problemList:
    - "Academic platforms need distinct onboarding paths per persona while sharing a single identity model."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Segmented signup"
      description: "Dedicated endpoints for student, teacher, and admin registration with role-aware payloads."
    - title: "Centralized login"
      description: "Single /login path issuing JWT tokens consumed by downstream services."
    - title: "Profile surface"
      description: "/my-profile for authenticated users without leaking cross-tenant data."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Runs as a stateless Spring Boot instance (default port 8082 in docker-compose)."
    - "OpenAPI/Swagger UI at /swagger-ui.html."
    - "PostgreSQL database account_db for identity persistence."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview image"
  credit: "Replace with a deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/account-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs after capturing real deployment assets."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-account-service.png"
      thumbnail: ""
      title: "Account Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-account-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance registered in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8082"
    description: "Host mapping in root docker-compose.yml"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Account Service** owns signup, authentication (JWT), and basic profile read for the Architecture College platform.

## Authoring notes

- `liveDemoUrl` may point at the repo anchor until a public API base URL exists.
- Media URLs are placeholders.
