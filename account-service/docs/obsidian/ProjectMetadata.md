---
projectId: "architecture-college-account"
featured: false
name: "Account Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Handles signup for students, teachers, and admins, JWT login, and authenticated profile access."
techStack:
  - "Java 17"
  - "Spring Boot 3.3.x"
  - "Spring Cloud 2023.0.3"
  - "Spring Cloud Netflix Eureka"
  - "Spring Cloud Config"
  - "Spring Security"
  - "SpringDoc OpenAPI"
  - "PostgreSQL"
  - "Docker / Docker Compose"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

Values above map to the **`Project`** interface in `docs-schema.ts` (top-level fields). Nested **`docs: ProjectDocsModel`** is documented across the other files in this folder.

This service uses **Spring Cloud Config**, registers with **Eureka**, and reports to **Spring Boot Admin**.
