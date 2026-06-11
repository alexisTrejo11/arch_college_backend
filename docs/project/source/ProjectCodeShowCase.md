---
codeExamples:
  # account-service
  - id: "account-service-example-1"
    title: "Auth and user controllers"
    description: "REST entry points for signup, login, and profile."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "security"
    files:
      - name: "AuthController.java"
        path: "account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/AuthController.java"
        language: "java"
        content: |
          // See repository for full sources. Endpoints under /v1/api/auth (signup/*, login).
        highlighted: true
        explanation: "Handles authentication and registration flows."
      - name: "UserController.java"
        path: "account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/UserController.java"
        language: "java"
        content: |
          // See repository for full sources. Profile endpoint /v1/api/user/my-profile.
        highlighted: false
        explanation: "Authenticated user read model exposure."
  # curriculum-service
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
  # enrollment-service
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
  # grade-service
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
  # schedule-service
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
  # student-service
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
  # teacher-service
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

# Project Code Showcase

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Front matter matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`. Replace `content` stubs with real excerpts for a portfolio renderer.

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** in `docs-schema.ts`.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/ProjectCodeShowCase.md -->
# Code showcase

Matches **`ProjectCodeShowCase`** in `docs-schema.ts`.

<!-- END teacher-service -->
