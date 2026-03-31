# Keep Notes – Your 2-Hour Daily Plan

You will work on this project for **2 hours per day**.
You will progress in this order (easy → difficult):
1. Backend completion (in-memory)
2. Database-backed backend
3. Frontend/UI

You will also learn Git gradually as you build.

---

## How to Structure Your 2 Hours (Every Day)

Use this same structure daily:
- **0:00–0:15**: Pull latest code, create/check your branch, define your goal for today.
- **0:15–1:20**: Build one focused feature only.
- **1:20–1:45**: Test your work. (Manual testing only for Days 1–15. Automated tests start Day 16.)
- **1:45–2:00**: Commit, push, and write a short end-of-day summary.

If you don’t finish in 2 hours, stop and continue tomorrow.

---

## Your Daily Exit Checklist

Before ending each day, confirm:
- [ ] You shipped one small feature
- [ ] You tested a happy path
- [ ] You tested at least one edge case
- [ ] You pushed a clean commit with a clear message
- [ ] You wrote a 3–5 line summary of what you completed

---

## Week 1 — Backend Basics (In-Memory)

### Day 1: Run Project + Git Setup
**Scope (2h max)**
- **What’s currently missing:** You may not yet know the project flow, endpoint behavior, or branch workflow.
- **What you need to do:** Run the app, call at least 3 existing endpoints, and write a short endpoint inventory.
- **Pointers:** Use Postman/Insomnia or curl. Create `feature/day-1-setup`. Keep one small commit for setup notes.

**Git Focus**
- `git status`, `git add`, `git commit`, `git switch -c`

**Done When**
- The app runs and you pushed one clean setup commit.

---

### Day 2: Fix `Note` Setter Bugs
**Scope (2h max)**
- **What’s currently wrong:** `Note.setBody()` is incorrect, so body updates silently fail.
- **What you need to do:** Fix the setter and verify body updates through the API.
- **Pointers:** Update model code first, then manually call create/update endpoints and verify response values.

**Done When**
- Note body updates correctly in manual testing.

---

### Day 3: Fix `User` Setter Bugs
**Scope (2h max)**
- **What’s currently wrong:** `User.setUserName()` and `User.setUserEmail()` don’t assign incoming values.
- **What you need to do:** Fix both setters and test one user update scenario.
- **Pointers:** Check one valid update case and one edge case (like blank input if validation exists).

**Done When**
- User fields persist correctly after update flow.

---

### Day 4: Basic Input Validation
**Scope (2h max)**
- **What’s currently missing:** Some invalid input is accepted and causes unpredictable behavior.
- **What you need to do:** Validate required fields (`userEmail`, note `title`, note `body`) and return HTTP 400 with clear messages.
- **Pointers:** Add checks at controller entry points first. Keep error messages specific and readable.

**Done When**
- Invalid payloads fail with clear errors.

---

### Day 5: Priority Validation and Edge Cases
**Scope (2h max)**
- **What’s currently risky:** Out-of-range priority/index values can break operations.
- **What you need to do:** Enforce `priority >= 1` and validate index bounds for delete/update/reorder endpoints.
- **Pointers:** Add guard clauses early in endpoints and keep status/message patterns consistent.

**Done When**
- Invalid priority/index inputs are handled safely.

---

## Week 2 — Backend API Cleanup

### Day 6: Standardize User Routes
**Scope (2h max)**
- **What’s currently wrong:** User routes are inconsistent (`/addUser`, `/getUser`, etc.).
- **What you need to do:** Introduce REST-style routes around `/users`.
- **Pointers:** You can keep old routes temporarily, but document which routes are the official ones.

**Done When**
- User create/get/delete routes follow one consistent style.

- Standardizes user routes to RESTful style under `/users` for better consistency and clarity.
---

### Day 7: Standardize Note Routes
**Scope (2h max)**
- **What’s currently wrong:** Note routes are not clearly grouped by owner.
- **What you need to do:** Move note routes under `/users/{email}/notes`.
- **Pointers:** Update route documentation right after changes to avoid drift.

**Done When**
- Note APIs are clearly grouped under each user.

---

