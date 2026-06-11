---
projectId: "architecture-college-grade"
featured: false
name: "Grade Service"
language: "Java"
category: "backend"
framework: "Spring Boot + Spring Cloud Netflix"
version: "1.0.0"
repositoryUrl: "https://github.com/alexisTrejo11/architecture-college-plattform"
liveDemoUrl: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
description: "Captures grades, validation/authorization flows, teacher grading consoles, and academic history projections."
techStack:
  - "Java 17"
  - "Spring Boot 3.3.x"
  - "Spring Cloud 2023.0.3"
  - "Spring Cloud Netflix Eureka"
  - "Spring Cloud Config"
  - "SpringDoc OpenAPI"
  - "Spring Security"
  - "Spring Web"
  - "Spring Data JPA"
  - "Docker / Docker Compose"
  - "PostgreSQL (`grade_db`) + MongoDB (`grade_db`)"
  - "Spring AMQP / RabbitMQ"
  - "Spring Data MongoDB"
status: "deployed"
createdAt: "2026-01-01T00:00:00.000Z"
updatedAt: "2026-05-11T00:00:00.000Z"
---

# Project metadata

This microservice is part of the **Architecture College Platform**. Configuration is delivered through **Spring Cloud Config**, instances register with **Eureka**, and health is visible in **Spring Boot Admin**. Documentation mirrors the `Project` model in `docs-schema.ts`.
