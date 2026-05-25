---
codeExamples:
  - id: "schedule-service-example-1"
    title: "Group creation and finder"
    description: "Write path for new groups and read path for lookups."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "jpa"
    files:
      - name: "GroupCreationController.java"
        path: "schedule-service/src/main/java/io/github/alexistrejo11/architecture/college/schedule/controller/GroupCreationController.java"
        language: "java"
        content: |
          // See repository: POST /v1/api/groups/obligatory and /elective.
        highlighted: true
        explanation: "Creates obligatory and elective groups."
      - name: "GroupFinderController.java"
        path: "schedule-service/src/main/java/io/github/alexistrejo11/architecture/college/schedule/controller/GroupFinderController.java"
        language: "java"
        content: |
          // See repository: GET /v1/api/finder/groups/* query surface.
        highlighted: false
        explanation: "Read-optimized group discovery."
---

# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.
