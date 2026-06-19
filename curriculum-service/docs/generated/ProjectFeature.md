# Project Features

## Careers, areas, and professional lines

CRUD and lookup APIs for organizational structure of the catalog.

| Property | Value |
| --- | --- |
| ID | curriculum-service-f1 |
| Category | api |
| Status | stable |
| Icon | layers |

### Highlights

- GET/POST/PUT for careers, areas, professional lines

### Tech stack

- Spring Web
- Spring Data JPA

## Obligatory and elective subjects

Manage subject definitions with filters by area, career, semester.

| Property | Value |
| --- | --- |
| ID | curriculum-service-f2 |
| Category | database |
| Status | stable |
| Icon | book |

### Highlights

- /v1/api/subjects/obligatory and /v1/api/subjects/electives

### Tech stack

- Spring Data JPA
- PostgreSQL

## Subject series graph

Link obligatory and elective subjects through series for sequencing rules.

| Property | Value |
| --- | --- |
| ID | curriculum-service-f3 |
| Category | integration |
| Status | stable |
| Icon | share |

### Highlights

- Subject series CRUD and queries

### Tech stack

- Spring Web

## Read-optimized catalog queries

List endpoints and filters for admin and downstream consumption.

| Property | Value |
| --- | --- |
| ID | curriculum-service-f4 |
| Category | performance |
| Status | stable |
| Icon | search |

### Highlights

- by-area, by-career, by-semester lookups

### Tech stack

- Spring Data JPA

## Additional notes

# Features

Each item maps to **`ProjectFeature`** (`FeatureCategory`, `FeatureStatus`) in `docs-schema.ts`.