### Day 8: Add Update Note Endpoint
**Scope (2h max)**
- **What’s currently missing:** Note CRUD is incomplete without a clear update endpoint.
- **What you need to do:** Add endpoint to edit `title`, `body`, and `priority`.
- **Pointers:** Reuse existing validation logic; avoid duplicate validation code.

**Done When**
- You can update note content and priority in one API call.

---

### Day 9: Add `isPinned`
**Scope (2h max)**
- **What’s currently missing:** There is no “pin important note” feature.
- **What you need to do:** Add `isPinned` and a pin/unpin action.
- **Pointers:** Use default `isPinned=false` and return this field in all note responses.

**Done When**
- Pin state can be toggled and is visible in responses.

---

### Day 10: Add `createdAt` and `updatedAt`
**Scope (2h max)**
- **What’s currently missing:** You can’t tell when notes were created or modified.
- **What you need to do:** Add timestamps and update `updatedAt` during edits.
- **Pointers:** Set both timestamps at create time; only update `updatedAt` on edits.

**Done When**
- Timestamps are present and behave correctly.

---

## Week 3 — Feature Completeness (In-Memory)

### Day 11: Archive Notes
**Scope (2h max)**
- **What’s currently missing:** There is no middle state between active and deleted.
- **What you need to do:** Add `isArchived` and archive/unarchive endpoint.
- **Pointers:** Decide whether archived notes appear in default list, and document that behavior.

**Done When**
- Archived state can be toggled successfully.

---

### Day 12: Trash (Soft Delete)
**Scope (2h max)**
- **What’s currently risky:** Delete removes data immediately.
- **What you need to do:** Convert delete to soft-delete (`isTrashed` or `deletedAt`).
- **Pointers:** Keep note data; only change state when deleting.

**Done When**
- Delete no longer removes notes permanently.

---

### Day 13: Restore + Permanent Delete
**Scope (2h max)**
- **What’s currently incomplete:** Soft delete is not useful without restore/hard-delete.
- **What you need to do:** Add restore endpoint and permanent delete endpoint.
- **Pointers:** Restrict hard delete to trashed notes (or clearly document your rule).

**Done When**
- Trash lifecycle (trash → restore/hard-delete) works.

---

### Day 14: Search Endpoint (Keyword)
**Scope (2h max)**
- **What’s currently missing:** Notes can’t be searched by text.
- **What you need to do:** Add query `q` to match title/body text.
- **Pointers:** Start with case-insensitive substring matching; keep it simple for now.

**Done When**
- Search returns only matching notes.

---

### Day 15: Filter + Sort Endpoint
**Scope (2h max)**
- **What’s currently missing:** Users can’t focus by note state or control ordering.
- **What you need to do:** Add filters (`pinned`, `archived`, `trashed`) and one sort (`priority` or `updatedAt`).
- **Pointers:** Keep query params optional and composable. Document default behavior.

**Done When**
- Filtered/sorted lists work and are manually verified.

---

## Week 4 — Tests, Errors, and Git Collaboration

### Day 16: First Controller Tests
**Scope (2h max)**
- **What’s currently missing:** No regression safety for core features.
- **What you need to do:** Add happy-path controller tests for user create/get and note create/update.
- **Pointers:** Start small (2–4 tests), keep test data minimal.

**Done When**
- Happy-path tests pass reliably.

---

### Day 17: Failure-Path Tests
**Scope (2h max)**
- **What’s currently missing:** Error flows are unprotected.
- **What you need to do:** Add tests for invalid payloads, invalid indexes, and not-found cases.
- **Pointers:** Assert both HTTP status and important error message fields.

**Done When**
- At least 4 failure scenarios are covered.

---

### Day 18: Global Error Response Format
**Scope (2h max)**
- **What’s currently wrong:** Different endpoints may return different error structures.
- **What you need to do:** Add `@ControllerAdvice` and standard JSON error response shape.
- **Pointers:** Include timestamp, status, message, and path where possible.

**Done When**
- Errors are consistent across endpoints.

---

### Day 19: API Docs (Minimal)
**Scope (2h max)**
- **What’s currently missing:** New developers can’t quickly understand your API.
- **What you need to do:** Document endpoints and add one request/response example per key route.
- **Pointers:** Prioritize user + note lifecycle endpoints first.

