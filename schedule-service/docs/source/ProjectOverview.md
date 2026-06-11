---
problemStatement:
  problemTitle: "Why Schedule Service exists"
  problemDescription: "Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows."
  problemList:
    - "Timetabling ties classrooms, teachers, and enrollment capacity; updates must stay consistent and visible to enrollment and faculty workflows."

solution:
  solutionTitle: "How the service addresses it"
  solutionList:
    - title: "Dedicated REST controllers"
      description: "Creation, finder, update, and deletion flows are split for clarity and evolution."
    - title: "Capacity and roster operations"
      description: "Endpoints adjust spots and assign or remove teachers with explicit paths."
    - title: "Event-friendly runtime"
      description: "RabbitMQ supports notifying peers when groups or schedules change."

keyMetrics:
  metricsTitle: "Operational signals"
  metricsList:
    - "Default HTTP port 8086 in root docker-compose.yml."
    - "PostgreSQL schedule_db for group and schedule persistence."
    - "RabbitMQ broker for AMQP (see compose env SPRING_RABBITMQ_*)."

coverImage:
  url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/platform-overview.png"
  alt: "Placeholder platform overview"
  credit: "Replace with deployment screenshot"

links:
  github: "https://github.com/alexisTrejo11/architecture-college-plattform"
  demo: "https://github.com/alexisTrejo11/architecture-college-plattform#deployed-stack"
  documentation: "https://github.com/alexisTrejo11/architecture-college-plattform/blob/main/schedule-service/README.md"
  dockerHub: null

mediaGallery:
  title: "Service visuals"
  description: "Replace URLs when production assets exist."
  items:
    - type: "image"
      url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/swagger-schedule-service.png"
      thumbnail: ""
      title: "Schedule Service Swagger"
      description: "SpringDoc UI"
      alt: "Swagger UI"
      category: "screenshot"

mediaItems:
  - type: "image"
    url: "https://github.com/alexisTrejo11/architecture-college-plattform/raw/main/docs/assets/eureka-schedule-service.png"
    thumbnail: ""
    title: "Eureka registration"
    description: "Instance in discovery"
    alt: "Eureka"
    category: "architecture"

metrics:
  - label: "Service port"
    value: "8086"
    description: "docker-compose host mapping"
    icon: "network"
    unit: ""
    trend: "stable"
    threshold: null
---

# Overview

Front matter matches **`ProjectOverview`** in `docs-schema.ts`.

**Schedule Service** owns **class groups**, **meeting patterns**, **teacher assignment**, **capacity**, and **lookup** APIs used by enrollment and operations.

## Authoring notes

- Concurrent `increase-spot` / `decrease-spot` calls may need **optimistic locking** or DB constraints — confirm in implementation.
- Define **Rabbit** exchanges/queues as code or IaC so environments match.
