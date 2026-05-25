# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

**type:** REST — full detail from **`/v3/api-docs`**.

---

## Commands (`/v1/api/teachers`)

| Method | Path | Summary |
| --- | --- | --- |
| POST | `/v1/api/teachers` | Create teacher |
| DELETE | `/v1/api/teachers/{teacherId}` | Delete teacher |
| GET | `/v1/api/teachers/{teacherAccountNumber}/validate` | Validate account number |

---

## Queries (current code: `/teachers`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/teachers/{teacherId}` | Teacher by id |
| GET | `/teachers/by-accountNumber/{accountNumber}` | By account number |
| GET | `/teachers/by-ids` | Batch (query params per OpenAPI) |

**Recommended external paths:** `/v1/api/teachers/{teacherId}`, etc., via **API gateway** rewrite.

---

## JWT

Tokens must be issued by **account-service** and validated with the **same signing secret** configured for **teacher-service**.
