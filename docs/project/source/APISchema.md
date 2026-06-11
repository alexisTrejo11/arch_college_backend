---
type: REST
httpEndpoints:
  # account-service
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
  # curriculum-service
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
  # enrollment-service
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
  # grade-service
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
  # schedule-service
  - id: "groups-create-obligatory"
    method: "POST"
    urlPath: "/v1/api/groups/obligatory"
    summary: "Create obligatory group"
    description: "Request body per OpenAPI; creates an obligatory offering."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
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

  - id: "groups-create-elective"
    method: "POST"
    urlPath: "/v1/api/groups/elective"
    summary: "Create elective group"
    description: "Request body per OpenAPI; creates an elective offering."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
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

  - id: "finder-group-by-id"
    method: "GET"
    urlPath: "/v1/api/finder/groups/{groupId}"
    summary: "Get group by id"
    description: "Path param groupId."
    tags:
      - "schedule"
      - "finder"
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

  - id: "finder-group-by-key"
    method: "GET"
    urlPath: "/v1/api/finder/groups/key/{key}"
    summary: "Get group by natural key"
    description: "Domain key in path."
    tags:
      - "schedule"
      - "finder"
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

  - id: "finder-current"
    method: "GET"
    urlPath: "/v1/api/finder/groups/current"
    summary: "Current groups slice"
    description: "Operational query for current term/window."
    tags:
      - "schedule"
      - "finder"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-update-schedule"
    method: "PUT"
    urlPath: "/v1/api/groups/update-schedule"
    summary: "Update meeting schedule"
    description: "Body defines new meeting pattern."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
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

  - id: "groups-add-teacher"
    method: "PUT"
    urlPath: "/v1/api/groups/{key}/add-teacher/{teacherId}"
    summary: "Assign teacher to group"
    description: "Natural key and teacher id in path."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Group or teacher not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-remove-teacher"
    method: "PATCH"
    urlPath: "/v1/api/groups/{key}/remove-teacher/{teacherId}"
    summary: "Remove teacher from group"
    description: "Partial update / roster change."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
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

  - id: "groups-delete"
    method: "DELETE"
    urlPath: "/v1/api/groups/{key}"
    summary: "Delete group by key"
    description: "Removes group identified by domain key."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier strict"
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

  - id: "groups-capacity-increase"
    method: "PUT"
    urlPath: "/v1/api/groups/{groupId}/increase-spot"
    summary: "Increase available spots"
    description: "Capacity mutation by groupId."
    tags:
      - "schedule"
      - "capacity"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 409
        description: "Conflict / invariant breach"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
  # student-service
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
  # teacher-service
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

# API Schema

> Auto-generated by `docs/project/merge_service_sources.py`. Edit service-level `{service}/docs/source/*.md` files, then regenerate.

<!-- BEGIN account-service -->
<!-- Source: account-service/docs/source/APISchema.md -->
# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. For full **`parameters`**, **`requestBody`**, and response schemas, use runtime OpenAPI at **`/v3/api-docs`** or Swagger UI.

## Notes

- **authenticated** reflects typical gateway posture; signup/login routes may be public at the edge but still require server-side validation.
- Add **`ApiParameter`** blocks when you snapshot query/path/header params into this file.

<!-- END account-service -->

<!-- BEGIN curriculum-service -->
<!-- Source: curriculum-service/docs/source/APISchema.md -->
# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. Expand with **`parameters`**, **`requestBody`**, and typed **`responses[].schema`** from `/v3/api-docs`.

## Path consistency

- **`SubjectSeriesController`** is declared as `v1/api/academic_curriculum/subject-series` without a leading slash; treat public paths as **`/v1/api/...`** after gateway normalization.

<!-- END curriculum-service -->

<!-- BEGIN enrollment-service -->
<!-- Source: enrollment-service/docs/source/APISchema.md -->
# API schema

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Extend with **`parameters`**, **`requestBody`**, and **`responses[].schema`** from `/v3/api-docs`.

## Path note

Several controllers omit a **leading slash** in `@RequestMapping` (e.g. `v1/api/students/`). External docs and gateways should normalize to **`/v1/api/...`**.

## Related preload families

Same **preload / status / clear** pattern exists for **subjects**, **schedules**, and **grades** under `v1/api/subjects`, `v1/api/schedules/`, `v1/api/grades/` — mirror this endpoint shape when documenting or generating OpenAPI.

<!-- END enrollment-service -->

<!-- BEGIN grade-service -->
<!-- Source: grade-service/docs/source/APISchema.md -->
# API schema

Subset aligned with Java controllers; canonical contracts live in **`/v3/api-docs`** and Swagger UI.

## Path normalization

- Prefer documenting public routes as **`/v1/api/...`** even when `@RequestMapping` omits a leading **`/`**.

<!-- END grade-service -->

<!-- BEGIN schedule-service -->
<!-- Source: schedule-service/docs/source/APISchema.md -->
# API schema

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Add **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Additional routes

Also implemented (not exhaustively listed above):

- `GET /v1/api/finder/groups/by-ids`, `GET .../by`, `GET .../current/by-teacher/{teacherId}`, `GET .../current/by-building/{buildingLetter}`
- `PUT /v1/api/groups/{groupId}/add_spots/{spotsToAdd}`, `PUT .../decrease-spot`

Mirror the same **`responses`** block pattern when extending this file.

<!-- END schedule-service -->

<!-- BEGIN student-service -->
<!-- Source: student-service/docs/source/APISchema.md -->
# API schema

Matches **`APISchema`** in `docs-schema.ts`. Extend with **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Security note

**authenticated** flags follow typical production posture; wire **Spring Security** to match your institutional roles.

<!-- END student-service -->

<!-- BEGIN teacher-service -->
<!-- Source: teacher-service/docs/source/APISchema.md -->
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

<!-- END teacher-service -->
