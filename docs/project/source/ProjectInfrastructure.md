---
deploymentLayers:
  # account-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "Serves config-data on port 8888."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "Registry on port 8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "Aggregates actuator endpoints on port 8081."

  - name: "Data plane"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "Host database account_db for this service."
      - name: "Future MongoDB / RabbitMQ"
        icon: "n/a"
        description: "Not used by account-service; other platform services may use them."

  - name: "Account Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot container"
        icon: "coffee"
        description: "Listens on 8082 in the default Compose file."
      - name: "Actuator"
        icon: "activity"
        description: "Health and metrics; restrict in production."

  # curriculum-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "Port 8888; config-data volume mount."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "Registry 8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "Aggregates actuator on 8081."

  - name: "Data plane (this service)"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "Database curriculum_db for all catalog entities."
      - name: "MongoDB / RabbitMQ"
        icon: "n/a"
        description: "Not used by curriculum-service; other platform services may consume them."

  - name: "Curriculum Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot container"
        icon: "coffee"
        description: "Listens on 8085 in default Compose."
      - name: "Actuator"
        icon: "activity"
        description: "Health and metrics; narrow exposure in production."

  # enrollment-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "8888; serves config-data/enrollment-service.yml."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "8081."

  - name: "Data plane"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "enrollment_db — transactional enrollments."
      - name: "MongoDB"
        icon: "leaf"
        description: "Document DB (e.g. enrollment_db on cluster) for preload/status."

  - name: "Enrollment Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot"
        icon: "coffee"
        description: "Port 8087 in default Compose."
      - name: "Actuator"
        icon: "activity"
        description: "Narrow endpoint exposure in production."

  # schedule-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "8888; config-data/schedule-service.yml."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "8081."

  - name: "Data & messaging"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "schedule_db — groups, slots, assignments."
      - name: "RabbitMQ"
        icon: "rabbit"
        description: "AMQP5672; management UI 15672 in Compose."

  - name: "Schedule Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot"
        icon: "coffee"
        description: "Port 8086 host mapping by default."
      - name: "Actuator"
        icon: "activity"
        description: "Tighten exposure in production."

  # student-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "8888; config-data/student-service.yml."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "8081."

  - name: "Data & messaging"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "student_db."
      - name: "RabbitMQ"
        icon: "rabbit"
        description: "AMQP; Compose defaults guest unless overridden."

  - name: "Student Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot"
        icon: "coffee"
        description: "Port 8083 default."
      - name: "Actuator"
        icon: "activity"
        description: "Tighten in production."

  # teacher-service
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "8888; config-data/teacher-service.yml + jwt.secret.key."
      - name: "Netflix Eureka"
        icon: "radar"
        description: "8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "8081."

  - name: "Data & messaging"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "teacher_db."
      - name: "RabbitMQ"
        icon: "rabbit"
        description: "AMQP broker."

  - name: "Teacher Service runtime"
    color: "#88c0d0"
    components:
      - name: "Spring Boot"
        icon: "coffee"
        description: "Port 8084 default."
      - name: "Actuator"
        icon: "activity"
        description: "Restrict in production."

dockerFiles:
  # account-service
  - service: "account-service (multi-stage)"
    description: "Root Dockerfile; build arg SERVICE_NAME selects the Gradle module."
    content: "See repository Dockerfile — produces an executable JAR."
  # curriculum-service
  - service: "curriculum-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects Gradle module."
    content: "Produces runnable JAR; see repository Dockerfile."
  # enrollment-service
  - service: "enrollment-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects Gradle module."
    content: "Runnable JAR; see repository Dockerfile."
  # schedule-service
  - service: "schedule-service (multi-stage)"
    description: "Root Dockerfile with SERVICE_NAME build arg."
    content: "Fat JAR; see repository Dockerfile."
  # student-service
  - service: "student-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects module."
    content: "Fat JAR; see repository Dockerfile."
  # teacher-service
  - service: "teacher-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects module."
    content: "Fat JAR; see repository Dockerfile."
cloudServices:
  # account-service
  - name: "Container runtime"
    purpose: "Runs the Spring Boot image built from the monorepo Dockerfile (SERVICE_NAME=account-service)."
    icon: "docker"
    cost: "Environment-specific"

  # curriculum-service
  - name: "Container runtime"
    purpose: "Spring Boot image from monorepo Dockerfile with SERVICE_NAME=curriculum-service."
    icon: "docker"
    cost: "Environment-specific"

  # enrollment-service
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=enrollment-service."
    icon: "docker"
    cost: "Environment-specific"

  # schedule-service
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=schedule-service."
    icon: "docker"
    cost: "Environment-specific"

  # student-service
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=student-service."
    icon: "docker"
    cost: "Environment-specific"

  # teacher-service
  - name: "Container runtime"
    purpose: "Spring Boot image SERVICE_NAME=teacher-service."
    icon: "docker"
    cost: "Environment-specific"

