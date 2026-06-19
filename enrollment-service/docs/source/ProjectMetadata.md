---
projectId: "architecture-college-enrollment"
featured: false
name: "Enrollment Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Group and student enrollments, enrollment lock rules, and Mongo-backed bulk preload jobs alongside Postgres transactions."
techStack:
  - "Java 17"
  - "Spring Boot 3.3.x"
  - "Spring Cloud 2023.0.3"
  - "Spring Cloud Netflix Eureka"
  - "Spring Cloud Config"
  - "Spring Web"
  - "Spring Data JPA"
  - "Spring Data MongoDB"
  - "Spring Security"
  - "SpringDoc OpenAPI"
  - "PostgreSQL (enrollment_db)"
  - "MongoDB"
  - "Docker / Docker Compose"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. **`ProjectDocsModel`** is split across sibling Obsidian files.

Uses **Spring Cloud Config**, **Eureka**, and **Spring Boot Admin**. Persistence is **polyglot**: **PostgreSQL** for transactional enrollments and **MongoDB** for preload/job artifacts (as implemented in the codebase).
