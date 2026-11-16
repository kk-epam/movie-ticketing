# Movie Ticketing System (Spring Boot)

Browse movies by title, pick a showtime, select seats, and book tickets.

## Features
- Search movies by title (partial, case-insensitive)
- View showtimes per movie
- Interactive seat map with live availability
- Atomic seat booking with pessimistic locking, so two users cannot book the same seat
- Booking confirmation with a booking code
- Sample data seeded on startup (4 movies, 2 days of showtimes)

## Tech stack
Java 17, Spring Boot 3.3, Spring Web, Spring Data JPA, Bean Validation, H2 (in-memory), plain HTML/JS frontend.

## Run it
```bash
mvn spring-boot:run
```
Then open http://localhost:8080. Tests: `mvn test`. H2 console: http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:ticketing`, user `sa`, empty password).

## REST API

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/movies` | List all movies |
| GET | `/api/movies?title=star` | Search movies by title |
| GET | `/api/movies/{id}` | Movie details |
| GET | `/api/movies/{id}/shows` | Showtimes for a movie |
| GET | `/api/shows/{showId}/seats` | Seat map with booked flags |
| POST | `/api/bookings` | Book seats |
| GET | `/api/bookings/{code}` | Look up a booking |

Example booking request:
```json
{
  "showId": 1,
  "customerName": "Ada Lovelace",
  "customerEmail": "ada@example.com",
  "seatLabels": ["C4", "C5"]
}
```

Responses: `201 Created` on success, `409 Conflict` if a seat is already taken, `400 Bad Request` for invalid input, `404 Not Found` for unknown IDs.

## Project layout
```
src/main/java/com/example/ticketing/
  controller/   REST endpoints
  service/      Business logic (search, seat availability, booking)
  repository/   Spring Data JPA repositories (incl. the locking query)
  model/        JPA entities: Movie, Show, ShowSeat, Booking
  dto/          Request and response records
  config/       Sample data seeding
src/main/resources/static/index.html   Web UI
```

## Ideas for next steps
- Authentication (Spring Security) so bookings belong to logged-in users
- Payment integration (e.g. Stripe) before confirming a booking
- Seat holds with a timeout, so a seat is reserved while a user is paying
- Real database (PostgreSQL) with Flyway migrations instead of H2
- Admin endpoints to add movies, screens, and showtimes
