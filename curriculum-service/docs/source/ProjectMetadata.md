---
projectId: "architecture-college-curriculum"
featured: false
name: "Curriculum Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Academic catalog: careers, areas, professional lines, obligatory and elective subjects, and subject-series relationships."
techStack:
  - "Java 17"
  - "Spring Boot 3.3.x"
  - "Spring Cloud 2023.0.3"
  - "Spring Cloud Netflix Eureka"
  - "Spring Cloud Config"
  - "Spring Web"
  - "Spring Data JPA"
  - "Spring Security"
  - "SpringDoc OpenAPI"
  - "PostgreSQL"
  - "Docker / Docker Compose"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

Values map to the **`Project`** interface in `docs-schema.ts`. **`ProjectDocsModel`** is split across the other files in this folder.

Configuration is loaded from **Spring Cloud Config** (`config-data/curriculum-service.yml`); the service registers with **Eureka** and reports to **Spring Boot Admin**.
