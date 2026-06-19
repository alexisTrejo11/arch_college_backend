# API Schema

**API type:** REST

## Academic-History

### `POST` /v1/api/academic-histories/student/{accountNumber}

**Load academic history (ADMIN)**

Despite POST, returns history for accountNumber; requires ADMIN. Class uses @RequestMapping without leading slash—normalize to this path at the edge.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict / admin |
| **Tags** | academic-history, admin |

#### Responses

- **200** — Success

```json
{}
```

- **400** — Invalid account number

```json
{}
```

- **403** — Forbidden

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

## Grade-Groups

### `GET` /v1/api/grades/groups/{groupId}

**Get grade group by id**

GroupDTO for a grading group.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | grade-groups |

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

### `GET` /v1/api/grades/groups/pending

**Pending grade groups**

Paginated pending groups.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | grade-groups |

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

## Grades

### `GET` /v1/api/grades/{gradeId}

**Get grade by id**

Returns GradeDTO or 404 via ResponseWrapper.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | grades |

#### Responses

- **200** — Success

```json
{}
```

- **404** — Grade not found

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

### `GET` /v1/api/grades/all

**Search grades with filters**

Query params: accountNumber, schoolPeriod, subjectId, subjectType, page, size, sort.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | grades |

#### Responses

- **200** — Paginated grades

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

### `GET` /v1/api/grades/pending-validation

**Pending validation grades**

Paginated list for authorizer workflows.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Gateway default |
| **Tags** | grades |

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

### `PUT` /v1/api/grades/{gradeId}/authorize

**Authorize grade**

Validates and authorizes grade by id.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | grades |

#### Responses

- **200** — Authorized

```json
{}
```

- **400** — Cannot authorize

```json
{}
```

- **404** — Grade not found

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

### `DELETE` /v1/api/grades/{gradeId}

**Soft delete grade**

Soft delete by id.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Strict / role-gated at gateway if needed |
| **Tags** | grades |

#### Responses

- **200** — Deleted

```json
{}
```

- **404** — Grade not found

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

## Students

### `GET` /v1/api/students/get-my-academic-history

**My academic history**

JWT derives student account number.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students, academic-history |

#### Responses

- **200** — Success

```json
{}
```

- **401** — Unauthorized

```json
{}
```

- **404** — History not found

```json
{}
```

- **500** — Server error

```json
{}
```

---

### `GET` /v1/api/students/get-my-annually-grades

**My annual grades**

Grades grouped by year for authenticated student.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students, grades |

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

### `GET` /v1/api/students/get-my-current-enrollments

**My current enrollment grades**

Current term enrollment-related grade rows.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | students, grades |

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

## Teachers

### `GET` /v1/api/teachers/grades/groups/pending-grade

**Teacher pending groups to grade**

JWT derives teacher account number.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | teachers, grades |

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

### `GET` /v1/api/teachers/grades/groups/my-history

**Teacher graded groups history**

JWT derives teacher account number.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | teachers, grades |

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

### `PUT` /v1/api/teachers/grades/groups/set-grades

**Assign grades to group**

Body: TeacherQualificationDTO; validates period and qualification.

| | |
|---|---|
| **Auth required** | Yes |
| **Rate limit** | Authenticated |
| **Tags** | teachers, grades |

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

Subset aligned with Java controllers; canonical contracts live in **`/v3/api-docs`** and Swagger UI.

## Path normalization

- Prefer documenting public routes as **`/v1/api/...`** even when `@RequestMapping` omits a leading **`/`**.

