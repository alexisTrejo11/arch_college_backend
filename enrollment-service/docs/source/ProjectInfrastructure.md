---
metrics:
  - label: "Compose service key"
    value: "enrollment-service"
    icon: "ship"
    description: "docker-compose: postgres, mongodb, config-server, eureka-server (no RabbitMQ for this module by default)."

cloudServices:
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=enrollment-service."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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

dockerFiles:
  - service: "enrollment-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects Gradle module."
    content: "Runnable JAR; see repository Dockerfile."
---

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Enrollment is **deployed** with **two datastores** (Postgres + Mongo) plus shared platform services.

## Notes

- Back up **both** Postgres and Mongo with RPO/RTO appropriate for registration periods.  
- Tune **Mongo connection pool** independently when preload traffic spikes.  
