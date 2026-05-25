# Features

Structured counterpart: [obsidian/ProjectFeature.md](obsidian/ProjectFeature.md) (`ProjectFeatures` / `ProjectFeature` in [docs-schema.ts](../../docs-schema.ts)).

## 1. Group enrollment operations

| Field | Value |
| --- | --- |
| **id** | enrollment-service-f1 |
| **category** | api |
| **status** | stable |

Create/cancel enrollments, query by id or student, read **lock date**. Uses **Spring Web** + **JPA**.

---

## 2. Student self-service enrollment

| Field | Value |
| --- | --- |
| **id** | enrollment-service-f2 |
| **category** | api |
| **status** | stable |

**My enrollments**, student POST enroll, DELETE by group/subject keys. Uses **Spring Security** + **Web**.

---

## 3. Bulk preload pipelines

| Field | Value |
| --- | --- |
| **id** | enrollment-service-f3 |
| **category** | integration |
| **status** | stable |

Preload + status + clear for **students**, **subjects**, **schedules**, **grades**. Uses **Spring Data MongoDB**.

---

## 4. Polyglot persistence

| Field | Value |
| --- | --- |
| **id** | enrollment-service-f4 |
| **category** | database |
| **status** | stable |

**PostgreSQL** for enrollments; **MongoDB** for flexible preload documents.
