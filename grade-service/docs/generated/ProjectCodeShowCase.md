# Code Showcase

## Grade and academic history controllers

REST entry points for grades, groups, teacher grading, student history, and admin academic history.

**Category:** backend | **Duration:** 5 min read | **Tags:** spring, rest, security

### GradeCommandController.java

**Path:** `grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/GradeCommandController.java`

Write operations on individual grades.

```java
// Authorize and soft-delete grade by id under /v1/api/grades.
```

### TeacherGradeController.java

**Path:** `grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/grade/TeacherGradeController.java`

Pending work, history, and bulk grade assignment.

```java
// Teacher JWT-scoped routes under /v1/api/teachers/grades/groups.
```

### StudentAcademicHistoryController.java

**Path:** `grade-service/src/main/java/io/github/alexistrejo11/architecture/college/grade/controller/academicHistory/StudentAcademicHistoryController.java`

Self-service academic history and enrollment views.

```java
// Student-facing GETs under /v1/api/students/...
```

## Additional notes

# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.

