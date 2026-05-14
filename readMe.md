## How to run
./mvnw spring-boot:run

## Base URL
http://localhost:8080/users

POST /users
{
  "email": "test@example.com",
  "name": "John Doe"
}

{
  "id": 0,
  "title": "Sample Note",
  "body": "This is a note",
  "priority": 1,
  "pinned": false,
  "archived": false,
  "trashed": false,
  "createdAt": "2026-04-21T12:00:00",
  "updatedAt": "2026-04-21T12:00:00"
}


POST /addUser
{
  "userEmail": "name@example.com",
  "notes": []
}
{
  "userEmail": "name@example.com",
  "notes": []
}

GET /1?userEmail=name@example.com
{
  "userEmail": "name@example.com",
  "notes": []
}

POST /users/notes/addNote?userEmail=name@example.com
{
  "title": "Buy milk",
  "body": "From store",
  "priority": 1
}
 Expected Response: {
  "id": 0,
  "title": "Buy milk",
  "body": "From store",
  "priority": 1,
  "pinned": false,
  "createdAt": "...",
  "updatedAt": "..."
}

DELETE /notes?userEmail=name@example.com
Response: Notes deleted Successfully

DELETE /{id}?userEmail=name@example.com
Response: User deleted Successfully


PUT /notes/updatePriority/0?userEmail=name@example.com&priority=2
{
  "fromIndex": 0,
  "toIndex": 2
}

PUT  /{email}/notes/{index}
{
  "title": "Updated",
  "body": "Updated body",
  "priority": 2
}

PATCH /{email}/notes/{index}/pin  //same for unpin, archive, unarchive, trash, restore

GET /notes/search?userEmail=john@example.com&q=milk

To vacate the port 8081, use the following command in PowerShell: Get-NetTCPConnection -LocalPort 8081 | Select-Object LocalAddress,LocalPort,State,OwningProcess
Then stop the process using the OwningProcess PID: Stop-Process -Id 31624 -Force