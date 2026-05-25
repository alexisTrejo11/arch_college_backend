# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

- **type:** `REST`  

Full **`parameters`**, **`requestBody`**, and response **`schema`** objects: export from **`/v3/api-docs`** or Swagger UI at runtime.

---

## Representative endpoints

### Careers

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/careers/all` | List all careers |
| GET | `/v1/api/careers/{careerId}` | Get career by id |
| POST | `/v1/api/careers` | Create career |

**Typical responses:** 200 success, 400 validation (mutations), 401 when protected, 404 on missing id, 500 server error.

---

### Areas

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/areas/all` | List areas |
| GET | `/v1/api/areas/{areaId}/with-subjects` | Area with related subjects |

---

### Professional lines

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/professional_lines/all` | List professional lines |

---

### Subjects

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/subjects/obligatory/all` | List obligatory subjects |
| GET | `/v1/api/subjects/electives/all` | List elective subjects |

*(Additional routes exist for filters: by area, career, semester, names — see OpenAPI.)*

---

### Subject series

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/academic_curriculum/subject-series/all` | List subject series |
| POST | `/v1/api/academic_curriculum/subject-series` | Create subject series |

---

## Path consistency

The **`SubjectSeriesController`** class mapping is declared as `v1/api/academic_curriculum/subject-series` **without** a leading slash. Public documentation and gateways should treat canonical paths as **`/v1/api/...`**.

## Authenticated routes

Mutating catalog operations should be **authenticated** in production; exact security rules follow your Spring Security configuration.
