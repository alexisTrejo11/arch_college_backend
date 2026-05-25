---
codeExamples:
  - id: "curriculum-service-example-1"
    title: "Catalog controllers"
    description: "REST boundaries for careers and obligatory subjects."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "jpa"
    files:
      - name: "CareerController.java"
        path: "curriculum-service/src/main/java/io/github/alexistrejo11/architecture/college/curriculum/controller/CareerController.java"
        language: "java"
        content: |
          // See repository: /v1/api/careers CRUD and lookups.
        highlighted: true
        explanation: "Career aggregate API."
      - name: "ObligatorySubjectController.java"
        path: "curriculum-service/src/main/java/io/github/alexistrejo11/architecture/college/curriculum/controller/ObligatorySubjectController.java"
        language: "java"
        content: |
          // See repository: obligatory subjects by area, semester, career.
        highlighted: false
        explanation: "Core subject catalog for sequencing and enrollment prerequisites."
---

# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.
