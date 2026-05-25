# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

**type:** REST — extend from **`/v3/api-docs`**.

## Students (`/v1/api/students`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/students/{studentId}` | By id |
| GET | `/v1/api/students/by-accountNumber/{accountNumber}` | By account |
| GET | `/v1/api/students/all` | List all |
| GET | `/v1/api/students/by` | Filtered query |
| POST | `/v1/api/students` | Create |
| PUT | `/v1/api/students/{studentId}` | Update |
| DELETE | `/v1/api/students/{studentId}` | Delete |
| POST | `/v1/api/students/{studentAccount}/set-professionalLine/{professionalLineId}/modality/{professionalLineModality}` | Set line + modality |
| POST | `/v1/api/students/{studentAccount}/increase-semester-completed` | Bump semester |

Typical HTTP statuses: **200**, **400**, **401**, **404**, **409**, **500** — see runtime OpenAPI for bodies.

## Security

Configure **Spring Security** so mutating routes match your **RBAC**; `authenticated` flags in Obsidian are indicative.
