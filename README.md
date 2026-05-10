This project is a backend API for a product catalog and inventory management system. Built with Spring Boot, it provides REST endpoints for managing products, categories, and users with JWT-based authentication and image upload support.

The API uses **JWT Bearer tokens**. After logging in via `POST /users/login`, include the token in subsequent requests:

```
Authorization: Bearer <your-jwt-token>
```

All input DTOs are validated using Jakarta Bean Validation. Validation errors return a structured `ErrorResponse` with field-level details.

---

### Features

- **Product Management** — Create, read, update, delete products with name, description, price, quantity, category, and image
- **Category Management** — Organize products into categories with full CRUD operations
- **User Authentication** — Register and login with JWT tokens
- **Image Upload** — Upload and retrieve product images via multipart file requests
- **Pagination & Search** — Paginated listing with optional name filtering for products, categories, and users
- **Input Validation** — Jakarta Bean Validation with custom error responses

---

### Tech Stack

| Technology      | Version |
| --------------- | ------- |
| Java            | 21      |
| Spring Boot     | 3.5.5   |
| Spring Data JPA | —       |
| Spring Security | —       |
| PostgreSQL      | —       |
| Auth0 Java JWT  | 4.5.0   |
| Docker          | —       |

### Prerequisites

- Java 21
- Maven
- PostgreSQL database
- (Optional) Docker & Docker Compose

### Running Locally

```bash
# Clone the repository
git clone https://github.com/fagner02/npi-back.git
cd npi-back

# Run with Maven
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

### Running with Docker

```bash
# Build and run with Docker Compose
docker-compose up --build
```

---

## Data Models

### Product

- `id` (Long)
- `name` (String, required)
- `description` (String)
- `price` (Double, required, > 0)
- `quantity` (Integer, required, ≥ 0)
- `category` (Category, nullable)
- `image` (byte[], nullable)

### Category

- `id` (Long)
- `name` (String, required, unique)
- `description` (String)
- `products` (List<Product>)

### User

- `id` (Long)
- `username` (String, required)
- `email` (String, required)
- `password` (String, required)
