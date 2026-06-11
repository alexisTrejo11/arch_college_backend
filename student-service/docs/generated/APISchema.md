# API Schema

**API type:** REST

## Students

### `GET` /v1/api/students/{studentId}

**Get student by id**

Path param studentId.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | students |

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

### `GET` /v1/api/students/by-accountNumber/{accountNumber}

**Get student by account number**

Cross-service lookup key.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | students |

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

### `GET` /v1/api/students/all

**List all students**

May be heavy; paginate at gateway if added.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict / admin |
| **Tags** | students |

#### Responses

- **200** — Success

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /v1/api/students/by

**Filter students**

Query parameters per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | students |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Bad filter

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `POST` /v1/api/students

**Create student**

Body per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students |

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

### `PUT` /v1/api/students/{studentId}

**Update student**

Path studentId; body per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Not found

```json
{}
```

- **400** — Validation failure

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `DELETE` /v1/api/students/{studentId}

**Delete student**

Hard delete or soft per implementation.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict |
| **Tags** | students |

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

### `POST` /v1/api/students/{studentAccount}/set-professionalLine/{professionalLineId}/modality/{professionalLineModality}

**Set professional line and modality**

Coordinates with curriculum-service identifiers.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students, curriculum |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Validation failure

```json
{}
```

- **404** — Student or line not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `POST` /v1/api/students/{studentAccount}/increase-semester-completed

**Increment completed semester counter**

Idempotency expectations depend on implementation.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students |

#### Responses

- **200** — Success

```json
{}
```

- **409** — Conflict / rule violation

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

Matches **`APISchema`** in `docs-schema.ts`. Extend with **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Security note

**authenticated** flags follow typical production posture; wire **Spring Security** to match your institutional roles.

