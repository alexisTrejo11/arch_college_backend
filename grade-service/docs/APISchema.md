# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

**type:** REST — canonical spec from **`/v3/api-docs`**.

## Grades (`/v1/api/grades`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/grades/{gradeId}` | By id |
| GET | `/v1/api/grades/all` | Filtered + paginated search |
| GET | `/v1/api/grades/pending-validation` | Pending authorization |
| PUT | `/v1/api/grades/{gradeId}/authorize` | Authorize |
| DELETE | `/v1/api/grades/{gradeId}` | Soft delete |

## Grade groups (`/v1/api/grades/groups`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/grades/groups/{groupId}` | Group detail |
| GET | `/v1/api/grades/groups/pending` | Pending groups (paged) |

## Teacher grading (`/v1/api/teachers/grades/groups`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/teachers/grades/groups/pending-grade` | JWT: pending for teacher |
| GET | `/v1/api/teachers/grades/groups/my-history` | JWT: graded history |
| PUT | `/v1/api/teachers/grades/groups/set-grades` | Assign grades (body) |

## Student (`/v1/api/students`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/students/get-my-academic-history` | JWT: full history |
| GET | `/v1/api/students/get-my-annually-grades` | JWT: by year |
| GET | `/v1/api/students/get-my-current-enrollments` | JWT: current |

## Academic history (ADMIN)

| Method | Path | Summary |
| --- | --- | --- |
| POST | `/v1/api/academic-histories/student/{accountNumber}` | ADMIN: load history (class omits leading `/` on mapping—normalize at gateway) |

Typical HTTP statuses: **200**, **400**, **401**, **403**, **404**, **500** — see runtime OpenAPI for bodies.

## Security

Teacher and student routes derive **account numbers from JWT**; the academic history route above uses **`hasRole('ADMIN')`**.
