# KeepNotes Demo

KeepNotes is a small Spring Boot backend for practicing REST APIs, validation, tests, Git workflow, and the first steps of moving an in-memory app toward database persistence.

The app currently lets you create users, add notes for a user, update notes, pin/unpin notes, search notes, filter notes, reorder notes, and delete users or notes. It also has a health endpoint so you can quickly check that the server is running.

## Current Project Status

This project is in the middle of the backend-to-database migration phase.

What is already done:

- Basic Spring Boot REST API is working.
- Users and notes can be managed through API endpoints.
- Required fields are validated with clear error messages.
- Notes support priority, pin state, archive/trash fields, and timestamps.
- A global error response format exists with timestamp, status, message, and path.
- JPA and H2 are configured.
- `UserEntity` exists with a unique email field.
- `Note` is now marked as a JPA entity.
- `UserRepository` and `NoteRepository` exist.
- The user-note database relationship has been added and tested.

Important note: the main controller still stores API data in an in-memory `HashMap`. That means the API behavior works for practice, but the main routes are not fully database-backed yet. The database work is currently proven through repository/entity tests. The next major step is moving API flows from the in-memory map into repository/service logic.

## Tech Stack

- Java 17
- Spring Boot
- Spring Web
- Spring Security
- Spring Data JPA
- H2 Database
- Maven
- JUnit / Spring Boot Test

## How To Run The App

From the project folder:

```powershell
.\mvnw.cmd spring-boot:run
```

The app runs on:

```text
http://localhost:8081
```

To confirm the app is running, open this URL:

```text
http://localhost:8081/
```

Expected response:

```text
KeepNotes is running
```

## Run With The Local H2 File Database

The local profile uses a file-based H2 database, so data can survive a restart:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=local"
```

H2 console:

```text
http://localhost:8081/h2-console
```

Use this JDBC URL for the local profile:

```text
jdbc:h2:file:./data/testdb
```

Default username:

```text
sa
```

Password is blank.

## How To Run Tests

```powershell
.\mvnw.cmd test
```

The tests currently cover controller happy paths, failure paths, global error shape, user repository CRUD, and the user-note repository relationship.

## API Base URL

Most API routes are under:

```text
http://localhost:8081/users
```

## Main Endpoints

### Health Check

```http
GET /
```

Response:

```text
KeepNotes is running
```

### Create User

```http
POST /users/addUser
```

Request body:

```json
{
  "userName": "Vaishnavi",
  "userEmail": "vaishnavi@example.com",
  "notes": []
}
```

### Get User

```http
GET /users/1?userEmail=vaishnavi@example.com
```

The `{id}` value is currently part of the route, but the lookup is done by `userEmail`.

### Delete User

```http
DELETE /users/1?userEmail=vaishnavi@example.com
```

### Add Note To User

```http
POST /users/notes/addNote?userEmail=vaishnavi@example.com
```

Request body:

```json
{
  "title": "Buy milk",
  "body": "From store",
  "priority": 1
}
```

Expected response includes the note fields plus timestamps:

```json
{
  "id": 0,
  "title": "Buy milk",
  "body": "From store",
  "priority": 1,
  "pinned": false,
  "archived": false,
  "trashed": false,
  "createdAt": "2026-04-21T12:00:00",
  "updatedAt": "2026-04-21T12:00:00"
}
```

### Update A Note

```http
PUT /users/{email}/notes/{index}
```

Example:

```http
PUT /users/vaishnavi@example.com/notes/0
```

Request body:

```json
{
  "title": "Updated title",
  "body": "Updated body",
  "priority": 2
}
```

### Update Note Priority

```http
PUT /users/notes/updatePriority/{index}?userEmail=vaishnavi@example.com&priority=2
```

### Reorder Notes

```http
PUT /users/notes/reorder?userEmail=vaishnavi@example.com
```

Request body:

```json
{
  "fromIndex": 0,
  "toIndex": 1
}
```

### Pin A Note

```http
PATCH /users/{email}/notes/{index}/pin
```

### Unpin A Note

```http
PATCH /users/{email}/notes/{index}/unpin
```

### Search Notes

```http
GET /users/notes/search?userEmail=vaishnavi@example.com&q=milk
```

Search checks the note title and body using simple case-insensitive matching.

### Filter Notes

```http
GET /users/notes?userEmail=vaishnavi@example.com
```

Optional query params:

- `pinned=true`
- `archived=true`
- `trashed=true`
- `sort=updatedAt`

Example:

```http
GET /users/notes?userEmail=vaishnavi@example.com&pinned=true&sort=updatedAt
```

### Delete One Note

```http
DELETE /users/notes/delete/{index}?userEmail=vaishnavi@example.com
```

### Delete All Notes For A User

```http
DELETE /users/notes?userEmail=vaishnavi@example.com
```

## Validation Rules

The API currently validates these common cases:

- `userEmail` is required when creating or finding a user.
- Duplicate users are rejected.
- Note `title` is required.
- Note `body` is required.
- Note `priority` must be `1` or greater.
- Duplicate note priority for the same user is rejected.
- Invalid note indexes are rejected.
- Missing users return a clear error.

## Error Response Format

Errors are returned in a standard shape:

```json
{
  "timestamp": "2026-06-05T12:00:00",
  "status": 400,
  "message": "userEmail is required",
  "path": "/users/addUser"
}
```

## Database Work So Far

The database layer has started, but it is not fully connected to every controller endpoint yet.

Current database pieces:

- `UserEntity` represents a user table.
- `Note` is marked as an entity.
- `UserRepository` supports user database operations.
- `NoteRepository` supports note database operations.
- A user can have many notes.
- A note belongs to one user.
- Repository tests prove basic CRUD and the user-note relationship.

Next database step:

Move one real API flow, such as create/get notes, from the in-memory `HashMap` into repository-backed code.

## If Port 8081 Is Already Busy

Find the process using the port:

```powershell
Get-NetTCPConnection -LocalPort 8081 | Select-Object LocalAddress, LocalPort, State, OwningProcess
```

Then stop it using the process id:

```powershell
Stop-Process -Id <PID> -Force
```

## Git Workflow Reminder

The daily branch workflow is:

```powershell
git switch master
git pull origin master
git switch -c day24
```

Replace `day24` with the day you are starting.

At the end of the day:

```powershell
git status
git add .
git commit -m "Describe today's work"
git push -u origin day24
```

Then open a pull request from that day branch into `master`.