---
features:
  - id: "grade-service-f1"
    title: "Grade queries and commands"
    description: "Search and fetch grades; authorize or soft-delete by id."
    icon: "clipboard-list"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/grade-service"
    highlights:
      - "GET /v1/api/grades/{gradeId}"
      - "GET /v1/api/grades/all (filters + pagination)"
      - "GET /v1/api/grades/pending-validation"
      - "PUT /v1/api/grades/{gradeId}/authorize"
      - "DELETE /v1/api/grades/{gradeId}"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "grade-service-f2"
    title: "Groups and teacher grading"
    description: "Inspect groups, list pending grading work for the authenticated teacher, submit grades."
    icon: "users"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/grade-service"
    highlights:
      - "GET /v1/api/grades/groups/{groupId}"
      - "GET /v1/api/grades/groups/pending"
      - "GET /v1/api/teachers/grades/groups/pending-grade"
      - "GET /v1/api/teachers/grades/groups/my-history"
      - "PUT /v1/api/teachers/grades/groups/set-grades"
    techStack:
      - "Spring Web"
      - "Spring Security"

  - id: "grade-service-f3"
    title: "Student academic views"
    description: "JWT-scoped reads for academic history, annual grades, and current enrollments."
    icon: "graduation-cap"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/grade-service"
    highlights:
      - "GET /v1/api/students/get-my-academic-history"
      - "GET /v1/api/students/get-my-annually-grades"
      - "GET /v1/api/students/get-my-current-enrollments"
    techStack:
      - "Spring Web"
      - "Spring Security"

  - id: "grade-service-f4"
    title: "Academic history admin and messaging"
    description: "ADMIN route to load academic history by account number; Rabbit listeners support async academic history updates."
    icon: "shield"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/grade-service"
    highlights:
      - "POST /v1/api/academic-histories/student/{accountNumber} (ADMIN; maps from class path without leading slash)"
      - "Spring AMQP consumers (see AcademicHistoryReceiver)"
    techStack:
      - "Spring Security"
      - "Spring AMQP"
      - "Spring Data MongoDB"
---

# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.
