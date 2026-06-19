# Project Features

## Create and delete teachers

POST to create; DELETE by teacher id.

| Property | Value |
| --- | --- |
| ID | teacher-service-f1 |
| Category | api |
| Status | stable |
| Icon | user-plus |

### Highlights

- POST /v1/api/teachers
- DELETE /v1/api/teachers/{teacherId}

### Tech stack

- Spring Web
- Spring Data JPA

## Account number validation

GET validate before committing downstream workflows.

| Property | Value |
| --- | --- |
| ID | teacher-service-f2 |
| Category | security |
| Status | stable |
| Icon | check-circle |

### Highlights

- GET /v1/api/teachers/{teacherAccountNumber}/validate

### Tech stack

- Spring Web

## Teacher queries

Fetch by id, account number, or batch ids (note /teachers base path).

| Property | Value |
| --- | --- |
| ID | teacher-service-f3 |
| Category | api |
| Status | stable |
| Icon | search |

### Highlights

- GET /teachers/{teacherId}
- GET /teachers/by-ids

### Tech stack

- Spring Web
- Spring Data JPA

## JWT and messaging

Resource server JWT validation; RabbitMQ for integration.

| Property | Value |
| --- | --- |
| ID | teacher-service-f4 |
| Category | integration |
| Status | stable |
| Icon | shield |

### Highlights

- Spring Security + JWT
- Spring AMQP

### Tech stack

- Spring Security
- Spring AMQP
- RabbitMQ

## Additional notes

# Features

Maps to **`ProjectFeature`** in `docs-schema.ts`.

