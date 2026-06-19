---
codeExamples:
  - id: "student-service-example-1"
    title: "Student command and query controllers"
    description: "Write and read APIs for the student aggregate."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "jpa"
    files:
      - name: "StudentCommandController.java"
        path: "student-service/src/main/java/io/github/alexistrejo11/architecture/college/student/controller/StudentCommandController.java"
        language: "java"
        content: |
          // See repository: POST/PUT/DELETE and progression routes under /v1/api/students.
        highlighted: true
        explanation: "Mutations and domain actions."
      - name: "StudentQueryController.java"
        path: "student-service/src/main/java/io/github/alexistrejo11/architecture/college/student/controller/StudentQueryController.java"
        language: "java"
        content: |
          // See repository: GET by id, account, all, /by filters.
        highlighted: false
        explanation: "Read-optimized queries."
---

# Code showcase

Matches **`ProjectCodeShowCase`** in `docs-schema.ts`.
