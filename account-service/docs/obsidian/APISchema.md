---
type: "REST"
httpEndpoints:
  - id: "auth-signup-student"
    method: "POST"
    urlPath: "/v1/api/auth/signup/student"
    summary: "Register a student account"
    description: "Creates a student user; see OpenAPI for request body schema."
    tags:
      - "auth"
    authenticated: true
    rateLimit: "Per gateway policy"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "auth-signup-teacher"
    method: "POST"
    urlPath: "/v1/api/auth/signup/teacher"
    summary: "Register a teacher account"
    description: "Creates a teacher user; see OpenAPI for body schema."
    tags:
      - "auth"
    authenticated: true
    rateLimit: "Per gateway policy"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "auth-signup-admin"
    method: "POST"
    urlPath: "/v1/api/auth/signup/admin"
    summary: "Register an admin account"
    description: "Creates an admin user; see OpenAPI for body schema."
    tags:
      - "auth"
    authenticated: true
    rateLimit: "Per gateway policy"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "auth-login"
    method: "POST"
    urlPath: "/v1/api/auth/login"
    summary: "Authenticate and obtain JWT"
    description: "Returns JWT on success; see OpenAPI for credential payload."
    tags:
      - "auth"
    authenticated: false
    rateLimit: "Stricter profile recommended at gateway"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Invalid credentials"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "user-my-profile"
    method: "GET"
    urlPath: "/v1/api/user/my-profile"
    summary: "Current authenticated user profile"
    description: "Requires valid JWT for the current principal."
    tags:
      - "user"
    authenticated: true
    rateLimit: "Per gateway policy"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. For full **`parameters`**, **`requestBody`**, and response schemas, use runtime OpenAPI at **`/v3/api-docs`** or Swagger UI.

## Notes

- **authenticated** reflects typical gateway posture; signup/login routes may be public at the edge but still require server-side validation.
- Add **`ApiParameter`** blocks when you snapshot query/path/header params into this file.