metrics:
  # account-service
  - label: "Compose service key"
    value: "account-service"
    icon: "ship"
    description: "Defined in root docker-compose.yml; depends on postgres, config-server, eureka-server."

  # curriculum-service
  - label: "Compose service key"
    value: "curriculum-service"
    icon: "ship"
    description: "Root docker-compose.yml: depends on postgres, config-server, eureka-server."

  # enrollment-service
  - label: "Compose service key"
    value: "enrollment-service"
    icon: "ship"
    description: "docker-compose: postgres, mongodb, config-server, eureka-server (no RabbitMQ for this module by default)."

  # schedule-service
  - label: "Compose service key"
    value: "schedule-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server."

  # student-service
  - label: "Compose service key"
    value: "student-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server."

  # teacher-service
  - label: "Compose service key"
    value: "teacher-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server; JWT env from Compose."

---

# Project Infrastructure

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Front matter matches **`InfrastructureModel`** in `docs-schema.ts`.

This service is **deployed** as one container in the platform stack, with Postgres for persistence and Config/Eureka/Admin for cross-cutting control.

## Operational notes

- Map real **CPU/memory** limits in orchestrator manifests when moving off Compose.
- Use managed **PostgreSQL** in cloud with automated backups.

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Curriculum runs as a **stateless** JVM service with **PostgreSQL**; platform **Config**, **Eureka**, and **Admin** are shared.

## Notes

- Tune **connection pool** and **Postgres resources** when catalog size grows.
- **TLS** and **secrets** belong at the ingress/orchestrator, not in Git.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Enrollment is **deployed** with **two datastores** (Postgres + Mongo) plus shared platform services.

## Notes

- Back up **both** Postgres and Mongo with RPO/RTO appropriate for registration periods.  
- Tune **Mongo connection pool** independently when preload traffic spikes.

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/ProjectInfrastructure.md -->
---
metrics:
  - label: "Compose service"
    value: "grade-service"
    icon: "ship"
    description: "Defined in root `docker-compose.yml` with health-checked dependencies."

cloudServices:
  - name: "Container runtime"
    purpose: "Hosts all Spring Boot services and backing infrastructure."
    icon: "docker"
    cost: "Variable (local dev: $0)"

deploymentLayers:
  - name: "Platform plane"
    color: "#eceff4"
    components:
      - name: "Spring Cloud Config"
        icon: "settings"
        description: "Serves `config-data/*.yml` on port 8888."
      - name: "Eureka"
        icon: "radar"
        description: "Service registry on 8761."
      - name: "Spring Boot Admin"
        icon: "monitor"
        description: "Aggregates actuator data on 8081."

  - name: "Data plane"
    color: "#d8dee9"
    components:
      - name: "PostgreSQL"
        icon: "database"
        description: "Primary OLTP for each microservice database."
      - name: "MongoDB"
        icon: "leaf"
        description: "Document store for enrollment/grade projections when enabled."
      - name: "RabbitMQ"
        icon: "rabbit"
        description: "Event bus for student, teacher, schedule, and grade interactions."

  - name: "Grade Service"
    color: "#88c0d0"
    components:
      - name: "Spring container"
        icon: "coffee"
        description: "Boot 3.3 JVM listening on 8088 (host mapping)."
      - name: "Actuator"
        icon: "activity"
        description: "Health + metrics via `/actuator/*` (narrow exposure in prod)."

dockerFiles:
  - service: "multi-stage"
    description: "Root `Dockerfile` parameterised by `SERVICE_NAME` build arg."
    content: "See repository root `Dockerfile` — builds fat jar per module."
---
# Infrastructure

Production/staging **is treated as deployed**: the stack is packaged with Docker Compose, Config Server, Eureka, Admin, PostgreSQL, MongoDB (where required), and RabbitMQ (for evented services).

## Notes

- Replace placeholder cost figures with FinOps data once cloud SKUs are finalized.
- TLS termination, secrets, and external DNS live at the edge (not committed here).

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Schedule relies on **PostgreSQL** plus **RabbitMQ** alongside shared platform services.

## Notes

- Tune **Rabbit** prefetch and **publisher confirms** under load.  
- Back up **schedule_db** before term rollover or bulk migrations.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **Backup student_db** for compliance and DR.  
- Monitor **Rabbit** consumer lag during registration peaks.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/ProjectInfrastructure.md -->
# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **`JWT_SECRET_KEY`** (or config `jwt.secret.key`) must be **identical** to **account-service** token signing.  
- Backup **teacher_db**; tune **Rabbit** for faculty import peaks if applicable.

<!-- END teacher-service -->
