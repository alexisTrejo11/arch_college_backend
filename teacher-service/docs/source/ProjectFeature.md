---
features:
  - id: "teacher-service-f1"
    title: "Create and delete teachers"
    description: "POST to create; DELETE by teacher id."
    icon: "user-plus"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "POST /v1/api/teachers"
      - "DELETE /v1/api/teachers/{teacherId}"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "teacher-service-f2"
    title: "Account number validation"
    description: "GET validate before committing downstream workflows."
    icon: "check-circle"
    category: "security"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "GET /v1/api/teachers/{teacherAccountNumber}/validate"
    techStack:
      - "Spring Web"

  - id: "teacher-service-f3"
    title: "Teacher queries"
    description: "Fetch by id, account number, or batch ids (note /teachers base path)."
    icon: "search"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "GET /teachers/{teacherId}"
      - "GET /teachers/by-ids"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "teacher-service-f4"
    title: "JWT and messaging"
    description: "Resource server JWT validation; RabbitMQ for integration."
    icon: "shield"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "Spring Security + JWT"
      - "Spring AMQP"
    techStack:
      - "Spring Security"
      - "Spring AMQP"
      - "RabbitMQ"
---

# Features

Maps to **`ProjectFeature`** in `docs-schema.ts`.
