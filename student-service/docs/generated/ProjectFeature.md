# Project Features

## Student CRUD

Create, update, and delete student records.

| Property | Value |
| --- | --- |
| ID | student-service-f1 |
| Category | api |
| Status | stable |
| Icon | user |

### Highlights

- POST, PUT, DELETE /v1/api/students

### Tech stack

- Spring Web
- Spring Data JPA

## Flexible student lookups

Fetch by id, account number, list all, or composite /by filters.

| Property | Value |
| --- | --- |
| ID | student-service-f2 |
| Category | performance |
| Status | stable |
| Icon | search |

### Highlights

- GET /v1/api/students/* query surface

### Tech stack

- Spring Data JPA

## Professional line and modality

Assign professional line with modality for a student account.

| Property | Value |
| --- | --- |
| ID | student-service-f3 |
| Category | integration |
| Status | stable |
| Icon | git-branch |

### Highlights

- POST .../set-professionalLine/.../modality/...

### Tech stack

- Spring Web

## Progression and messaging

Increment completed semester; emit integration events via RabbitMQ.

| Property | Value |
| --- | --- |
| ID | student-service-f4 |
| Category | messaging |
| Status | stable |
| Icon | rabbit |

### Highlights

- POST .../increase-semester-completed
- Spring AMQP

### Tech stack

- Spring AMQP
- RabbitMQ

## Additional notes

# Features

Each entry maps to **`ProjectFeature`** in `docs-schema.ts`.

