---
type: "REST"
httpEndpoints:
  - id: "enroll-create"
    method: "POST"
    urlPath: "/v1/api/group-enrollments/{studentAccountNumber}"
    summary: "Create or process enrollment for student"
    description: "Body per OpenAPI; student account in path."
    tags:
      - "enrollment"
      - "commands"
    authenticated: true
    rateLimit: "Strict during peak registration"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation / business rule failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "enroll-get"
    method: "GET"
    urlPath: "/v1/api/group-enrollments/{enrollmentId}"
    summary: "Get enrollment by id"
    description: "Path param enrollmentId."
    tags:
      - "enrollment"
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

  - id: "enroll-by-student"
    method: "GET"
    urlPath: "/v1/api/group-enrollments/by-student/{studentAccountNumber}"
    summary: "List enrollments for student"
    description: "Finder for all enrollments tied to account number."
    tags:
      - "enrollment"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "enroll-delete"
    method: "DELETE"
    urlPath: "/v1/api/group-enrollments/{enrollmentId}"
    summary: "Remove enrollment"
    description: "Deletes or cancels enrollment identified by id."
    tags:
      - "enrollment"
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

  - id: "enroll-lock-date"
    method: "GET"
    urlPath: "/v1/api/group-enrollments/lock-date"
    summary: "Enrollment lock date"
    description: "Returns configured lock date for enrollment window."
    tags:
      - "enrollment"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-my-enrollments"
    method: "GET"
    urlPath: "/v1/api/group-enrollments/students/my-enrollments"
    summary: "Current student enrollments"
    description: "Self-service list for authenticated student principal."
    tags:
      - "enrollment"
      - "students"
    authenticated: true
    rateLimit: "Per-user"
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

  - id: "student-enroll-create"
    method: "POST"
    urlPath: "/v1/api/group-enrollments/students"
    summary: "Student enroll action"
    description: "Student-facing POST body per OpenAPI."
    tags:
      - "enrollment"
      - "students"
    authenticated: true
    rateLimit: "Per-user strict"
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

  - id: "student-enroll-delete"
    method: "DELETE"
    urlPath: "/v1/api/group-enrollments/students/{groupKey}/{subjectKey}"
    summary: "Student drop enrollment"
    description: "Path keys identify group and subject offering."
    tags:
      - "enrollment"
      - "students"
    authenticated: true
    rateLimit: "Per-user strict"
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

  - id: "preload-students-start"
    method: "POST"
    urlPath: "/v1/api/students/preload"
    summary: "Start student preload job"
    description: "Controller base is v1/api/students/ — use leading slash at gateway."
    tags:
      - "enrollment"
      - "preload"
    authenticated: true
    rateLimit: "Admin / batch"
    responses:
      - status: 200
        description: "Job accepted / started"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "preload-students-status"
    method: "GET"
    urlPath: "/v1/api/students/preload/{processId}/status"
    summary: "Student preload status"
    description: "Polls Mongo-backed job state for processId."
    tags:
      - "enrollment"
      - "preload"
    authenticated: true
    rateLimit: "Admin / batch"
    responses:
      - status: 200
        description: "Status payload"
        example: {}
      - status: 404
        description: "Unknown process"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Extend with **`parameters`**, **`requestBody`**, and **`responses[].schema`** from `/v3/api-docs`.

## Path note

Several controllers omit a **leading slash** in `@RequestMapping` (e.g. `v1/api/students/`). External docs and gateways should normalize to **`/v1/api/...`**.

## Related preload families

Same **preload / status / clear** pattern exists for **subjects**, **schedules**, and **grades** under `v1/api/subjects`, `v1/api/schedules/`, `v1/api/grades/` — mirror this endpoint shape when documenting or generating OpenAPI.
