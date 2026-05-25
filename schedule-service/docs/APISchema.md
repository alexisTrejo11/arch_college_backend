# API schema

Structured counterpart: [obsidian/APISchema.md](obsidian/APISchema.md) (`APISchema` in [docs-schema.ts](../../docs-schema.ts)).

- **type:** REST  
- Full **`ApiParameter`**, **`requestBody`**, **`responses[].schema`:** from **`/v3/api-docs`**.  

---

## Group mutations (`/v1/api/groups`)

| Method | Path | Summary |
| --- | --- | --- |
| POST | `/v1/api/groups/obligatory` | Create obligatory group |
| POST | `/v1/api/groups/elective` | Create elective group |
| PUT | `/v1/api/groups/update-schedule` | Update meeting schedule |
| PUT | `/v1/api/groups/{key}/add-teacher/{teacherId}` | Assign teacher |
| PATCH | `/v1/api/groups/{key}/remove-teacher/{teacherId}` | Remove teacher |
| DELETE | `/v1/api/groups/{key}` | Delete group |
| PUT | `/v1/api/groups/{groupId}/add_spots/{spotsToAdd}` | Add capacity |
| PUT | `/v1/api/groups/{groupId}/decrease-spot` | Reduce capacity |
| PUT | `/v1/api/groups/{groupId}/increase-spot` | Increase capacity |

Typical status codes: **200**, **400**, **401**, **404**, **409** (conflict on capacity), **500**.

---

## Finder (`/v1/api/finder/groups`)

| Method | Path | Summary |
| --- | --- | --- |
| GET | `/v1/api/finder/groups/{groupId}` | By id |
| GET | `/v1/api/finder/groups/key/{key}` | By natural key |
| GET | `/v1/api/finder/groups/by-ids` | Batch ids (query per OpenAPI) |
| GET | `/v1/api/finder/groups/by` | Filtered query |
| GET | `/v1/api/finder/groups/current` | Current offerings |
| GET | `/v1/api/finder/groups/current/by-teacher/{teacherId}` | By teacher |
| GET | `/v1/api/finder/groups/current/by-building/{buildingLetter}` | By building |

---

## Security note

Mutations should require **operator** authentication in production; finder endpoints may be more open depending on policy — align with Spring Security config.
