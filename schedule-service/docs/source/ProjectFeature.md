---
features:
  - id: "schedule-service-f1"
    title: "Group creation"
    description: "Create obligatory and elective class groups with validation."
    icon: "plus-square"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "POST /v1/api/groups/obligatory and /elective"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "schedule-service-f2"
    title: "Finder and current-term queries"
    description: "Look up groups by id, key, filters, teacher, or building; current offerings."
    icon: "search"
    category: "performance"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "GET /v1/api/finder/groups/*"
    techStack:
      - "Spring Web"
      - "Spring Data JPA"

  - id: "schedule-service-f3"
    title: "Roster and capacity mutations"
    description: "Update schedules, add/remove teachers, adjust available spots."
    icon: "sliders"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "PUT/PATCH/DELETE under /v1/api/groups"
    techStack:
      - "Spring Web"
      - "Jakarta Validation"

  - id: "schedule-service-f4"
    title: "Asynchronous integration"
    description: "RabbitMQ for notifying other services of schedule changes."
    icon: "rabbit"
    category: "messaging"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/schedule-service"
    highlights:
      - "Spring AMQP listeners/publishers"
    techStack:
      - "Spring AMQP"
      - "RabbitMQ"
---

# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.
