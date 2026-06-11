---
projectId: "architecture-college-schedule"
featured: false
name: "Schedule Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Class groups (obligatory/elective): create, query, attach teachers, adjust capacity, and emit scheduling-related integration events over RabbitMQ."
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
  - "PostgreSQL (schedule_db)"
  - "Docker / Docker Compose"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

Maps to **`Project`** in `docs-schema.ts`. **`ProjectDocsModel`** is split across sibling Obsidian files.

Uses **Spring Cloud Config**, **Eureka**, **Spring Boot Admin**, **PostgreSQL** for `schedule_db`, and **RabbitMQ** for asynchronous integration (per platform wiring).
