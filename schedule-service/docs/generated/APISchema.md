# API Schema

**API type:** REST

## Schedule

### `POST` /v1/api/groups/obligatory

**Create obligatory group**

Request body per OpenAPI; creates an obligatory offering.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, groups |

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

### `POST` /v1/api/groups/elective

**Create elective group**

Request body per OpenAPI; creates an elective offering.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, groups |

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

### `GET` /v1/api/finder/groups/{groupId}

**Get group by id**

Path param groupId.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | schedule, finder |

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

### `GET` /v1/api/finder/groups/key/{key}

**Get group by natural key**

Domain key in path.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | schedule, finder |

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

### `GET` /v1/api/finder/groups/current

**Current groups slice**

Operational query for current term/window.

| | |
|---|---|
| **Auth required** | No |
| **Rate limit** | Gateway default |
| **Tags** | schedule, finder |

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

### `PUT` /v1/api/groups/update-schedule

**Update meeting schedule**

Body defines new meeting pattern.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, groups |

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

### `PUT` /v1/api/groups/{key}/add-teacher/{teacherId}

**Assign teacher to group**

Natural key and teacher id in path.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, groups |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Group or teacher not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `PATCH` /v1/api/groups/{key}/remove-teacher/{teacherId}

**Remove teacher from group**

Partial update / roster change.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, groups |

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

### `DELETE` /v1/api/groups/{key}

**Delete group by key**

Removes group identified by domain key.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier strict |
| **Tags** | schedule, groups |

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

### `PUT` /v1/api/groups/{groupId}/increase-spot

**Increase available spots**

Capacity mutation by groupId.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Operator tier |
| **Tags** | schedule, capacity |

#### Responses

- **200** — Success

```json
{}
```

- **409** — Conflict / invariant breach

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

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Add **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Additional routes

Also implemented (not exhaustively listed above):

- `GET /v1/api/finder/groups/by-ids`, `GET .../by`, `GET .../current/by-teacher/{teacherId}`, `GET .../current/by-building/{buildingLetter}`
- `PUT /v1/api/groups/{groupId}/add_spots/{spotsToAdd}`, `PUT .../decrease-spot`

Mirror the same **`responses`** block pattern when extending this file.

