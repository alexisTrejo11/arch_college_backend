# Code showcase

Structured counterpart: [obsidian/ProjectCodeShowCase.md](obsidian/ProjectCodeShowCase.md) (`ProjectCodeShowCase` / `CodeExample` / `CodeFile` in [docs-schema.ts](../../docs-schema.ts)).

## Example: Auth and user controllers

| Field | Value |
| --- | --- |
| **id** | `account-service-example-1` |
| **title** | Auth and user controllers |
| **description** | REST entry points for signup, login, and profile. |
| **category** | backend |
| **duration** | 5 min read |
| **views** | 0 |

**Tags:** `spring`, `rest`, `security`

### Files

#### AuthController.java

| Field | Value |
| --- | --- |
| **path** | `account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/AuthController.java` |
| **language** | java |
| **highlighted** | true |

**Explanation:** Handles authentication and registration flows.

```java
// See repository for full sources. Endpoints under /v1/api/auth (signup/*, login).
```

#### UserController.java

| Field | Value |
| --- | --- |
| **path** | `account-service/src/main/java/io/github/alexistrejo11/architecture/college/account/controller/UserController.java` |
| **language** | java |
| **highlighted** | false |

**Explanation:** Authenticated user read model exposure.

```java
// See repository for full sources. Profile endpoint /v1/api/user/my-profile.
```

## Note

Replace comment stubs with real excerpts when publishing to a portfolio site that embeds `CodeFile.content`.
