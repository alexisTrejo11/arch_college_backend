---
metrics:
  - label: "Compose service key"
    value: "teacher-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server; JWT env from Compose."

cloudServices:
  - name: "Container runtime"
    purpose: "Spring Boot image SERVICE_NAME=teacher-service."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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
  - service: "teacher-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects module."
    content: "Fat JAR; see repository Dockerfile."
---

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **`JWT_SECRET_KEY`** (or config `jwt.secret.key`) must be **identical** to **account-service** token signing.  
- Backup **teacher_db**; tune **Rabbit** for faculty import peaks if applicable.  
