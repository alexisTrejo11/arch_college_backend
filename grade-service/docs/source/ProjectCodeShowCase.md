---
codeExamples:
  - id: "grade-service-example-1"
    title: "Grade and academic history controllers"
    description: "REST entry points for grades, groups, teacher grading, student history, and admin academic history."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "security"
    files:
      - name: "GradeCommandController.java"
        path: "grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/GradeCommandController.java"
        language: "java"
        content: |
          // Authorize and soft-delete grade by id under /v1/api/grades.
        highlighted: true
        explanation: "Write operations on individual grades."
      - name: "TeacherGradeController.java"
        path: "grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/TeacherGradeController.java"
        language: "java"
        content: |
          // Teacher JWT-scoped routes under /v1/api/teachers/grades/groups.
        highlighted: true
        explanation: "Pending work, history, and bulk grade assignment."
      - name: "StudentAcademicHistoryController.java"
        path: "grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/academicHistory/StudentAcademicHistoryController.java"
        language: "java"
        content: |
          // Student-facing GETs under /v1/api/students/...
        highlighted: false
        explanation: "Self-service academic history and enrollment views."
---

# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.
