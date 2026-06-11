# API Schema

**API type:** REST

## Curriculum

### `GET` /v1/api/careers/all

**List all careers**

Returns catalog careers; see OpenAPI for response schema.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, careers |

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

### `GET` /v1/api/careers/{careerId}

**Get career by id**

Path param careerId.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, careers |

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

### `POST` /v1/api/careers

**Create career**

Request body per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated / admin |
| **Tags** | curriculum, careers |

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

### `GET` /v1/api/areas/{areaId}/with-subjects

**Area with subjects**

Aggregated read for an area.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, areas |

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

### `GET` /v1/api/professional_lines/all

**List professional lines**

All professional line entities.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum |

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

### `GET` /v1/api/subjects/obligatory/all

**List obligatory subjects**

Full obligatory catalog slice.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, subjects |

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

### `GET` /v1/api/subjects/electives/all

**List elective subjects**

Full elective catalog slice.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, subjects |

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

### `GET` /v1/api/academic_curriculum/subject-series/all

**List subject series**

Controller maps v1/api/... (leading slash recommended at gateway).

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | curriculum, series |

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

### `POST` /v1/api/academic_curriculum/subject-series

**Create subject series**

Body links obligatory/elective subjects per OpenAPI.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated / admin |
| **Tags** | curriculum, series |

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

## Additional notes

# API schema

Front matter matches **`APISchema`** and **`ApiEndpoint`** in `docs-schema.ts`. Expand with **`parameters`**, **`requestBody`**, and typed **`responses[].schema`** from `/v3/api-docs`.

## Path consistency

- **`SubjectSeriesController`** is declared as `v1/api/academic_curriculum/subject-series` without a leading slash; treat public paths as **`/v1/api/...`** after gateway normalization.

