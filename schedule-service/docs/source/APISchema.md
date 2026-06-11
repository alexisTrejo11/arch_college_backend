---
type: "REST"
httpEndpoints:
  - id: "groups-create-obligatory"
    method: "POST"
    urlPath: "/v1/api/groups/obligatory"
    summary: "Create obligatory group"
    description: "Request body per OpenAPI; creates an obligatory offering."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-create-elective"
    method: "POST"
    urlPath: "/v1/api/groups/elective"
    summary: "Create elective group"
    description: "Request body per OpenAPI; creates an elective offering."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "finder-group-by-id"
    method: "GET"
    urlPath: "/v1/api/finder/groups/{groupId}"
    summary: "Get group by id"
    description: "Path param groupId."
    tags:
      - "schedule"
      - "finder"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "finder-group-by-key"
    method: "GET"
    urlPath: "/v1/api/finder/groups/key/{key}"
    summary: "Get group by natural key"
    description: "Domain key in path."
    tags:
      - "schedule"
      - "finder"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "finder-current"
    method: "GET"
    urlPath: "/v1/api/finder/groups/current"
    summary: "Current groups slice"
    description: "Operational query for current term/window."
    tags:
      - "schedule"
      - "finder"
    authenticated: false
    rateLimit: "Gateway default"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-update-schedule"
    method: "PUT"
    urlPath: "/v1/api/groups/update-schedule"
    summary: "Update meeting schedule"
    description: "Body defines new meeting pattern."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 400
        description: "Validation failure"
        example: {}
      - status: 401
        description: "Unauthorized"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-add-teacher"
    method: "PUT"
    urlPath: "/v1/api/groups/{key}/add-teacher/{teacherId}"
    summary: "Assign teacher to group"
    description: "Natural key and teacher id in path."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Group or teacher not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-remove-teacher"
    method: "PATCH"
    urlPath: "/v1/api/groups/{key}/remove-teacher/{teacherId}"
    summary: "Remove teacher from group"
    description: "Partial update / roster change."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-delete"
    method: "DELETE"
    urlPath: "/v1/api/groups/{key}"
    summary: "Delete group by key"
    description: "Removes group identified by domain key."
    tags:
      - "schedule"
      - "groups"
    authenticated: true
    rateLimit: "Operator tier strict"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 404
        description: "Not found"
        example: {}
      - status: 500
        description: "Server error"
        example: {}

  - id: "groups-capacity-increase"
    method: "PUT"
    urlPath: "/v1/api/groups/{groupId}/increase-spot"
    summary: "Increase available spots"
    description: "Capacity mutation by groupId."
    tags:
      - "schedule"
      - "capacity"
    authenticated: true
    rateLimit: "Operator tier"
    responses:
      - status: 200
        description: "Success"
        example: {}
      - status: 409
        description: "Conflict / invariant breach"
        example: {}
      - status: 500
        description: "Server error"
        example: {}
---

# API schema

Matches **`APISchema`** / **`ApiEndpoint`** in `docs-schema.ts`. Add **`parameters`** and **`requestBody`** from `/v3/api-docs`.

## Additional routes

Also implemented (not exhaustively listed above):

- `GET /v1/api/finder/groups/by-ids`, `GET .../by`, `GET .../current/by-teacher/{teacherId}`, `GET .../current/by-building/{buildingLetter}`
- `PUT /v1/api/groups/{groupId}/add_spots/{spotsToAdd}`, `PUT .../decrease-spot`

Mirror the same **`responses`** block pattern when extending this file.
