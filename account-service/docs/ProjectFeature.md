# Features

Structured counterpart: [obsidian/ProjectFeature.md](obsidian/ProjectFeature.md) (`ProjectFeatures` / `ProjectFeature` in [docs-schema.ts](../../docs-schema.ts)).

Each row satisfies **`FeatureCategory`** and **`FeatureStatus`** enums.

## Feature list

### 1. Student, teacher, and admin registration

| Field | Value |
| --- | --- |
| **id** | `account-service-f1` |
| **category** | authentication |
| **status** | stable |
| **icon** | user-plus |
| **githubExampleUrl** | https://github.com/alexisTrejo11/architecture-college-plattform/tree/main/account-service |

**Description:** Dedicated signup endpoints per persona.

**Highlights**

- Separate routes for student, teacher, and admin signup  

**Tech stack**

- Spring Boot  
- Spring Security  

---

### 2. JWT login

| Field | Value |
| --- | --- |
| **id** | `account-service-f2` |
| **category** | security |
| **status** | stable |
| **icon** | key |

**Description:** Issues JWT after successful authentication.

**Highlights**

- `POST /v1/api/auth/login`  

**Tech stack**

- Spring Security  
- JWT library as configured in the module  

---

### 3. Authenticated profile

| Field | Value |
| --- | --- |
| **id** | `account-service-f3` |
| **category** | api |
| **status** | stable |
| **icon** | user |

**Description:** Current user profile for the authenticated principal.

**Highlights**

- `GET /v1/api/user/my-profile`  

**Tech stack**

- Spring Web  
- Spring Security  

---

### 4. Service discovery registration

| Field | Value |
| --- | --- |
| **id** | `account-service-f4` |
| **category** | integration |
| **status** | stable |
| **icon** | radar |

**Description:** Registers with Eureka for discovery.

**Highlights**

- Eureka client enabled via Spring Cloud  

**Tech stack**

- Spring Cloud Netflix Eureka  
