# Movie Ticket Booking System — Project Documentation

---

## 1. Project Overview

**MovieBook** is a full-stack web application that allows users to browse movies, view details, book tickets, and track their booking history. The system is built using Angular for the frontend, Spring Boot for the backend, and PostgreSQL as the database.

---

## 2. Technology Stack

### Frontend — Angular 21

| Technology | Version | Purpose |
|---|---|---|
| Angular | 21.2.x | Core frontend framework (SPA) |
| TypeScript | 5.9.x | Strongly typed JavaScript superset |
| Angular Router | 21.2.x | Client-side navigation between pages |
| Angular Forms (FormsModule) | 21.2.x | Two-way data binding for forms |
| Angular HttpClient | 21.2.x | HTTP calls to the Spring Boot REST API |
| Angular SSR | 21.2.x | Server-Side Rendering support |
| RxJS | 7.8.x | Reactive programming, handling async HTTP streams |
| Angular Signals | Built-in | Reactive state management for auth status |

### Backend — Spring Boot 3.2

| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 3.2.0 | Core backend framework |
| Spring Web (spring-boot-starter-web) | 3.2.0 | REST API endpoints, HTTP request handling |
| Spring Data JPA (spring-boot-starter-data-jpa) | 3.2.0 | ORM layer — maps Java classes to DB tables |
| Hibernate | 6.3.x | JPA implementation — generates SQL queries |
| PostgreSQL Driver | 17.x | JDBC driver to connect Spring Boot to PostgreSQL |
| Lombok | 1.18.30 | Reduces boilerplate code (annotation processor) |
| Jackson | Built-in | JSON serialization/deserialization |
| Maven | 3.x | Build tool and dependency management |

### Database — PostgreSQL 17

| Aspect | Detail |
|---|---|
| Database | PostgreSQL 17 |
| Database Name | ticket_db |
| Port | 5432 |
| ORM | Hibernate (via Spring Data JPA) |
| Table creation | Automatic via `ddl-auto=update` |

---

## 3. Project Structure

```
ticket_booking_system/
│
├── movie-booking/                  ← Angular Frontend
│   └── src/app/
│       ├── pages/                  ← All page components
│       │   ├── login/
│       │   ├── signup/
│       │   ├── movies/
│       │   ├── movie-detail/
│       │   ├── booking/
│       │   ├── booking-confirmation/
│       │   └── my-bookings/
│       ├── services/               ← HTTP service layer
│       │   ├── auth.ts
│       │   ├── movie.ts
│       │   └── booking.ts
│       ├── models/                 ← TypeScript interfaces
│       │   ├── movie.model.ts
│       │   └── booking.model.ts
│       ├── guards/                 ← Route protection
│       │   └── auth.guard.ts
│       ├── shared/navbar/          ← Shared navbar component
│       ├── app.routes.ts           ← Route definitions
│       ├── app.config.ts           ← App providers config
│       └── app.ts                  ← Root component
│
└── movie-booking-backend/          ← Spring Boot Backend
    └── src/main/java/com/moviebooking/
        ├── controller/             ← REST API endpoints
        │   ├── MovieController.java
        │   ├── BookingController.java
        │   └── UserController.java
        ├── service/                ← Business logic layer
        │   ├── MovieService.java
        │   ├── BookingService.java
        │   └── UserService.java
        ├── repository/             ← Database access layer
        │   ├── MovieRepository.java
        │   ├── BookingRepository.java
        │   └── UserRepository.java
        ├── model/                  ← JPA Entity classes (DB tables)
        │   ├── Movie.java
        │   ├── Booking.java
        │   └── User.java
        ├── dto/                    ← Data Transfer Objects
        │   ├── AuthRequest.java
        │   └── AuthResponse.java
        ├── config/
        │   └── CorsConfig.java     ← CORS configuration
        ├── exception/
        │   └── ResourceNotFoundException.java
        ├── seeder/
        │   └── DataSeeder.java     ← Auto-seeds movie data
        └── MovieBookingApplication.java ← Entry point
```

---

## 4. Database Schema

### Table: movies
| Column | Type | Description |
|---|---|---|
| id | BIGINT (PK, Auto) | Unique movie identifier |
| name | VARCHAR | Movie title |
| price | DOUBLE | Ticket price per seat |
| language | VARCHAR | Movie language |
| genre | VARCHAR | Movie genre |
| release_date | VARCHAR | Release date string |

