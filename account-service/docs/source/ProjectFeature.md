---
features:
  - id: "account-service-f1"
    title: "Student, teacher, and admin registration"
    description: "Dedicated signup endpoints per persona."
    icon: "user-plus"
    category: "authentication"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "Separate routes for student, teacher, and admin signup"
    techStack:
      - "Spring Boot"
      - "Spring Security"

  - id: "account-service-f2"
    title: "JWT login"
    description: "Issues JWT after successful authentication."
    icon: "key"
    category: "security"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "POST /v1/api/auth/login"
    techStack:
      - "Spring Security"
      - "JJWT or configured JWT library"

  - id: "account-service-f3"
    title: "Authenticated profile"
    description: "Current user profile for authenticated principal."
    icon: "user"
    category: "api"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "GET /v1/api/user/my-profile"
    techStack:
      - "Spring Web"
      - "Spring Security"

  - id: "account-service-f4"
    title: "Service discovery registration"
    description: "Registers with Eureka for use by gateways or other services."
    icon: "radar"
    category: "integration"
    status: "stable"
    githubExampleUrl: "https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service"
    highlights:
      - "Eureka client enabled via Spring Cloud"
    techStack:
      - "Spring Cloud Netflix Eureka"
---

# Features

Each list item matches **`ProjectFeature`** in `docs-schema.ts` (`category` uses **`FeatureCategory`**, `status` uses **`FeatureStatus`**).
