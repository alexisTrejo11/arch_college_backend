---
features:
  - id: "student-service-f1"
    title: "Student CRUD"
    description: "Create, update, and delete student records."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST, PUT, DELETE /v1/api/students"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "student-service-f2"
    title: "Flexible student lookups"
    description: "Fetch by id, account number, list all, or composite /by filters."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "GET /v1/api/students/* query surface"
    techStack:
      - "Spring Data JPA"

  - id: "student-service-f3"
    title: "Professional line and modality"
    description: "Assign professional line with modality for a student account."
    icon: "git-branch"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST .../set-professionalLine/.../modality/..."
    techStack:
      - "Spring Web"

  - id: "student-service-f4"
    title: "Progression and messaging"
    description: "Increment completed semester; emit integration events via RabbitMQ."
    icon: "rabbit"
    category: "messaging"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST .../increase-semester-completed"
      - "Spring AMQP"
    techStack:
      - "Spring AMQP"
      - "RabbitMQ"
---

# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.
