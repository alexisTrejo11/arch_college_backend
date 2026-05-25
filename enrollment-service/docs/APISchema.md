# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

- **type:** REST  

Use **`/v3/api-docs`** for full **`ApiParameter`**, **`ApiRequestBody`**, and **`ApiResponse.schema`**.

---

## Group enrollments (`/v1/api/group-enrollments`)

| Method | Path | Summary |
| --- | --- | --- |
| POST | `/v1/api/group-enrollments/{studentAccountNumber}` | Create / process enrollment |
| GET | `/v1/api/group-enrollments/{enrollmentId}` | Get by id |
| GET | `/v1/api/group-enrollments/by-student/{studentAccountNumber}` | List for student |
| DELETE | `/v1/api/group-enrollments/{enrollmentId}` | Remove enrollment |
| GET | `/v1/api/group-enrollments/lock-date` | Enrollment lock date |

Typical statuses: **200**, **400** (validation), **401**, **404** (missing id), **500**.

---

## Student self-service (`/v1/api/group-enrollments/students`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/group-enrollments/students/my-enrollments` | Current student’s enrollments |
| POST | `/v1/api/group-enrollments/students` | Student enroll |
| DELETE | `/v1/api/group-enrollments/students/{groupKey}/{subjectKey}` | Student drop |

---

## Bulk preload — students (canonical paths)

| Method | Path | Summary |
| --- | --- | --- |
| POST | `/v1/api/students/preload` | Start preload |
| GET | `/v1/api/students/preload/{processId}/status` | Poll status |
| DELETE | `/v1/api/students/clear` | Clear preload data (see implementation) |

**Note:** Java uses `@RequestMapping("v1/api/students/")` without a leading slash — treat public API as above.

---

## Other preload families

Same pattern applies under:

- `v1/api/subjects` — subject preload  
- `v1/api/schedules/` — schedule preload  
- `v1/api/grades/` — grade preload  

Document **`/v1/api/...`** consistently in gateways and external docs.
