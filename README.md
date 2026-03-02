# E-Commerce MVP — Lab 0

Spring Boot e-commerce application configured for production standards (12-Factor App).

---

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose installed

### Run everything with one command
```bash
docker-compose up --build
```

This will:
1. Build the application JAR inside Docker (no local JDK/Maven required)
2. Start PostgreSQL and wait until it's healthy
3. Start the app — Flyway migrations run automatically on startup

### Run unit tests
```bash
./mvnw test
```

> Tests use an in-memory H2 database — no PostgreSQL required.

![](/screenshots/1.png)

---

## ⚙️ Environment Variables

All configuration is provided via environment variables. No passwords or URLs are hardcoded in source code.

| Variable      | Description           | Value in docker-compose |
|---------------|-----------------------|-------------------------|
| `DB_HOST`     | PostgreSQL host       | `postgres`              |
| `DB_PORT`     | PostgreSQL port       | `5432`                  |
| `DB_NAME`     | Database name         | `ecommerce`             |
| `DB_USER`     | Database username     | `postgres`              |
| `DB_PASSWORD` | Database password     | `postgres`              |

To override locally:
```bash
export DB_HOST=localhost
export DB_PORT=5433
export DB_NAME=ecommerce
export DB_USER=postgres
export DB_PASSWORD=postgres

./mvnw spring-boot:run
```

> Note: PostgreSQL is exposed on host port `5433` (mapped from container port `5432`).

---

## 🔍 Health Check

**Endpoint:** `GET /health`

| DB state   | HTTP status            |
|------------|------------------------|
| Connected  | `200 OK`               |
| Unreachable| `503 Service Unavailable` |
```bash
# 200 OK — normal state
curl -i localhost:8080/health

# 503 — simulate DB failure
docker-compose stop postgres
curl -i localhost:8080/health
```

### ✅ 200 OK (DB connected)
![screenshot](/screenshots/2.png)

### ❌ 503 Service Unavailable (DB down)
![screenshot](/screenshots/3.png)

---

## 📋 JSON Logs Example

All logs are written to `STDOUT` in JSON format (compatible with ELK / Prometheus collectors):
![screenshot](/screenshots/4.png)

---

## 🛑 Graceful Shutdown

The application handles `SIGTERM` — active HTTP requests are completed before shutdown and all DB connections are closed cleanly.
```bash
# Send SIGTERM to the container
docker-compose stop app

# Or send directly to the process
kill <pid>
```

Expected log sequence:
![](/screenshots/5.png)

---

## 🗄️ Database Migrations

Managed by **Flyway** — migrations run automatically on every startup. No manual SQL scripts needed.

| Version | File                  | Description           |
|---------|-----------------------|-----------------------|
| V1      | `V1__init_schema.sql` | Create products table |

---

## 📁 API Endpoints

| Method   | Path                 | Description       |
|----------|----------------------|-------------------|
| `GET`    | `/api/products`      | List all products |
| `GET`    | `/api/products/{id}` | Get product by ID |
| `POST`   | `/api/products`      | Create product    |
| `DELETE` | `/api/products/{id}` | Delete product    |
| `GET`    | `/health`            | Health check      |

---

## 🛠 Useful Commands
```bash
# Start everything
docker-compose up --build

# Start in background
docker-compose up --build -d

# View app logs
docker-compose logs -f app

# Stop containers (keep DB data)
docker-compose down

# Stop containers and delete DB volume
docker-compose down -v
```