**Done When**
- Another student can call APIs from docs only.

---

### Day 20: Git Intermediate Practice
**Scope (2h max)**
- **What’s currently missing:** You may know commits but not conflict resolution.
- **What you need to do:** Create an intentional merge conflict and resolve it cleanly.
- **Pointers:** Start with a text-file conflict, then a Java-file conflict.

**Git Focus**
- `git diff`, `git stash`, merge conflict workflow

**Done When**
- You can explain what conflicted and how you resolved it.

---

## Week 5 — Database Migration (Backend Persistence)

### Day 21: Add JPA + DB Config
**Scope (2h max)**
- **What’s currently wrong:** Data disappears on restart because storage is in-memory.
- **What you need to do:** Add JPA dependency and local DB profile (H2 or MySQL).
- **Pointers:** Keep one local profile and verify boot + DB connection.

**Done When**
- App starts with working DB connection.

---

### Day 22: Create `UserEntity`
**Scope (2h max)**
- **What’s currently missing:** No persistent user table/model.
- **What you need to do:** Add `UserEntity` and repository, then prove basic CRUD.
- **Pointers:** Start minimal and add uniqueness constraints for email if possible.

**Done When**
- User repository CRUD works.

---

### Day 23: Create `NoteEntity`
**Scope (2h max)**
- **What’s currently missing:** Notes are not mapped to relational storage.
- **What you need to do:** Add `NoteEntity`, repository, and relationship to user.
- **Pointers:** Make ownership explicit (one user has many notes).

**Done When**
- User-note relationship persists correctly.

---

### Day 24: Move One API Flow to DB
**Scope (2h max)**
- **What’s currently wrong:** API logic still depends on in-memory map.
- **What you need to do:** Refactor one flow (like create/get notes) to repository/service.
- **Pointers:** Keep scope tight; verify behavior stays the same as before.

**Done When**
- One selected flow is DB-backed and tested.

---

### Day 25: Complete DB Refactor + Git Advanced
**Scope (2h max)**
- **What’s currently incomplete:** Some routes may still be in-memory; commit history may be messy.
- **What you need to do:** Move remaining key note lifecycle routes to DB and clean commit history.
- **Pointers:** Prioritize correctness first, then commit clarity.

**Git Focus**
- clean commits, squash merge concept, commit message quality

**Done When**
- Core note lifecycle runs without the in-memory map.

---

## Week 6 — UI Implementation (Feature-Complete Product)

### Day 26: UI Project Setup
**Scope (2h max)**
- **What’s currently missing:** You only have backend; there is no user-facing interface.
- **What you need to do:** Scaffold frontend and configure API client/base URL.
- **Pointers:** Keep a simple structure (`pages`, `components`, `services`).

**Done When**
- UI runs and can call a test/health endpoint.

---

### Day 27: Notes List View
**Scope (2h max)**
- **What’s currently missing:** Users cannot view notes in the UI.
- **What you need to do:** Build list screen and render notes.
- **Pointers:** Show title, priority, timestamps, and an empty-state message.

**Done When**
- Notes are fetched and displayed.

---

### Day 28: Create/Edit Note Form
**Scope (2h max)**
- **What’s currently missing:** Create/edit workflows are not available in UI.
- **What you need to do:** Add create/edit form and connect to backend.
- **Pointers:** Reuse one form component for both modes.

**Done When**
- You can create and edit notes from UI.

---

### Day 29: Note Actions UI
**Scope (2h max)**
- **What’s currently missing:** Pin/archive/trash/restore may exist in API but not UI.
- **What you need to do:** Add action buttons and refresh behavior.
- **Pointers:** Show clear success/error feedback after each action.

**Done When**
- Full note lifecycle is usable in UI.

---

### Day 30: Search/Filter UI + Final Demo
**Scope (2h max)**
- **What’s currently missing:** Final discoverability and demo polish.
- **What you need to do:** Add search and one filter, run final demo, and update README.
- **Pointers:** In your demo, show full flow: create → edit → pin/archive/trash/restore → search.

**Done When**
- Project is portfolio-ready and demonstrates backend + DB + UI flow.


