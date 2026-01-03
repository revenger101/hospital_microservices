# Hospital Microservices (Spring Boot + Eureka + API Gateway)

This project is a small hospital microservices system built with:

- Spring Boot (v4.x)
- Spring Cloud (2025.1.x)
- Netflix Eureka (service discovery)
- Spring Cloud Gateway (WebMVC)
- JWT authentication (Auth service issues tokens; gateway + services validate tokens)
- Feign clients for service-to-service calls (with JWT forwarding)

## Repository structure

- `Eureka_Discovery_Server` — service discovery server
- `API_Gateway_Service` — single entry point for all APIs
- `Auth_Service` — issues JWT access tokens
- `Patient_Service` — patient CRUD + demo flow
- `Doctor_Service` — doctor CRUD + demo flow
- `Appointment_Service` — appointment CRUD + demo flow
- `Notification_Service` — notification CRUD + demo endpoint

## Ports

| Service | Port | URL (host) |
|---|---:|---|
| Eureka Discovery Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | http://localhost:8080 |
| Patient Service | 8081 | http://localhost:8081 |
| Doctor Service | 8082 | http://localhost:8082 |
| Notification Service | 8083 | http://localhost:8083 |
| Appointment Service | 8084 | http://localhost:8084 |
| Auth Service | 8090 | http://localhost:8090 |

## Authentication (JWT)

- `Auth_Service` exposes `POST /login` (no auth required).
- `API_Gateway_Service` allows unauthenticated access to `/auth/**` and `/actuator/**`.
- All other gateway routes require `Authorization: Bearer <token>`.
- Backend services validate JWTs with the shared secret/issuer from their `application.properties`.

Important:
- If you change `app.jwt.secret` or `app.jwt.issuer`, change it **everywhere** (gateway + all services + auth).

## How services talk to each other (Feign)

Some services call other services using Feign (via Eureka service discovery).
To keep authentication working across service-to-service calls, the following services include a Feign interceptor that forwards the incoming `Authorization` header:

- `Patient_Service`
- `Appointment_Service`
- `Doctor_Service`

That means if you call the gateway with a valid JWT, internal calls also carry the same JWT.

## Prerequisites (local / from scratch)

### Required

- Java 21 (this repo uses `<java.version>21</java.version>` in the Maven POMs)
- Maven Wrapper is included (`mvnw.cmd`), so you do not need Maven installed

### Optional

- Docker Desktop (for containerized run)

## Run with Docker (recommended)

Docker assets are already included:

- Root Compose file: `docker-compose.yml`
- A `Dockerfile` in each service folder

### 1) Start the stack

From the repository root:

```bash
docker compose up --build
```

### 2) Verify Eureka

Open:

- Eureka dashboard: http://localhost:8761

You should see the services registering (gateway + all microservices).

### 3) Stop

```bash
docker compose down
```

## Run locally (without Docker)

### Startup order

1. Eureka Discovery Server
2. Auth Service
3. Patient Service
4. Doctor Service
5. Notification Service
6. Appointment Service
7. API Gateway

### Commands (Windows PowerShell)

Run each service in its own terminal window.

#### 1) Eureka

```powershell
cd .\Eureka_Discovery_Server
.\mvnw.cmd spring-boot:run
```

#### 2) Auth

```powershell
cd .\Auth_Service
.\mvnw.cmd spring-boot:run
```

#### 3) Patient

```powershell
cd .\Patient_Service
.\mvnw.cmd spring-boot:run
```

#### 4) Doctor

```powershell
cd .\Doctor_Service
.\mvnw.cmd spring-boot:run
```

#### 5) Notification

```powershell
cd .\Notification_Service
.\mvnw.cmd spring-boot:run
```

#### 6) Appointment

```powershell
cd .\Appointment_Service
.\mvnw.cmd spring-boot:run
```

#### 7) API Gateway

```powershell
cd .\API_Gateway_Service
.\mvnw.cmd spring-boot:run
```

## API Gateway routing

The gateway uses these route prefixes (and strips the prefix before forwarding):

- `/auth/**` → Auth service
- `/patient/**` → Patient service
- `/doctor/**` → Doctor service
- `/notification/**` → Notification service
- `/appointment/**` → Appointment service

Example:

- `POST http://localhost:8080/auth/login` is forwarded to `POST /login` on the auth-service.
- `GET http://localhost:8080/patient/patients` is forwarded to `GET /patients` on the patient-service.

## Test the APIs (step-by-step)

All examples below call APIs through the gateway at `http://localhost:8080`.

### 0) Health check (no JWT)

- Eureka UI: http://localhost:8761

### 1) Login and get a JWT

Default credentials (configurable in `Auth_Service/src/main/resources/application.properties`):

- username: `admin`
- password: `admin123`

#### PowerShell

```powershell
$loginBody = @{ username = 'admin'; password = 'admin123' } | ConvertTo-Json
$login = Invoke-RestMethod -Method Post -Uri 'http://localhost:8080/auth/login' -ContentType 'application/json' -Body $loginBody

$token = $login.accessToken
$authHeader = @{ Authorization = "Bearer $token" }
```

#### curl

```bash
curl -s -X POST "http://localhost:8080/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

Response shape:

```json
{
  "tokenType": "Bearer",
  "accessToken": "...",
  "expiresAt": "..."
}
```

### 2) Create a doctor

Endpoint (through gateway):

- `POST /doctor/doctors`

```bash
curl -X POST "http://localhost:8080/doctor/doctors" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"name":"Dr. House","specialty":"Diagnostics","active":true}'
```

### 3) Create a patient

Endpoint (through gateway):

- `POST /patient/patients`

```bash
curl -X POST "http://localhost:8080/patient/patients" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","phone":"123456","dateOfBirth":"1990-01-01"}'
```

### 4) Book an appointment (direct CRUD)

Endpoint (through gateway):

- `POST /appointment/appointments`

```bash
curl -X POST "http://localhost:8080/appointment/appointments" \
  -H "Authorization: Bearer <TOKEN>" \
  -H "Content-Type: application/json" \
  -d '{"patientId": 1, "doctorId": 1}'
```

### 5) List resources

```bash
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/doctor/doctors"
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/patient/patients"
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/appointment/appointments"
curl -H "Authorization: Bearer <TOKEN>" "http://localhost:8080/notification/notifications"
```

## Demo flow (shows service-to-service calls)

This flow triggers a chain of internal calls (Feign + JWT forwarding):

1. Patient demo calls Appointment demo
2. Appointment demo calls Doctor demo
3. Doctor demo calls Notification demo

### Call the demo booking endpoint

Endpoint (through gateway):

- `GET /patient/demo/patient/book?patientId={id}&doctorId={id}`

Example:

```bash
curl -H "Authorization: Bearer <TOKEN>" \
  "http://localhost:8080/patient/demo/patient/book?patientId=1&doctorId=1"
```

Expected: a JSON response that includes an appointment creation result, doctor check, and notification payload.

## Run tests

This repo uses Maven per service. Run tests inside any service directory:

```powershell
cd .\Patient_Service
.\mvnw.cmd test
```

Repeat for other services if needed.

## Troubleshooting

- If a gateway call returns `401`, you likely forgot the `Authorization: Bearer <token>` header.
- If services do not show up in Eureka, confirm they can reach Eureka.
  - Local run uses `http://localhost:8761/eureka/`.
  - Docker run uses Compose overrides so services use `http://eureka:8761/eureka/`.
- These services use in-memory H2 by default; data resets when the service restarts.
