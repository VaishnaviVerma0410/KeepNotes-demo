## Day 1 – Endpoint Inventory

### GET /notes
- Description: Fetch notes for a given user
- Params: userEmail (query param)
- Response: List of Note objects

### PUT /notes/reorder
- Description: Reorders notes for a user
- Params: userEmail (query param)
- Body:
  - fromIndex (int)
  - toIndex (int)
- Response: Updated User object

### GET /
- Description: Basic endpoint to verify app is running
