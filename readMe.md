## How to run
./mvnw spring-boot:run

## Base URL
<!-- http://localhost:8080 -->

POST /users/addUser

Request: {
  "userEmail": "john@example.com",
  "notes": []
}
Response:
{
  "email": "test@example.com",
  "name": "John Doe"
}
GET /users/{id} 
JSON Response:
{
  "id": 1,
  "email": "name@example.com"}

  POST /users/notes/addNote?userEmail=name@example.com
  Request: 
  {
  "title": "Buy milk",
  "body": "From store",
  "priority": 1
}
Response: 
{
  "id": 0,
  "title": "Buy milk",
  "body": "From store",
  "priority": 1,
  "pinned": false,
  "createdAt": "...",
  "updatedAt": "..."
}


DELETE /users/{id}?userEmail=name@example.com
Reponse: User deleted successfully

DELETE /users/notes/delete/{index}?userEmail=name@example.com
Response: Note deleted successfully

DELETE /users/notes?userEmail=name@example.com
Response: {
  "notes": []
}

PUT /users/notes/updatePriority/{index}?userEmail=name@example.com&priority=2

PUT /users/notes/reorder?userEmail=name@example.com
Request:
{
  "fromIndex": 0,
  "toIndex": 2
}

PUT /users/{email}/notes/{index}
Request:
{
  "title": "Updated",
  "body": "Updated body",
  "priority": 2
}

PATCH /users/{email}/notes/{index}/pin //same for unpin, archive, unarchive, trashed and restore notes

GET /users/notes/search?userEmail=name@example.com&q=milk
Response: List of matching notes