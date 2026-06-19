---
type: "REST"
httpEndpoints:
  - id: "teacher-create"
    method: "POST"
    urlPath: "/v1/api/teachers"
    summary: "Create teacher"
    description: "Request body per OpenAPI."
    tags:
      - "teachers"
      - "commands"
    authenticated: true
    rateLimit: "Operator / admin"
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

  - id: "teacher-delete"
    method: "DELETE"
    urlPath: "/v1/api/teachers/{teacherId}"
    summary: "Delete teacher"
    description: "Path teacherId."
    tags:
      - "teachers"
      - "commands"
    authenticated: true
    rateLimit: "Strict"
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

  - id: "teacher-validate"
    method: "GET"
    urlPath: "/v1/api/teachers/{teacherAccountNumber}/validate"
    summary: "Validate teacher account number"
    description: "Pre-check uniqueness / eligibility before long workflows."
    tags:
      - "teachers"
      - "validation"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Valid or validation result payload"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "teacher-get-by-id-legacy"
    method: "GET"
    urlPath: "/teachers/{teacherId}"
    summary: "Get teacher by id (legacy base path)"
    description: "TeacherQueryController uses @RequestMapping(\"/teachers\"). Gateway should expose as /v1/api/teachers/{teacherId}."
    tags:
      - "teachers"
      - "queries"
    authenticated: true
    rateLimit: "Gateway default"
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

  - id: "teacher-get-by-account-legacy"
    method: "GET"
    urlPath: "/teachers/by-accountNumber/{accountNumber}"
    summary: "Get teacher by account number (legacy base path)"
    description: "Prefer public path /v1/api/teachers/by-accountNumber/{accountNumber} after gateway rewrite."
    tags:
      - "teachers"
      - "queries"
    authenticated: true
    rateLimit: "Gateway default"
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

  - id: "teacher-by-ids-legacy"
    method: "GET"
    urlPath: "/teachers/by-ids"
    summary: "Batch fetch teachers (legacy base path)"
    description: "Query params per OpenAPI (e.g. id list)."
    tags:
      - "teachers"
      - "queries"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Bad request"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Matches **`APISchema`** in `docs-schema.ts`. Add request/response **schemas** from `/v3/api-docs`.

## Path normalization

| Controller | Base mapping | Coverage |
| --- | --- | --- |
| TeacherCommandController | `/v1/api/teachers` | create, delete, validate |
| TeacherQueryController | `/teachers` | get by id, by account, by-ids |

**Recommendation:** reverse-proxy **`/v1/api/teachers`** → **`/teachers`** for query routes, or refactor controllers to a single versioned prefix.

## JWT

Protected routes expect **Bearer tokens** signed by **account-service** with the **same secret** as configured here.
