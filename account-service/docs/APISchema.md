# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` / `ApiEndpoint` in [docs-schema.ts](../../docs-schema.ts)).

- **type:** `REST`

Use runtime OpenAPI (`/v3/api-docs`) for complete `parameters`, `requestBody`, and response **schema** objects.

---

## Endpoints

### POST `/v1/api/auth/signup/student`

| Field | Value |
| --- | --- |
| **id** | `auth-signup-student` |
| **summary** | Register a student account |
| **authenticated** | true (typical gateway posture) |
| **rateLimit** | Per gateway policy |

**Tags:** `auth`

**Responses**

| Status | Description |
| --- | --- |
| 200 | Success |
| 400 | Validation failure |
| 401 | Unauthorized |
| 500 | Server error |

---

### POST `/v1/api/auth/signup/teacher`

| **id** | `auth-signup-teacher` |
| **summary** | Register a teacher account |
| **authenticated** | true |
| **tags** | auth |

**Responses:** 200, 400, 401, 500 (as above).

---

### POST `/v1/api/auth/signup/admin`

| **id** | `auth-signup-admin` |
| **summary** | Register an admin account |
| **authenticated** | true |
| **tags** | auth |

**Responses:** 200, 400, 401, 500.

---

### POST `/v1/api/auth/login`

| **id** | `auth-login` |
| **summary** | Authenticate and obtain JWT |
| **authenticated** | false |
| **rateLimit** | Stricter profile recommended at gateway |
| **tags** | auth |

**Responses:** 200, 400, 401 (`Invalid credentials`), 500.

---

### GET `/v1/api/user/my-profile`

| **id** | `user-my-profile` |
| **summary** | Current authenticated user profile |
| **authenticated** | true |
| **tags** | user |

**Responses:** 200, 401, 500.

---

## Notes

- When mirroring **`ApiEndpoint`** fully, add **`ApiParameter`**, **`ApiRequestBody`**, and typed **`ApiResponse.schema`** blocks from OpenAPI exports.
- **`authenticated`** describes the usual expectation at the edge; adjust per your gateway rules.
