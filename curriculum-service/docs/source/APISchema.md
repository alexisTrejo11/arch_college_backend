---
type: "REST"
httpEndpoints:
  - id: "careers-list"
    method: "GET"
    urlPath: "/v1/api/careers/all"
    summary: "List all careers"
    description: "Returns catalog careers; see OpenAPI for response schema."
    tags:
      - "curriculum"
      - "careers"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "careers-get"
    method: "GET"
    urlPath: "/v1/api/careers/{careerId}"
    summary: "Get career by id"
    description: "Path param careerId."
    tags:
      - "curriculum"
      - "careers"
    authenticated: false
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

  - id: "careers-create"
    method: "POST"
    urlPath: "/v1/api/careers"
    summary: "Create career"
    description: "Request body per OpenAPI."
    tags:
      - "curriculum"
      - "careers"
    authenticated: true
    rateLimit: "Authenticated / admin"
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

  - id: "areas-with-subjects"
    method: "GET"
    urlPath: "/v1/api/areas/{areaId}/with-subjects"
    summary: "Area with subjects"
    description: "Aggregated read for an area."
    tags:
      - "curriculum"
      - "areas"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "professional-lines-list"
    method: "GET"
    urlPath: "/v1/api/professional_lines/all"
    summary: "List professional lines"
    description: "All professional line entities."
    tags:
      - "curriculum"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "obligatory-subjects-list"
    method: "GET"
    urlPath: "/v1/api/subjects/obligatory/all"
    summary: "List obligatory subjects"
    description: "Full obligatory catalog slice."
    tags:
      - "curriculum"
      - "subjects"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "elective-subjects-list"
    method: "GET"
    urlPath: "/v1/api/subjects/electives/all"
    summary: "List elective subjects"
    description: "Full elective catalog slice."
    tags:
      - "curriculum"
      - "subjects"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "subject-series-list"
    method: "GET"
    urlPath: "/v1/api/academic_curriculum/subject-series/all"
    summary: "List subject series"
    description: "Controller maps v1/api/... (leading slash recommended at gateway)."
    tags:
      - "curriculum"
      - "series"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "subject-series-create"
    method: "POST"
    urlPath: "/v1/api/academic_curriculum/subject-series"
    summary: "Create subject series"
    description: "Body links obligatory/elective subjects per OpenAPI."
    tags:
      - "curriculum"
      - "series"
    authenticated: true
    rateLimit: "Authenticated / admin"
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
---

# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. Expand with **`parameters`**, **`requestBody`**, and typed **`responses[].schema`** from `/v3/api-docs`.

## Path consistency

- **`SubjectSeriesController`** is declared as `v1/api/academic_curriculum/subject-series` without a leading slash; treat public paths as **`/v1/api/...`** after gateway normalization.
