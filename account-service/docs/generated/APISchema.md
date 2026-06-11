# API Schema

**API type:** REST

## Auth

### `POST` /v1/api/auth/signup/student

**Register a student account**

Creates a student user; see OpenAPI for request body schema.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per gateway policy |
| **Tags** | auth |

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

### `POST` /v1/api/auth/signup/teacher

**Register a teacher account**

Creates a teacher user; see OpenAPI for body schema.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per gateway policy |
| **Tags** | auth |

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

### `POST` /v1/api/auth/signup/admin

**Register an admin account**

Creates an admin user; see OpenAPI for body schema.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per gateway policy |
| **Tags** | auth |

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

### `POST` /v1/api/auth/login

**Authenticate and obtain JWT**

Returns JWT on success; see OpenAPI for credential payload.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Stricter profile recommended at gateway |
| **Tags** | auth |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Validation failure

```json
{}
```

- **401** — Invalid credentials

```json
{}
```

- **500** — Server error

```json
{}
```

---

## User

### `GET` /v1/api/user/my-profile

**Current authenticated user profile**

Requires valid JWT for the current principal.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per gateway policy |
| **Tags** | user |

#### Responses

- **200** — Success

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

## Additional notes

# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. For full **`parameters`**, **`requestBody`**, and response schemas, use runtime OpenAPI at **`/v3/api-docs`** or Swagger UI.

## Notes

- **authenticated** reflects typical gateway posture; signup/login routes may be public at the edge but still require server-side validation.
- Add **`ApiParameter`** blocks when you snapshot query/path/header params into this file.

