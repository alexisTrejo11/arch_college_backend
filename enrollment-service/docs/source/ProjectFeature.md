---
features:
  - id: "enrollment-service-f1"
    title: "Group enrollment operations"
    description: "Create and cancel enrollments by student account; fetch by id or student; enrollment lock date."
    icon: "clipboard"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "POST/DELETE /v1/api/group-enrollments/..."
      - "GET lock-date"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "enrollment-service-f2"
    title: "Student self-service enrollment"
    description: "Students list, add, and drop enrollments by group/subject keys."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "GET my-enrollments; POST/DELETE student routes"
    techStack:
      - "Spring Security"
      - "Spring Web"

  - id: "enrollment-service-f3"
    title: "Bulk preload pipelines"
    description: "Operational preload for students, subjects, schedules, grades with status polling."
    icon: "download"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "POST preload, GET status, DELETE clear per domain"
    techStack:
      - "Spring Data MongoDB"

  - id: "enrollment-service-f4"
    title: "Polyglot persistence"
    description: "Postgres for enrollments; Mongo for flexible preload/job documents."
    icon: "database"
    category: "database"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "Dual datasource configuration via Spring Cloud"
    techStack:
      - "PostgreSQL"
      - "MongoDB"
---

# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.
