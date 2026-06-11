---
type: "REST"
httpEndpoints:
  - id: "student-get-by-id"
    method: "GET"
    urlPath: "/v1/api/students/{studentId}"
    summary: "Get student by id"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Gateway default"
    description: "Path param studentId."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-get-by-account"
    method: "GET"
    urlPath: "/v1/api/students/by-accountNumber/{accountNumber}"
    summary: "Get student by account number"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Gateway default"
    description: "Cross-service lookup key."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-list-all"
    method: "GET"
    urlPath: "/v1/api/students/all"
    summary: "List all students"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Strict / admin"
    description: "May be heavy; paginate at gateway if added."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-filter"
    method: "GET"
    urlPath: "/v1/api/students/by"
    summary: "Filter students"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Gateway default"
    description: "Query parameters per OpenAPI."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Bad filter"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-create"
    method: "POST"
    urlPath: "/v1/api/students"
    summary: "Create student"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Authenticated"
    description: "Body per OpenAPI."
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

  - id: "student-update"
    method: "PUT"
    urlPath: "/v1/api/students/{studentId}"
    summary: "Update student"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Authenticated"
    description: "Path studentId; body per OpenAPI."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-delete"
    method: "DELETE"
    urlPath: "/v1/api/students/{studentId}"
    summary: "Delete student"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Strict"
    description: "Hard delete or soft per implementation."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-set-prof-line"
    method: "POST"
    urlPath: "/v1/api/students/{studentAccount}/set-professionalLine/{professionalLineId}/modality/{professionalLineModality}"
    summary: "Set professional line and modality"
    tags:
      - "students"
      - "curriculum"
    authenticated: true
    rateLimit: "Authenticated"
    description: "Coordinates with curriculum-service identifiers."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 404
        description: "Student or line not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-increment-semester"
    method: "POST"
    urlPath: "/v1/api/students/{studentAccount}/increase-semester-completed"
    summary: "Increment completed semester counter"
    tags:
      - "students"
    authenticated: true
    rateLimit: "Authenticated"
    description: "Idempotency expectations depend on implementation."
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 409
        description: "Conflict / rule violation"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Matches **`APISchema`** in `docs-schema.ts`. Extend with **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Security note

**authenticated** flags follow typical production posture; wire **Spring Security** to match your institutional roles.
