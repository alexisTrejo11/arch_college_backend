---
problemStatement:
  problemTitle: "Architecture College Platform — Problem Statements"
  problemDescription: |-
    Combined problem descriptions from all Architecture College Platform microservices.
    
    # account-service
    Academic platforms need distinct onboarding paths per persona while sharing a single identity model.
    
    # curriculum-service
    Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume.
    
    # enrollment-service
    Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path.
    
    # grade-service
    Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work.
    
    # schedule-service
    Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows.
    
    # student-service
    Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling.
    
    # teacher-service
    Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens.
  problemList:
    # account-service
    - "Academic platforms need distinct onboarding paths per persona while sharing a single identity model."

    # curriculum-service
    - "Curriculum data is hierarchical (careers, areas, subjects) and must stay normalized, version-friendly, and easy for enrollment and scheduling to consume."

    # enrollment-service
    - "Enrollment windows mix strong consistency for seat assignment with heavy bulk preload and status tracking that should not block the OLTP path."

    # grade-service
    - "Grades must be auditable and authorizable, while students and teachers need fast, role-appropriate views of history and pending work."

    # schedule-service
    - "Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows."

    # student-service
    - "Student profiles and progression (line, semester) must stay consistent while peer services react without tight synchronous coupling."

    # teacher-service
    - "Scheduling and grading need authoritative faculty records with safeguards against duplicate payroll identifiers and clear integration with identity tokens."

solution:
  solutionTitle: "Architecture College Platform — Solutions"
  solutionList:
    # account-service
    - title: "Segmented signup"
      description: "Dedicated endpoints for student, teacher, and admin registration with role-aware payloads."
    - title: "Centralized login"
      description: "Single /login path issuing JWT tokens consumed by downstream services."
    - title: "Profile surface"
      description: "/my-profile for authenticated users without leaking cross-tenant data."

    # curriculum-service
    - title: "Bounded REST resources"
      description: "Controllers map to aggregates: careers, areas, professional lines, obligatory/elective subjects, subject series."
    - title: "Single relational catalog"
      description: "Postgres curriculum_db is the source of truth; no shared tables with other services."
    - title: "Rich read shapes"
      description: "Queries by area, career, semester, and series support admin tooling and downstream services."

    # enrollment-service
    - title: "Polyglot persistence"
      description: "PostgreSQL holds authoritative enrollment rows; MongoDB stores preload process state and auxiliary documents."
    - title: "Dedicated REST surfaces"
      description: "Command vs finder controllers separate writes from reads; student self-service has its own routes."
    - title: "Bulk preload APIs"
      description: "Students, subjects, schedules, and grades each expose preload + status + clear lifecycles for operators."

    # grade-service
    - title: "Split controllers by audience"
      description: "Admin-grade queries, teacher group grading, and student self-service reads each have focused routes."
    - title: "Postgres + Mongo"
      description: "Transactional grade rows in PostgreSQL; Mongo supports projections and document-oriented reads where configured."
    - title: "RabbitMQ"
      description: "Async hooks (e.g. academic history refresh) decouple heavy updates from the request path."

    # schedule-service
    - title: "Dedicated REST controllers"
      description: "Creation, finder, update, and deletion flows are split for clarity and evolution."
    - title: "Capacity and roster operations"
      description: "Endpoints adjust spots and assign or remove teachers with explicit paths."
    - title: "Event-friendly runtime"
      description: "RabbitMQ supports notifying peers when groups or schedules change."

    # student-service
    - title: "Command vs query split"
      description: "StudentCommandController handles writes; StudentQueryController optimizes reads."
    - title: "Relational source of truth"
      description: "student_db stores canonical student aggregates under this service boundary."
    - title: "AMQP integration"
      description: "RabbitMQ propagates events for downstream sync (as implemented in the module)."

    # teacher-service
    - title: "Explicit validation endpoint"
      description: "Pre-validate teacher account numbers before expensive downstream flows."
    - title: "Command vs query APIs"
      description: "Writes under /v1/api/teachers; reads under /teachers (legacy path — normalize at gateway)."
    - title: "Broker-backed integration"
      description: "RabbitMQ keeps schedule/grade consumers loosely coupled (as implemented)."

keyMetrics:
  metricsTitle: "Architecture College Platform — Key Metrics"
  metricsList:
    # account-service
    - "Runs as a stateless Spring Boot instance (default port 8082 in docker-compose)."
    - "OpenAPI/Swagger UI at /swagger-ui.html."
    - "PostgreSQL database account_db for identity persistence."

    # curriculum-service
    - "Default HTTP port 8085 in root docker-compose.yml."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."
    - "Isolated database curriculum_db for all catalog entities."

    # enrollment-service
    - "Default HTTP port 8087 in root docker-compose.yml."
    - "JDBC to PostgreSQL enrollment_db; Mongo URI for document collections."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

    # grade-service
    - "Default HTTP port 8088 in root docker-compose.yml."
    - "PostgreSQL grade_db; MongoDB grade_db; RabbitMQ from compose env."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

    # schedule-service
    - "Default HTTP port 8086 in root docker-compose.yml."
    - "PostgreSQL schedule_db for group and schedule persistence."
    - "RabbitMQ broker for AMQP (see compose env SPRING_RABBITMQ_*)."

    # student-service
    - "Default HTTP port 8083 in root docker-compose.yml."
    - "PostgreSQL student_db; RabbitMQ broker from compose env."
    - "Swagger UI at /swagger-ui.html when SpringDoc is enabled."

    # teacher-service
    - "Default HTTP port 8084 in docker-compose.yml."
    - "PostgreSQL teacher_db; RabbitMQ from compose."
    - "JWT secret must match account-service signing key in every environment."

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: null
  documentation: "docs/project/source"
  dockerHub: null
