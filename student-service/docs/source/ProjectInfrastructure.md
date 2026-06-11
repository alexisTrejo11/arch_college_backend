---
metrics:
  - label: "Compose service key"
    value: "student-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server."

cloudServices:
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=student-service."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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

dockerFiles:
  - service: "student-service (multi-stage)"
    description: "Root Dockerfile; SERVICE_NAME selects module."
    content: "Fat JAR; see repository Dockerfile."
---

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

## Notes

- **Backup student_db** for compliance and DR.  
- Monitor **Rabbit** consumer lag during registration peaks.  
