# Features

Structured counterpart: [obsidian/ProjectFeature.md](obsidian/ProjectFeature.md) (`ProjectFeatures` in [docs-schema.ts](../../docs-schema.ts)).

## 1. Group creation

| **id** | schedule-service-f1 |
| **category** | api |
| **status** | stable |

**Obligatory / elective** group creation via `POST /v1/api/groups/obligatory` and `/elective`.

---

## 2. Finder and current-term queries

| **id** | schedule-service-f2 |
| **category** | performance |
| **status** | stable |

Read-heavy **`/v1/api/finder/groups`** surface (by id, key, filters, teacher, building, “current”).

---

## 3. Roster and capacity mutations

| **id** | schedule-service-f3 |
| **category** | api |
| **status** | stable |

Update schedule, add/remove teachers, adjust spots (`PUT`/`PATCH`/`DELETE` under `/v1/api/groups`).

---

## 4. Asynchronous integration

| **id** | schedule-service-f4 |
| **category** | messaging |
| **status** | stable |

**Spring AMQP** + **RabbitMQ** for change notifications to peers.
