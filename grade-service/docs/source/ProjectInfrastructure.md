
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
