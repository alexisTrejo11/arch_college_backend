---
metrics:
  - label: "Compose service key"
    value: "curriculum-service"
    icon: "ship"
    description: "Root docker-compose.yml: depends on postgres, config-server, eureka-server."

cloudServices:
  - name: "Container runtime"
    purpose: "Spring Boot image from monorepo Dockerfile with SERVICE_NAME=curriculum-service."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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

dockerFiles:
  - service: "curriculum-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects Gradle module."
    content: "Produces runnable JAR; see repository Dockerfile."
---

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Curriculum runs as a **stateless** JVM service with **PostgreSQL**; platform **Config**, **Eureka**, and **Admin** are shared.

## Notes

- Tune **connection pool** and **Postgres resources** when catalog size grows.
- **TLS** and **secrets** belong at the ingress/orchestrator, not in Git.
