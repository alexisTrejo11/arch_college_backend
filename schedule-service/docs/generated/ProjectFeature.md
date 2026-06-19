# Project Features

## Group creation

Create obligatory and elective class groups with validation.

| Property | Value |
| --- | --- |
| ID | schedule-service-f1 |
| Category | api |
| Status | stable |
| Icon | plus-square |

### Highlights

- POST /v1/api/groups/obligatory and /elective

### Tech stack

- Spring Web
- Spring Data JPA

## Finder and current-term queries

Look up groups by id, key, filters, teacher, or building; current offerings.

| Property | Value |
| --- | --- |
| ID | schedule-service-f2 |
| Category | performance |
| Status | stable |
| Icon | search |

### Highlights

- GET /v1/api/finder/groups/*

### Tech stack

- Spring Web
- Spring Data JPA

## Roster and capacity mutations

Update schedules, add/remove teachers, adjust available spots.

| Property | Value |
| --- | --- |
| ID | schedule-service-f3 |
| Category | api |
| Status | stable |
| Icon | sliders |

### Highlights

- PUT/PATCH/DELETE under /v1/api/groups

### Tech stack

- Spring Web
- Jakarta Validation

## Asynchronous integration

RabbitMQ for notifying other services of schedule changes.

| Property | Value |
| --- | --- |
| ID | schedule-service-f4 |
| Category | messaging |
| Status | stable |
| Icon | rabbit |

### Highlights

- Spring AMQP listeners/publishers

### Tech stack

- Spring AMQP
- RabbitMQ

## Additional notes

# Features

Each entry maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

