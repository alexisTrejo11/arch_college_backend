# API Schema

**API type:** REST

## Teachers

### `POST` /v1/api/teachers

**Create teacher**

Request body per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator / admin |
| **Tags** | teachers, commands |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Validation failure

```json
{}
```

- **401** — Unauthorized

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `DELETE` /v1/api/teachers/{teacherId}

**Delete teacher**

Path teacherId.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict |
| **Tags** | teachers, commands |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /v1/api/teachers/{teacherAccountNumber}/validate

**Validate teacher account number**

Pre-check uniqueness / eligibility before long workflows.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | teachers, validation |

#### Responses

- **200** — Valid or validation result payload

```json
{}
```

- **404** — Not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /teachers/{teacherId}

**Get teacher by id (legacy base path)**

TeacherQueryController uses @RequestMapping("/teachers"). Gateway should expose as /v1/api/teachers/{teacherId}.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | teachers, queries |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /teachers/by-accountNumber/{accountNumber}

**Get teacher by account number (legacy base path)**

Prefer public path /v1/api/teachers/by-accountNumber/{accountNumber} after gateway rewrite.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | teachers, queries |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /teachers/by-ids

**Batch fetch teachers (legacy base path)**

Query params per OpenAPI (e.g. id list).

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | teachers, queries |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Bad request

```json
{}
```

- **500** — Server error

```json
{}
```

---

## Additional notes

# API schema

Matches **`APISchema`** in `docs-schema.ts`. Add request/response **schemas** from `/v3/api-docs`.

## Path normalization

| Controller | Base mapping | Coverage |
| --- | --- | --- |
| TeacherCommandController | `/v1/api/teachers` | create, delete, validate |
| TeacherQueryController | `/teachers` | get by id, by account, by-ids |

**Recommendation:** reverse-proxy **`/v1/api/teachers`** → **`/teachers`** for query routes, or refactor controllers to a single versioned prefix.

## JWT

Protected routes expect **Bearer tokens** signed by **account-service** with the **same secret** as configured here.

