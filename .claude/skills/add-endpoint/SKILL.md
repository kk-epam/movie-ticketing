---
name: add-endpoint
description: Add a new REST endpoint following the project's controller/service/repository layering. Use when asked to add or extend an API.
---

Package root: `src/main/java/com/example/ticketing/`

1. Add or extend a repository in `repository/` if new data access is needed.
2. Put business logic in `service/` (see `BookingService`, `MovieService`).
3. Expose it from a controller in `controller/`; use request/response types from `dto/Dtos.java`.
4. Add a unit test in `src/test/java/com/example/ticketing/` (see `BookingServiceTest`).
5. Run the `run-tests` skill before finishing.
