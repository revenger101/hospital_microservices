# Docker setup (microservices)

This repo contains multiple Spring Boot services.

## Prerequisites

- Docker Desktop installed and running
- Docker Compose v2 (comes with Docker Desktop)

## Build images

From the repo root:

```bash
docker compose build
```

## Run the full stack

```bash
docker compose up
```

To run in background:

```bash
docker compose up -d
```

## Stop

```bash
docker compose down
```

## URLs (host machine)

- Eureka dashboard: http://localhost:8761
- API Gateway: http://localhost:8080
- Patient Service: http://localhost:8081
- Doctor Service: http://localhost:8082
- Notification Service: http://localhost:8083
- Appointment Service: http://localhost:8084
- Auth Service: http://localhost:8090

## Notes

- Inside Docker, services talk to Eureka using `http://eureka:8761/eureka/` via environment overrides in `docker-compose.yml`.
- Each service uses an in-memory H2 database by default; data will reset when containers restart.
