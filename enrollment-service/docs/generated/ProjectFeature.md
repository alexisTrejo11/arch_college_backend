# Project Features

## Group enrollment operations

Create and cancel enrollments by student account; fetch by id or student; enrollment lock date.

| Property | Value |
| --- | --- |
| ID | enrollment-service-f1 |
| Category | api |
| Status | stable |
| Icon | clipboard |

### Highlights

- POST/DELETE /v1/api/group-enrollments/...
- GET lock-date

### Tech stack

- Spring Web
- Spring Data JPA

## Student self-service enrollment

Students list, add, and drop enrollments by group/subject keys.

| Property | Value |
| --- | --- |
| ID | enrollment-service-f2 |
| Category | api |
| Status | stable |
| Icon | user |

### Highlights

- GET my-enrollments; POST/DELETE student routes

### Tech stack

- Spring Security
- Spring Web

## Bulk preload pipelines

Operational preload for students, subjects, schedules, grades with status polling.

| Property | Value |
| --- | --- |
| ID | enrollment-service-f3 |
| Category | integration |
| Status | stable |
| Icon | download |

### Highlights

- POST preload, GET status, DELETE clear per domain

### Tech stack

- Spring Data MongoDB

## Polyglot persistence

Postgres for enrollments; Mongo for flexible preload/job documents.

| Property | Value |
| --- | --- |
| ID | enrollment-service-f4 |
| Category | database |
| Status | stable |
| Icon | database |

### Highlights

- Dual datasource configuration via Spring Cloud

### Tech stack

- PostgreSQL
- MongoDB

## Additional notes

# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

