---
type: "REST"
httpEndpoints:
  - id: "grade-get-by-id"
    method: "GET"
    urlPath: "/v1/api/grades/{gradeId}"
    summary: "Get grade by id"
    description: "Returns GradeDTO or 404 via ResponseWrapper."
    tags:
      - "grades"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Grade not found"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "grade-search"
    method: "GET"
    urlPath: "/v1/api/grades/all"
    summary: "Search grades with filters"
    description: "Query params: accountNumber, schoolPeriod, subjectId, subjectType, page, size, sort."
    tags:
      - "grades"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Paginated grades"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "grade-pending-validation"
    method: "GET"
    urlPath: "/v1/api/grades/pending-validation"
    summary: "Pending validation grades"
    description: "Paginated list for authorizer workflows."
    tags:
      - "grades"
    authenticated: true
    rateLimit: "Gateway default"
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

  - id: "grade-authorize"
    method: "PUT"
    urlPath: "/v1/api/grades/{gradeId}/authorize"
    summary: "Authorize grade"
    description: "Validates and authorizes grade by id."
    tags:
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
    responses:
      - status: 200
        description: "Authorized"
        example: {}
      - status: 400
        description: "Cannot authorize"
        example: {}
      - status: 404
        description: "Grade not found"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "grade-delete"
    method: "DELETE"
    urlPath: "/v1/api/grades/{gradeId}"
    summary: "Soft delete grade"
    description: "Soft delete by id."
    tags:
      - "grades"
    authenticated: true
    rateLimit: "Strict / role-gated at gateway if needed"
    responses:
      - status: 200
        description: "Deleted"
        example: {}
      - status: 404
        description: "Grade not found"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "grade-group-by-id"
    method: "GET"
    urlPath: "/v1/api/grades/groups/{groupId}"
    summary: "Get grade group by id"
    description: "GroupDTO for a grading group."
    tags:
      - "grade-groups"
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

  - id: "grade-groups-pending"
    method: "GET"
    urlPath: "/v1/api/grades/groups/pending"
    summary: "Pending grade groups"
    description: "Paginated pending groups."
    tags:
      - "grade-groups"
    authenticated: true
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "teacher-pending-grade"
    method: "GET"
    urlPath: "/v1/api/teachers/grades/groups/pending-grade"
    summary: "Teacher pending groups to grade"
    description: "JWT derives teacher account number."
    tags:
      - "teachers"
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
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

  - id: "teacher-grading-history"
    method: "GET"
    urlPath: "/v1/api/teachers/grades/groups/my-history"
    summary: "Teacher graded groups history"
    description: "JWT derives teacher account number."
    tags:
      - "teachers"
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
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

  - id: "teacher-set-grades"
    method: "PUT"
    urlPath: "/v1/api/teachers/grades/groups/set-grades"
    summary: "Assign grades to group"
    description: "Body: TeacherQualificationDTO; validates period and qualification."
    tags:
      - "teachers"
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
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

  - id: "student-academic-history"
    method: "GET"
    urlPath: "/v1/api/students/get-my-academic-history"
    summary: "My academic history"
    description: "JWT derives student account number."
    tags:
      - "students"
      - "academic-history"
    authenticated: true
    rateLimit: "Authenticated"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 404
        description: "History not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "student-annual-grades"
    method: "GET"
    urlPath: "/v1/api/students/get-my-annually-grades"
    summary: "My annual grades"
    description: "Grades grouped by year for authenticated student."
    tags:
      - "students"
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
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

  - id: "student-current-enrollments"
    method: "GET"
    urlPath: "/v1/api/students/get-my-current-enrollments"
    summary: "My current enrollment grades"
    description: "Current term enrollment-related grade rows."
    tags:
      - "students"
      - "grades"
    authenticated: true
    rateLimit: "Authenticated"
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

  - id: "admin-academic-history-by-account"
    method: "POST"
    urlPath: "/v1/api/academic-histories/student/{accountNumber}"
    summary: "Load academic history (ADMIN)"
    description: "Despite POST, returns history for accountNumber; requires ADMIN. Class uses @RequestMapping without leading slash—normalize to this path at the edge."
    tags:
      - "academic-history"
      - "admin"
    authenticated: true
    rateLimit: "Strict / admin"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Invalid account number"
        example: {}
      - status: 403
        description: "Forbidden"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Subset aligned with Java controllers; canonical contracts live in **`/v3/api-docs`** and Swagger UI.

## Path normalization

- Prefer documenting public routes as **`/v1/api/...`** even when `@RequestMapping` omits a leading **`/`**.
