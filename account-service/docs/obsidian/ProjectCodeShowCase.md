---
codeExamples:
  - id: "account-service-example-1"
    title: "Auth and user controllers"
    description: "REST entry points for signup, login, and profile."
    category: "backend"
    duration: "5 min read"
    views: 0
    tags:
      - "spring"
      - "rest"
      - "security"
    files:
      - name: "AuthController.java"
        path: "account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/AuthController.java"
        language: "java"
        content: |
          // See repository for full sources. Endpoints under /v1/api/auth (signup/*, login).
        highlighted: true
        explanation: "Handles authentication and registration flows."
      - name: "UserController.java"
        path: "account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/UserController.java"
        language: "java"
        content: |
          // See repository for full sources. Profile endpoint /v1/api/user/my-profile.
        highlighted: false
        explanation: "Authenticated user read model exposure."
---

# Code showcase

Front matter matches **`ProjectCodeShowCase`** / **`CodeExample`** / **`CodeFile`** in `docs-schema.ts`. Replace `content` stubs with real excerpts for a portfolio renderer.
