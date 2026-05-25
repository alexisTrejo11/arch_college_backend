---
metrics:
  - label: "Compose service key"
    value: "account-service"
    icon: "ship"
    description: "Defined in root docker-compose.yml; depends on postgres, config-server, eureka-server."

cloudServices:
  - name: "Container runtime"
    purpose: "Runs the Spring Boot image built from the monorepo Dockerfile (SERVICE_NAME=account-service)."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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

dockerFiles:
  - service: "account-service (multi-stage)"
    description: "Root Dockerfile; build arg SERVICE_NAME selects the Gradle module."
    content: "See repository Dockerfile — produces an executable JAR."
---

# Infrastructure

Front matter matches **`InfrastructureModel`** in `docs-schema.ts`.

This service is **deployed** as one container in the platform stack, with Postgres for persistence and Config/Eureka/Admin for cross-cutting control.

## Operational notes

- Map real **CPU/memory** limits in orchestrator manifests when moving off Compose.
- Use managed **PostgreSQL** in cloud with automated backups.