### Table: bookings
| Column | Type | Description |
|---|---|---|
| id | BIGINT (PK, Auto) | Unique booking identifier |
| movie_id | BIGINT | Foreign reference to movie |
| movie_name | VARCHAR | Movie name (denormalized for display) |
| customer_name | VARCHAR | Customer full name |
| customer_email | VARCHAR | Customer email (used to fetch bookings) |
| customer_phone | VARCHAR | Customer phone number |
| number_of_seats | INT | Number of seats booked |
| total_price | DOUBLE | Total amount paid |
| booking_date | DATE | Selected booking date |

### Table: users
| Column | Type | Description |
|---|---|---|
| id | BIGINT (PK, Auto) | Unique user identifier |
| name | VARCHAR | User full name |
| email | VARCHAR (Unique) | User email (login identifier) |
| password | VARCHAR | User password |

---

## 5. Backend REST API Endpoints

### Auth API — `/api/auth`
| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| POST | `/api/auth/signup` | Register new user | `{ name, email, password }` |
| POST | `/api/auth/login` | Login user | `{ email, password }` |

### Movies API — `/api/movies`
| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/movies` | Get all movies |
| GET | `/api/movies/{id}` | Get single movie by ID |

### Bookings API — `/api/bookings`
| Method | Endpoint | Description | Request Body |
|---|---|---|---|
| POST | `/api/bookings` | Create new booking | Booking object |
| GET | `/api/bookings?email=xyz` | Get bookings by customer email | Query param |

---

## 6. Angular Components — What Each Does

### `LoginComponent` (`/login`)
- Displays a split-screen login page with MovieBook branding on the left
- Accepts email and password input
- Calls `AuthService.login()` which posts to `POST /api/auth/login`
- On success: stores session in `localStorage` and navigates to `/movies`
- On failure: shows error message from the backend response

### `SignupComponent` (`/signup`)
- Same split-screen layout as login
- Accepts name, email, password, confirm password
- Validates that passwords match before calling `AuthService.signup()`
- Calls `POST /api/auth/signup` — prevents duplicate email registration
- On success: redirects to `/login` with a success toast message

### `NavbarComponent` (shared, always visible)
- Sticky top navigation bar across all pages
- Uses Angular **Signals** (`computed()`) to reactively show/hide Login, Sign Up buttons or user name and Logout button based on auth state
- Shows "My Bookings" link only when user is logged in
- `routerLinkActive` highlights the current active page link

### `MoviesComponent` (`/movies`) — Protected
- Loads all movies from `GET /api/movies` on `ngOnInit`
- Displays each movie as a glassmorphism card showing only the movie name
- Clicking "View Details →" passes the full movie object via **Router state** to avoid redundant API calls

### `MovieDetailComponent` (`/movies/:id`) — Protected
- Reads movie from `history.state` if the state movie's id matches the URL id
- Falls back to `GET /api/movies/{id}` if state is stale or URL was opened directly
- Shows full movie info: genre badge, title, language, genre, release date, price
- "Book Tickets Now" button navigates to `/booking/:id`

### `BookingComponent` (`/booking/:id`) — Protected
- Reads movie from `history.state` only if `state.movie.id === URL id` (prevents stale state bug)
- Pre-fills customer email from `localStorage`
- Booking date picker with today as the minimum selectable date
- Seat stepper (+/- buttons) for selecting number of seats
- Live total price calculation: `price × seats`
- Submits to `POST /api/bookings`
- On success: navigates to `/booking-confirmation` passing booking via router state

### `BookingConfirmationComponent` (`/booking-confirmation`) — Protected
- Reads booking from `history.state.booking` (passed from BookingComponent)
- If no state exists (user landed directly): redirects to `/movies`
- Displays full booking summary: movie name, customer details, seats, total paid, booking date
- "Browse More Movies" button returns to movies list

### `MyBookingsComponent` (`/my-bookings`) — Protected
- Reads logged-in user email from `AuthService.getEmail()`
- Calls `GET /api/bookings?email=` to fetch all bookings for that user
- Automatically tags each booking as **🟢 Upcoming** or **🔵 Completed** based on booking date vs today
- Shows empty state with "Browse Movies" button if no bookings found
- Shows detailed error with steps if the server is unreachable

---

## 7. Angular Services — What Each Does

### `AuthService` (`services/auth.ts`)
- Central authentication service used across the entire app
- Calls `POST /api/auth/signup` and `POST /api/auth/login`
- Stores login session in `localStorage` (loggedIn, userEmail, userName)
- Exposes `isLoggedIn` and `userName` as Angular **Signals** so any component reactively updates when auth state changes
- `setSession()` — stores response data after successful login
- `logout()` — clears localStorage and resets signals

### `MovieService` (`services/movie.ts`)
- Calls `GET /api/movies` — returns list of all movies
- Calls `GET /api/movies/{id}` — returns single movie

### `BookingService` (`services/booking.ts`)
- Calls `POST /api/bookings` — creates a new booking
- Calls `GET /api/bookings?email=` — fetches bookings by customer email

---

## 8. Angular Guard

### `authGuard` (`guards/auth.guard.ts`)
- A **functional route guard** using Angular's `CanActivateFn`
- Applied to all routes except `/login` and `/signup`
- Checks `AuthService.isLoggedIn()` signal
- If not logged in: redirects to `/login` and blocks the route
- Prevents users from accessing `/movies`, `/booking`, `/my-bookings` without authentication

---

## 9. Spring Boot Architecture — Layer by Layer

```
HTTP Request from Angular
        ↓
   Controller Layer        ← Receives HTTP request, validates input, returns response
        ↓
   Service Layer           ← Contains all business logic
        ↓
   Repository Layer        ← Interfaces that talk to the database via JPA
        ↓
   Model/Entity Layer      ← Java classes mapped to PostgreSQL tables
        ↓
   PostgreSQL Database
