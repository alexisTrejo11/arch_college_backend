---
metrics:
  - label: "Compose service key"
    value: "schedule-service"
    icon: "ship"
    description: "docker-compose: postgres, rabbitmq, config-server, eureka-server."

cloudServices:
  - name: "Container runtime"
    purpose: "Spring Boot image with SERVICE_NAME=schedule-service."
    icon: "docker"
    cost: "Environment-specific"

deploymentLayers:
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

dockerFiles:
  - service: "schedule-service (multi-stage)"
    description: "Root Dockerfile with SERVICE_NAME build arg."
    content: "Fat JAR; see repository Dockerfile."
---

# Infrastructure

Matches **`InfrastructureModel`** in `docs-schema.ts`.

Schedule relies on **PostgreSQL** plus **RabbitMQ** alongside shared platform services.

## Notes

- Tune **Rabbit** prefetch and **publisher confirms** under load.  
- Back up **schedule_db** before term rollover or bulk migrations.  
