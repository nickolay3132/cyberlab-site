# CyberLab Site

## Scenario Store Rest API Documentation

### GET `/api/store/scenario/by-title/{String}` - Get Scenario by Title
```http request
GET /api/store/scenario/by-title/{String}
X-Key: some_secret_key
```
**Response:**
```json
{
  "id": "Scenario UUID",
  "title": "Scenario Title",
  "description": "Scenario Description",
  "difficulty": "Scenario Difficulty",
  "isActive": "Is Scenario Active (Boolean)",
  "attackTypes": [
    {
      "label": "Scenario Attack Type Label",
      "description": "Scenario Attack Type Description"
    }
  ],
  "steps": [
    {
      "stepIndex": "Scenario Step Index (Int)",
      "instruction": "Scenario Step Instruction (String with HTML)"
    }
  ]
}
```

### POST `/api/store/scenario` - Scenario Creation
```http request
POST /api/store/scenario
X-Key: some_secret_key
Content-Type: application/json

{
  "title": "String",
  "description": "String",
  "difficulty": Enum<"EASY", "MEDIUM", "HARD">,
  "isActive": Boolean,
  "attackTypes": List<String>,
  "steps": [
    {
      "stepIndex": Int (Starts From 1),
      "instruction": "String",
      "expectedFlagHash": "String" (Will Be Hashed)
    }
  ]
}
```
**Response:**
```text
"String" (Scenario UUID)
```

### PUT `/api/store/scenario/{uuid}/step` - Update Or Add Scenario's Step
```http request
PUT /api/store/scenario/{uuid}/step
X-Key: some_secret_key
Content-Type: application/json

{
  "stepIndex": Int (Step Index to Update Or Add),
  "instruction": "String",
  "expectedFlagHash": "String"
}
```
**Response:**
```http request
200 OK
```

### PUT `/api/store/scenario/{uuid}/attack-type` - Add Attack Type
```http request
PUT /api/store/scenario/{uuid}/attack-type?label={String}
X-Key: some_secret_key
```
**Response:**
```http request
200 OK
```

### DELETE `/api/store/scenario/{uuid}` - Delete Specified Scenario And It's Steps
```http request
DELETE /api/store/scenario/{uuid}
X-Key: some_secret_key
```
**Response:**
```http request
204 No Content
```

### POST `/api/store/attack-type` - Add New Attack Type
```http request
POST /api/store/attack-type
X-Key: some_secret_key

{
  "label": "String",
  "description": "String"
}
```
**Response:**
```text
7 (ID of Created Attack Type)
```