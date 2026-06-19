# API Schema

**API type:** REST

## Enrollment

### `POST` /v1/api/group-enrollments/{studentAccountNumber}

**Create or process enrollment for student**

Body per OpenAPI; student account in path.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict during peak registration |
| **Tags** | enrollment, commands |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Validation / business rule failure

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

### `GET` /v1/api/group-enrollments/{enrollmentId}

**Get enrollment by id**

Path param enrollmentId.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | enrollment |

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

### `GET` /v1/api/group-enrollments/by-student/{studentAccountNumber}

**List enrollments for student**

Finder for all enrollments tied to account number.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | enrollment |

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

### `DELETE` /v1/api/group-enrollments/{enrollmentId}

**Remove enrollment**

Deletes or cancels enrollment identified by id.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict |
| **Tags** | enrollment |

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

### `GET` /v1/api/group-enrollments/lock-date

**Enrollment lock date**

Returns configured lock date for enrollment window.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | enrollment |

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

### `GET` /v1/api/group-enrollments/students/my-enrollments

**Current student enrollments**

Self-service list for authenticated student principal.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per-user |
| **Tags** | enrollment, students |

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

### `POST` /v1/api/group-enrollments/students

**Student enroll action**

Student-facing POST body per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per-user strict |
| **Tags** | enrollment, students |

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

### `DELETE` /v1/api/group-enrollments/students/{groupKey}/{subjectKey}

**Student drop enrollment**

Path keys identify group and subject offering.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Per-user strict |
| **Tags** | enrollment, students |

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

### `POST` /v1/api/students/preload

**Start student preload job**

Controller base is v1/api/students/ — use leading slash at gateway.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Admin / batch |
| **Tags** | enrollment, preload |

#### Responses

- **200** — Job accepted / started

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

### `GET` /v1/api/students/preload/{processId}/status

**Student preload status**

Polls Mongo-backed job state for processId.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Admin / batch |
| **Tags** | enrollment, preload |

#### Responses

- **200** — Status payload

```json
{}
```

- **404** — Unknown process

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

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Extend with **`parameters`**, **`requestBody`**, and **`responses[].schema`** from `/v3/api-docs`.

## Path note

Several controllers omit a **leading slash** in `@RequestMapping` (e.g. `v1/api/students/`). External docs and gateways should normalize to **`/v1/api/...`**.

## Related preload families

Same **preload / status / clear** pattern exists for **subjects**, **schedules**, and **grades** under `v1/api/subjects`, `v1/api/schedules/`, `v1/api/grades/` — mirror this endpoint shape when documenting or generating OpenAPI.

