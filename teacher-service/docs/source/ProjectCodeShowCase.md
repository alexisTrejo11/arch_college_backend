---
codeExamples:
  - id: "teacher-service-example-1"
    title: "Teacher command and query controllers"
    description: "Mutations under /v1/api/teachers; reads under /teachers."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "security"
      - "jpa"
    files:
      - name: "TeacherCommandController.java"
        path: "teacher-service/src/main/java/io/github/alexistrejo11/architecture/college/teacher/controller/TeacherCommandController.java"
        language: "java"
        content: |
          // See repository: POST/DELETE/validate under /v1/api/teachers.
        highlighted: true
        explanation: "Writes and validation entry points."
      - name: "TeacherQueryController.java"
        path: "teacher-service/src/main/java/io/github/alexistrejo11/architecture/college/teacher/controller/TeacherQueryController.java"
        language: "java"
        content: |
          // See repository: GET under /teachers (gateway should map to /v1/api/teachers/...).
        highlighted: false
        explanation: "Read APIs — path prefix differs from command controller."
---

# Code showcase

Matches **`ProjectCodeShowCase`** in `docs-schema.ts`.
