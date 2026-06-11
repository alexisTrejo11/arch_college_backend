# Project Features

## Student, teacher, and admin registration

Dedicated signup endpoints per persona.

| Property | Value |
| --- | --- |
| ID | account-service-f1 |
| Category | authentication |
| Status | stable |
| Icon | user-plus |

### Highlights

- Separate routes for student, teacher, and admin signup

### Tech stack

- Spring Boot
- Spring Security

## JWT login

Issues JWT after successful authentication.

| Property | Value |
| --- | --- |
| ID | account-service-f2 |
| Category | security |
| Status | stable |
| Icon | key |

### Highlights

- POST /v1/api/auth/login

### Tech stack

- Spring Security
- JJWT or configured JWT library

## Authenticated profile

Current user profile for authenticated principal.

| Property | Value |
| --- | --- |
| ID | account-service-f3 |
| Category | api |
| Status | stable |
| Icon | user |

### Highlights

- GET /v1/api/user/my-profile

### Tech stack

- Spring Web
- Spring Security

## Service discovery registration

Registers with Eureka for use by gateways or other services.

| Property | Value |
| --- | --- |
| ID | account-service-f4 |
| Category | integration |
| Status | stable |
| Icon | radar |

### Highlights

- Eureka client enabled via Spring Cloud

### Tech stack

- Spring Cloud Netflix Eureka

## Additional notes

# Features

Each list item matches **`ProjectFeature`** in `docs-schema.ts` (`category` uses **`FeatureCategory`**, `status` uses **`FeatureStatus`**).

