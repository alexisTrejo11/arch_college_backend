# Code showcase

Structured counterpart: [obsidian/ProjectCodeShowCase.md](obsidian/ProjectCodeShowCase.md) (`ProjectCodeShowCase` in [docs-schema.ts](../../docs-schema.ts)).

## Example: Catalog controllers

| Field | Value |
| --- | --- |
| **id** | `curriculum-service-example-1` |
| **title** | Catalog controllers |
| **description** | REST boundaries for careers and obligatory subjects. |
| **category** | backend |
| **duration** | 5 min read |
| **views** | 0 |

**Tags:** spring, rest, jpa  

### CareerController.java

| Field | Value |
| --- | --- |
| **path** | `curriculum-service/src/main/java/io/github/alexistrejo11/architecture/college/curriculum/controller/CareerController.java` |
| **language** | java |
| **highlighted** | true |

**Explanation:** Career aggregate API (`/v1/api/careers`).

```java
// See repository: /v1/api/careers CRUD and lookups.
```

### ObligatorySubjectController.java

| Field | Value |
| --- | --- |
| **path** | `curriculum-service/src/main/java/io/github/alexistrejo11/architecture/college/curriculum/controller/ObligatorySubjectController.java` |
| **language** | java |
| **highlighted** | false |

**Explanation:** Obligatory subjects by area, semester, career.

```java
// See repository: obligatory subjects by area, semester, career.
```

Replace comment stubs with real excerpts when embedding in a portfolio renderer.