mediaGallery:
  title: "Architecture College Platform — Media Gallery"
  description: "Merged media gallery items from all domain services."
  items:
    # account-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-account-service.png"
      thumbnail: ""
      title: "Account Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # curriculum-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-curriculum-service.png"
      thumbnail: ""
      title: "Curriculum Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # enrollment-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-enrollment-service.png"
      thumbnail: ""
      title: "Enrollment Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # grade-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-grade-service.png"
      thumbnail: ""
      title: "Grade Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # schedule-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-schedule-service.png"
      thumbnail: ""
      title: "Schedule Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # student-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-student-service.png"
      thumbnail: ""
      title: "Student Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

    # teacher-service
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-teacher-service.png"
      thumbnail: ""
      title: "Teacher Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  # account-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-account-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance registered in discovery"
    alt: "Eureka"
    category: "architecture"

  # curriculum-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-curriculum-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

  # enrollment-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-enrollment-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

  # grade-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-grade-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

  # schedule-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-schedule-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

  # student-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-student-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

  # teacher-service
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-teacher-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  # account-service
  - label: "Service port"
    value: "8082"
    description: "Host mapping in root docker-compose.yml"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # curriculum-service
  - label: "Service port"
    value: "8085"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # enrollment-service
  - label: "Service port"
    value: "8087"
    description: "Host mapping in docker-compose"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # grade-service
  - label: "Service port"
    value: "8088"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # schedule-service
  - label: "Service port"
    value: "8086"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # student-service
  - label: "Service port"
    value: "8083"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
  # teacher-service
  - label: "Service port"
    value: "8084"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Project Overview

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Account Service** owns signup, authentication (JWT), and basic profile read for the Architecture College platform.

## Authoring notes

- `liveDemoUrl` may point at the repo anchor until a public API base URL exists.
- Media URLs are placeholders.

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Curriculum Service** is the **academic catalog** for the platform: careers, areas, professional lines, subjects, and how series link offerings together.

## Authoring notes

- Some controllers omit a leading `/` on `@RequestMapping` — normalize at an API gateway for public docs.
- Verify **`springdoc.packages-to-scan`** in `config-data/curriculum-service.yml` matches `io.github.alexistrejo11...` so Swagger discovers all controllers.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Enrollment Service** coordinates **group enrollments**, **student self-service** enroll/drop, **lock-date** rules, and **bulk preload** operations with status tracked in MongoDB.

## Authoring notes

- Several controllers use `@RequestMapping("v1/api/...")` **without** a leading slash — document canonical paths as **`/v1/api/...`** at the gateway.
- Define which store is **source of truth** per aggregate (Postgres vs Mongo) in runbooks to avoid split-brain reads.

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Grade Service** owns **grade queries and lifecycle** (authorize, soft delete), **teacher grading** for groups, **student-facing academic history** endpoints, and **admin academic history** access, with **Postgres**, **MongoDB**, and **RabbitMQ** in the deployed stack.

## Authoring notes

- **`AcademicHistoryController`** uses `@RequestMapping("v1/api/academic-histories")` without a leading slash—normalize at the gateway to **`/v1/api/...`** for public docs.
- Keep **JWT signing** aligned with **account-service** for resource-server validation.

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Schedule Service** owns **class groups**, **meeting patterns**, **teacher assignment**, **capacity**, and **lookup** APIs used by enrollment and operations.

## Authoring notes

- Concurrent `increase-spot` / `decrease-spot` calls may need **optimistic locking** or DB constraints — confirm in implementation.
- Define **Rabbit** exchanges/queues as code or IaC so environments match.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Student Service** owns **student CRUD**, **lookups**, **professional line / modality**, and **semester completion** increments, with **messaging** for integration.

## Authoring notes

- Coordinate **professional line ids** with curriculum-service contract.  
- Document **exchange/queue** names if operators must troubleshoot Rabbit consumers.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/ProjectOverview.md -->
# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Teacher Service** is the **faculty aggregate**: **create/delete**, **validate** account numbers, **batch get**, with **JWT** validation and **RabbitMQ**.

## Authoring notes

- **`TeacherQueryController`** maps to **`/teachers`** while commands use **`/v1/api/teachers`** — unify at **API gateway** (e.g. `/v1/api/teachers/...` everywhere).  
- **Rotate JWT secrets** with account-service in lockstep.

<!-- END teacher-service -->
