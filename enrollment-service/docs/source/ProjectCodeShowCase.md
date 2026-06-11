---
codeExamples:
  - id: "enrollment-service-example-1"
    title: "Enrollment and preload controllers"
    description: "Command paths and Mongo-backed preload entry points."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "mongodb"
    files:
      - name: "EnrollmentCommandController.java"
        path: "enrollment-service/src/main/java/io/github/alexistrejo11/architecture/college/enrollment/controller/EnrollmentCommandController.java"
        language: "java"
        content: |
          // See repository: /v1/api/group-enrollments mutations and lock-date.
        highlighted: true
        explanation: "Administrative enrollment commands."
      - name: "StudentPreLoadController.java"
        path: "enrollment-service/src/main/java/io/github/alexistrejo11/architecture/college/enrollment/controller/preload/StudentPreLoadController.java"
        language: "java"
        content: |
          // See repository: students preload, status, clear under v1/api/students/.
        highlighted: false
        explanation: "Bulk student preload lifecycle backed by Mongo documents."
---

# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.
