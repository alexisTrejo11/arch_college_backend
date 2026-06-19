# Code Showcase

## Teacher command and query controllers

Mutations under /v1/api/teachers; reads under /teachers.

**Category:** backend | **Duration:** 5 min read | **Tags:** spring, security, jpa

### TeacherCommandController.java

**Path:** `teacher-service/src/main/java/io/github/alexistrejo11/architecture/college/teacher/controller/TeacherCommandController.java`

Writes and validation entry points.

```java
// See repository: POST/DELETE/validate under /v1/api/teachers.
```

### TeacherQueryController.java

**Path:** `teacher-service/src/main/java/io/github/alexistrejo11/architecture/college/teacher/controller/TeacherQueryController.java`

Read APIs — path prefix differs from command controller.

```java
// See repository: GET under /teachers (gateway should map to /v1/api/teachers/...).
```

## Additional notes

# Code showcase

Matches **`ProjectCodeShowCase`** in `docs-schema.ts`.

