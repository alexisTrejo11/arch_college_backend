---
features:
  # account-service
  - id: "account-service-f1"
    title: "Student, teacher, and admin registration"
    description: "Dedicated signup endpoints per persona."
    icon: "user-plus"
    category: "authentication"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "Separate routes for student, teacher, and admin signup"
    techStack:
      - "Spring Boot"
      - "Spring Security"

  - id: "account-service-f2"
    title: "JWT login"
    description: "Issues JWT after successful authentication."
    icon: "key"
    category: "security"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "POST /v1/api/auth/login"
    techStack:
      - "Spring Security"
      - "JJWT or configured JWT library"

  - id: "account-service-f3"
    title: "Authenticated profile"
    description: "Current user profile for authenticated principal."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "GET /v1/api/user/my-profile"
    techStack:
      - "Spring Web"
      - "Spring Security"

  - id: "account-service-f4"
    title: "Service discovery registration"
    description: "Registers with Eureka for use by gateways or other services."
    icon: "radar"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "Eureka client enabled via Spring Cloud"
    techStack:
      - "Spring Cloud Netflix Eureka"
  # curriculum-service
  - id: "curriculum-service-f1"
    title: "Careers, areas, and professional lines"
    description: "CRUD and lookup APIs for organizational structure of the catalog."
    icon: "layers"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "GET/POST/PUT for careers, areas, professional lines"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "curriculum-service-f2"
    title: "Obligatory and elective subjects"
    description: "Manage subject definitions with filters by area, career, semester."
    icon: "book"
    category: "database"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "/v1/api/subjects/obligatory and /v1/api/subjects/electives"
    techStack:
      - "Spring Data JPA"
      - "PostgreSQL"

  - id: "curriculum-service-f3"
    title: "Subject series graph"
    description: "Link obligatory and elective subjects through series for sequencing rules."
    icon: "share"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "Subject series CRUD and queries"
    techStack:
      - "Spring Web"

  - id: "curriculum-service-f4"
    title: "Read-optimized catalog queries"
    description: "List endpoints and filters for admin and downstream consumption."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/curriculum-service"
    highlights:
      - "by-area, by-career, by-semester lookups"
    techStack:
      - "Spring Data JPA"
  # enrollment-service
  - id: "enrollment-service-f1"
    title: "Group enrollment operations"
    description: "Create and cancel enrollments by student account; fetch by id or student; enrollment lock date."
    icon: "clipboard"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "POST/DELETE /v1/api/group-enrollments/..."
      - "GET lock-date"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "enrollment-service-f2"
    title: "Student self-service enrollment"
    description: "Students list, add, and drop enrollments by group/subject keys."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "GET my-enrollments; POST/DELETE student routes"
    techStack:
      - "Spring Security"
      - "Spring Web"

  - id: "enrollment-service-f3"
    title: "Bulk preload pipelines"
    description: "Operational preload for students, subjects, schedules, grades with status polling."
    icon: "download"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "POST preload, GET status, DELETE clear per domain"
    techStack:
      - "Spring Data MongoDB"

  - id: "enrollment-service-f4"
    title: "Polyglot persistence"
    description: "Postgres for enrollments; Mongo for flexible preload/job documents."
    icon: "database"
    category: "database"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/enrollment-service"
    highlights:
      - "Dual datasource configuration via Spring Cloud"
    techStack:
      - "PostgreSQL"
      - "MongoDB"
  # grade-service
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
  # schedule-service
  - id: "schedule-service-f1"
    title: "Group creation"
    description: "Create obligatory and elective class groups with validation."
    icon: "plus-square"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "POST /v1/api/groups/obligatory and /elective"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "schedule-service-f2"
    title: "Finder and current-term queries"
    description: "Look up groups by id, key, filters, teacher, or building; current offerings."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "GET /v1/api/finder/groups/*"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "schedule-service-f3"
    title: "Roster and capacity mutations"
    description: "Update schedules, add/remove teachers, adjust available spots."
    icon: "sliders"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "PUT/PATCH/DELETE under /v1/api/groups"
    techStack:
      - "Spring Web"
      - "Jakarta Validation"

  - id: "schedule-service-f4"
    title: "Asynchronous integration"
    description: "RabbitMQ for notifying other services of schedule changes."
    icon: "rabbit"
    category: "messaging"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "Spring AMQP listeners/publishers"
    techStack:
      - "Spring AMQP"
      - "RabbitMQ"
  # student-service
  - id: "student-service-f1"
    title: "Student CRUD"
    description: "Create, update, and delete student records."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST, PUT, DELETE /v1/api/students"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "student-service-f2"
    title: "Flexible student lookups"
    description: "Fetch by id, account number, list all, or composite /by filters."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "GET /v1/api/students/* query surface"
    techStack:
      - "Spring Data JPA"

  - id: "student-service-f3"
    title: "Professional line and modality"
    description: "Assign professional line with modality for a student account."
    icon: "git-branch"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST .../set-professionalLine/.../modality/..."
    techStack:
      - "Spring Web"

  - id: "student-service-f4"
    title: "Progression and messaging"
    description: "Increment completed semester; emit integration events via RabbitMQ."
    icon: "rabbit"
    category: "messaging"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/student-service"
    highlights:
      - "POST .../increase-semester-completed"
      - "Spring AMQP"
    techStack:
      - "Spring AMQP"
      - "RabbitMQ"
  # teacher-service
  - id: "teacher-service-f1"
    title: "Create and delete teachers"
    description: "POST to create; DELETE by teacher id."
    icon: "user-plus"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "POST /v1/api/teachers"
      - "DELETE /v1/api/teachers/{teacherId}"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "teacher-service-f2"
    title: "Account number validation"
    description: "GET validate before committing downstream workflows."
    icon: "check-circle"
    category: "security"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "GET /v1/api/teachers/{teacherAccountNumber}/validate"
    techStack:
      - "Spring Web"

  - id: "teacher-service-f3"
    title: "Teacher queries"
    description: "Fetch by id, account number, or batch ids (note /teachers base path)."
    icon: "search"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "GET /teachers/{teacherId}"
      - "GET /teachers/by-ids"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "teacher-service-f4"
    title: "JWT and messaging"
    description: "Resource server JWT validation; RabbitMQ for integration."
    icon: "shield"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/teacher-service"
    highlights:
      - "Spring Security + JWT"
      - "Spring AMQP"
    techStack:
      - "Spring Security"
      - "Spring AMQP"
      - "RabbitMQ"
---

# Project Features

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/ProjectFeature.md -->
# Features

Each list item matches **`ProjectFeature`** in `docs-schema.ts` (`category` uses **`FeatureCategory`**, `status` uses **`FeatureStatus`**).

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/ProjectFeature.md -->
# Features

Each item maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/ProjectFeature.md -->
# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/ProjectFeature.md -->
# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/ProjectFeature.md -->
# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/ProjectFeature.md -->
# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/ProjectFeature.md -->
# Features

Maps to **`ProjectFeature`** in `docs-schema.ts`.

<!-- END teacher-service -->
