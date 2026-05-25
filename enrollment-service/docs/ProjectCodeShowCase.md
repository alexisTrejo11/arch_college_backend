# Code showcase

Structured counterpart: [obsidian/ProjectCodeShowCase.md](obsidian/ProjectCodeShowCase.md) (`ProjectCodeShowCase` in [docs-schema.ts](../../docs-schema.ts)).

## Example: Enrollment and preload

| **id** | `enrollment-service-example-1` |
| **title** | Enrollment and preload controllers |
| **category** | backend |

**Tags:** spring, rest, mongodb  

### EnrollmentCommandController.java

- **Path:** `enrollment-service/src/main/java/io/github/alexistrejo11/architecture/college/enrollment/controller/EnrollmentCommandController.java`  
- **Language:** Java  
- **Explanation:** Administrative `/v1/api/group-enrollments` mutations and lock-date.  

```java
// See repository: /v1/api/group-enrollments mutations and lock-date.
```

### StudentPreLoadController.java

- **Path:** `enrollment-service/src/main/java/io/github/alexistrejo11/architecture/college/enrollment/controller/preload/StudentPreLoadController.java`  
- **Explanation:** Bulk student preload under `v1/api/students/` (normalize to `/v1/api/...` externally).  

```java
// See repository: students preload, status, clear under v1/api/students/.
```

Replace stubs with real excerpts for portfolio embeds.
