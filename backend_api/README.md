# Notes Management API (Backend)

Ocean Professional style – modern, clean, compliant.

## Overview
Spring Boot 3 REST API providing CRUD operations for user notes with GxP-compliant scaffolding:
- Audit trail (CREATE/READ/UPDATE/DELETE) with before/after snapshots
- Validation and structured error handling
- Role-based authorization hooks (placeholders)
- OpenAPI documentation
- H2 in-memory DB for development and tests

## Build & Run
- Java 17+
- Gradle wrapper included

Run:
- ./gradlew bootRun

Docs:
- Swagger UI: /swagger-ui.html
- OpenAPI JSON: /api-docs

## Endpoints
- POST /api/v1/notes
- GET /api/v1/notes?page={p}&size={s}&sort={field}&direction={asc|desc}
- GET /api/v1/notes/{id}
- PUT /api/v1/notes/{id}
- DELETE /api/v1/notes/{id}

## Validation
- title: required, 1..200 chars
- content: optional, <= 5000 chars
- createdAt/updatedAt auto-managed

## Audit Trail
- Entity: `AuditLog`
- Logged fields: `userId`, `timestamp`, `action`, `noteId`, `beforeState`, `afterState`, `path`, `reason (optional)`
- Service: `AuditService` persists logs within the same transactions
- User attribution: placeholder `CurrentUser` -> "demo-user"
  - TODO: Replace with authenticated principal

## RBAC Hooks
- Placeholder config in `SecurityConfig` with `@EnableMethodSecurity`
- Ownership checks in `NoteService.enforceOwnershipOrAdmin`
- TODO: Integrate JWT/OAuth2 and replace placeholder roles

## Error Handling
- GlobalExceptionHandler returns `ApiError`:
  - code, message, details, timestamp, path
- Proper HTTP statuses for validation, authz, not found, and general errors

## Testing
- Unit tests:
  - `NoteServiceTest` (create, update, delete, list, not found)
  - `NoteControllerTest` (happy paths + validation error)
- Integration scaffolding:
  - `NotesIntegrationTest` end-to-end CRUD with MockMvc

## Traceability
Each public API/service function includes docstrings and comments referencing requirement IDs aligned with GxP standards.

## Config
- H2 in-memory DB for local/testing
- JPA: ddl-auto=update (dev only)

## Notes
- This is initial scaffolding; integrate real security and user context for production.