```

### Controller Layer
- `MovieController` — handles movie-related HTTP requests
- `BookingController` — handles booking creation and retrieval
- `UserController` — handles user signup and login

### Service Layer
- `MovieService` — fetches movies, throws `ResourceNotFoundException` (HTTP 404) if not found
- `BookingService` — calculates total price, sets movie name, saves booking with `@Transactional`
- `UserService` — checks for duplicate email on signup, validates credentials on login

### Repository Layer
- Extends `JpaRepository<Entity, Long>` — Spring Data auto-generates all SQL
- `BookingRepository` — custom JPQL query for case-insensitive email lookup
- `UserRepository` — `findByEmailIgnoreCase`, `existsByEmailIgnoreCase`

### Model Layer (JPA Entities)
- `Movie` — `@Entity` mapped to `movies` table
- `Booking` — `@Entity` mapped to `bookings` table, `@JsonFormat` for date serialization
- `User` — `@Entity` mapped to `users` table, email has `@Column(unique=true)`

### Config
- `CorsConfig` — allows `http://localhost:*` (any localhost port) for all `/api/**` routes, all HTTP methods including OPTIONS preflight

---

## 10. Complete User Flow

```
1. User opens app
        ↓
2. Redirected to /login (authGuard blocks all other routes)
        ↓
3. New user? → Click "Sign Up" → /signup
   Fills name, email, password → POST /api/auth/signup
   Backend checks duplicate email → saves to users table
   Redirected back to /login with success message
        ↓
4. User logs in → POST /api/auth/login
   Backend validates credentials → returns name + email
   Frontend stores session in localStorage
   AuthService signals update → Navbar shows user name + My Bookings
        ↓
5. Navigates to /movies (authGuard passes)
   GET /api/movies → displays movie cards (name only)
        ↓
6. Clicks "View Details" on a movie
   Movie object passed via Router state → /movies/:id
   MovieDetailComponent shows full details (genre, language, date, price)
        ↓
7. Clicks "Book Tickets Now"
   Navigates to /booking/:id with movie in router state
   Form pre-fills email from localStorage
   User fills name, phone, selects date, adjusts seats
   Live total = price × seats
   Clicks "Confirm Booking" → POST /api/bookings
   Backend: looks up movie by movieId, sets movieName + totalPrice + date
   Saves to bookings table
        ↓
8. Redirected to /booking-confirmation
   Displays booking ID, all details, total paid
        ↓
9. User clicks "My Bookings" in navbar
   GET /api/bookings?email=user@email.com
   Shows all bookings tagged as Upcoming or Completed
```

---

## 11. How to Run the Project

