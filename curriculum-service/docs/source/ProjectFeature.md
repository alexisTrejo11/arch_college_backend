---
features:
  - id: "curriculum-service-f1"
    title: "Careers, areas, and professional lines"
    description: "CRUD and lookup APIs for organizational structure of the catalog."
    icon: "layers"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "GET/POST/PUT for careers, areas, professional lines"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "curriculum-service-f2"
    title: "Obligatory and elective subjects"
    description: "Manage subject definitions with filters by area, career, semester."
    icon: "book"
    category: "database"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "/v1/api/subjects/obligatory and /v1/api/subjects/electives"
    techStack:
      - "Spring Data JPA"
      - "PostgreSQL"

  - id: "curriculum-service-f3"
    title: "Subject series graph"
    description: "Link obligatory and elective subjects through series for sequencing rules."
    icon: "share"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "Subject series CRUD and queries"
    techStack:
      - "Spring Web"

  - id: "curriculum-service-f4"
    title: "Read-optimized catalog queries"
    description: "List endpoints and filters for admin and downstream consumption."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "by-area, by-career, by-semester lookups"
    techStack:
      - "Spring Data JPA"
---

# Features

Each item maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.
