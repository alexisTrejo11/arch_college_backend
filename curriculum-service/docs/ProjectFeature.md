# Features

Structured counterpart: [obsidian/ProjectFeature.md](obsidian/ProjectFeature.md) (`ProjectFeatures` / `ProjectFeature` in [docs-schema.ts](../../docs-schema.ts)).

## 1. Careers, areas, and professional lines

| Field | Value |
| --- | --- |
| **id** | `curriculum-service-f1` |
| **category** | api |
| **status** | stable |
| **icon** | layers |

**Description:** CRUD and lookup APIs for the organizational structure of the catalog.

**Highlights**

- GET/POST/PUT for careers, areas, professional lines  

**Tech stack:** Spring Web, Spring Data JPA  

---

## 2. Obligatory and elective subjects

| Field | Value |
| --- | --- |
| **id** | `curriculum-service-f2` |
| **category** | database |
| **status** | stable |
| **icon** | book |

**Description:** Manage subject definitions with filters by area, career, semester.

**Highlights**

- `/v1/api/subjects/obligatory` and `/v1/api/subjects/electives`  

**Tech stack:** Spring Data JPA, PostgreSQL  

---

## 3. Subject series graph

| Field | Value |
| --- | --- |
| **id** | `curriculum-service-f3` |
| **category** | integration |
| **status** | stable |
| **icon** | share |

**Description:** Link obligatory and elective subjects through series.

**Highlights**

- Subject series CRUD and list endpoints  

**Tech stack:** Spring Web  

---

## 4. Read-optimized catalog queries

| Field | Value |
| --- | --- |
| **id** | `curriculum-service-f4` |
| **category** | performance |
| **status** | stable |
| **icon** | search |

**Description:** List and filter endpoints for admin and downstream consumers.

**Highlights**

- by-area, by-career, by-semester lookups  

**Tech stack:** Spring Data JPA  