### Prerequisites
- Node.js 20+, npm 11+
- Java 17+, Maven 3.x
- PostgreSQL 17 running locally

### Step 1 — Setup Database
```sql
CREATE DATABASE ticket_db;
```

### Step 2 — Start Backend
```bash
cd movie-booking-backend
mvn spring-boot:run
```
Backend starts on `http://localhost:8080`
Hibernate auto-creates tables on first run.
`DataSeeder` auto-inserts 8 movies if the table is empty.

### Step 3 — Start Frontend
```bash
cd movie-booking
ng serve
```
Frontend starts on `http://localhost:4200` (or any available port)

### Step 4 — Open Browser
Navigate to `http://localhost:4200` → redirected to Login page.

---

## 12. Key Angular Concepts Used

| Concept | Where Used | Why |
|---|---|---|
| Components | All pages | Each screen is an independent reusable unit |
| Services | AuthService, MovieService, BookingService | Centralizes HTTP logic, shared across components |
| HttpClient | All services | Makes REST API calls to Spring Boot |
| Angular Router | app.routes.ts | Navigates between pages without full page reload |
| RouterLink | Navbar | Declarative navigation links in templates |
| RouterLinkActive | Navbar | Highlights the current active navigation link |
| ActivatedRoute | MovieDetail, Booking | Reads URL parameters like `/movies/:id` |
| Router State | MovieDetail, Booking, Confirmation | Passes data between pages without extra API calls |
| canActivate Guard | All protected routes | Blocks unauthenticated access, redirects to login |
| ngOnInit | All page components | Runs initialization logic after component loads |
| ngFor | Movies, MyBookings | Loops over arrays to render lists |
| ngIf | All components | Conditionally shows/hides elements |
| Two-way binding (ngModel) | Booking, Login, Signup forms | Syncs form input values with component properties |
| Angular Signals | AuthService, Navbar | Reactive state — navbar auto-updates on login/logout |
| Computed Signals | NavbarComponent | Derives isLoggedIn and userName reactively |
| FormsModule | Booking, Login, Signup | Enables template-driven forms with validation |
| CommonModule | All components | Provides ngIf, ngFor, async pipe etc. |
| Observable / subscribe | All HTTP calls | Handles async API responses with success/error |
| RxJS HttpParams | BookingService | Builds query string `?email=` cleanly |
| SSR Server Routes | app.routes.server.ts | Marks dynamic routes as Client render mode to avoid SSR prerender errors |

---

## 13. Key Spring Boot Concepts Used

| Concept | Where Used | Why |
|---|---|---|
| @SpringBootApplication | MovieBookingApplication | Bootstraps the entire Spring Boot app |
| @RestController | All controllers | Marks class as REST API controller, returns JSON |
| @RequestMapping | All controllers | Sets base URL path for all methods in the controller |
| @GetMapping | MovieController, BookingController | Maps HTTP GET requests |
| @PostMapping | BookingController, UserController | Maps HTTP POST requests |
| @PathVariable | MovieController | Reads `{id}` from URL path |
| @RequestParam | BookingController | Reads `?email=` query parameter |
| @RequestBody | BookingController, UserController | Deserializes JSON request body to Java object |
| @ResponseStatus | BookingController | Sets HTTP 201 Created on booking POST |
| @Entity | Movie, Booking, User | Marks class as a JPA-managed DB table |
| @Table | All entities | Specifies the exact table name in PostgreSQL |
| @Id + @GeneratedValue | All entities | Primary key with auto-increment |
| @Column(unique=true) | User.email | Enforces email uniqueness at DB level |
| JpaRepository | All repositories | Provides save, findAll, findById out of the box |
| Custom JPQL Query (@Query) | BookingRepository | Case-insensitive email search |
| @Service | All services | Marks class as a Spring-managed service bean |
| @Transactional | BookingService | Ensures booking is fully saved or fully rolled back |
| @Configuration + @Bean | CorsConfig | Registers global CORS configuration |
| CommandLineRunner | DataSeeder | Runs seed logic once on app startup |
| @JsonFormat | Booking.bookingDate | Serializes LocalDate as `yyyy-MM-dd` string in JSON |
| spring.jpa.ddl-auto=update | application.properties | Auto-creates/updates DB tables from entity classes |
| ResourceNotFoundException | MovieService | Returns HTTP 404 when movie not found |
