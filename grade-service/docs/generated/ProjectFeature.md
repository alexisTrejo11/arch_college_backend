# Project Features

## Grade queries and commands

Search and fetch grades; authorize or soft-delete by id.

| Property | Value |
| --- | --- |
| ID | grade-service-f1 |
| Category | api |
| Status | stable |
| Icon | clipboard-list |

### Highlights

- GET /v1/api/grades/{gradeId}
- GET /v1/api/grades/all (filters + pagination)
- GET /v1/api/grades/pending-validation
- PUT /v1/api/grades/{gradeId}/authorize
- DELETE /v1/api/grades/{gradeId}

### Tech stack

- Spring Web
- Spring Data JPA

## Groups and teacher grading

Inspect groups, list pending grading work for the authenticated teacher, submit grades.

| Property | Value |
| --- | --- |
| ID | grade-service-f2 |
| Category | api |
| Status | stable |
| Icon | users |

### Highlights

- GET /v1/api/grades/groups/{groupId}
- GET /v1/api/grades/groups/pending
- GET /v1/api/teachers/grades/groups/pending-grade
- GET /v1/api/teachers/grades/groups/my-history
- PUT /v1/api/teachers/grades/groups/set-grades

### Tech stack

- Spring Web
- Spring Security

## Student academic views

JWT-scoped reads for academic history, annual grades, and current enrollments.

| Property | Value |
| --- | --- |
| ID | grade-service-f3 |
| Category | api |
| Status | stable |
| Icon | graduation-cap |

### Highlights

- GET /v1/api/students/get-my-academic-history
- GET /v1/api/students/get-my-annually-grades
- GET /v1/api/students/get-my-current-enrollments

### Tech stack

- Spring Web
- Spring Security

## Academic history admin and messaging

ADMIN route to load academic history by account number; Rabbit listeners support async academic history updates.

| Property | Value |
| --- | --- |
| ID | grade-service-f4 |
| Category | integration |
| Status | stable |
| Icon | shield |

### Highlights

- POST /v1/api/academic-histories/student/{accountNumber} (ADMIN; maps from class path without leading slash)
- Spring AMQP consumers (see AcademicHistoryReceiver)

### Tech stack

- Spring Security
- Spring AMQP
- Spring Data MongoDB

## Additional notes

# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.

