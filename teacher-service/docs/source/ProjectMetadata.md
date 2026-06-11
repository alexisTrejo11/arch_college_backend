---
projectId: "architecture-college-teacher"
featured: false
name: "Teacher Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Faculty lifecycle: create and delete teachers, validate account numbers, batch lookups, and RabbitMQ integration. JWT validation aligned with account-service."
techStack:
  - "Java 17"
  - "Spring Boot 3.3.x"
  - "Spring Cloud 2023.0.3"
  - "Spring Cloud Netflix Eureka"
  - "Spring Cloud Config"
  - "Spring Web"
  - "Spring Data JPA"
  - "Spring AMQP / RabbitMQ"
  - "Spring Security"
  - "SpringDoc OpenAPI"
  - "JWT (shared secret with account-service)"
  - "PostgreSQL (teacher_db)"
  - "Docker / Docker Compose"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. Nested **`docs`** are split across companion Obsidian files.

**Config Server**, **Eureka**, **Spring Boot Admin**, **PostgreSQL** (`teacher_db`), **RabbitMQ**, and **JWT** configuration (see `config-data/teacher-service.yml` and Compose) apply to this module.
