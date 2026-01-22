# 🔐 Auth Service – Spring Boot JWT + Redis + CI

A **production-grade authentication & authorization service** built with **Spring Boot 3**, featuring **JWT security, Redis rate limiting, audit logging, full test coverage, and Jenkins CI**.

> Designed like a real-world backend system — not a tutorial project.

---

## 🚀 Key Features

- User Registration with **BCrypt password hashing**
- **JWT Authentication** (Access Token + Refresh Token)
- Refresh Token Flow with secure re-issuance
- **Stateless Spring Security** with custom JWT filter
- **Redis-based Rate Limiting** (login, register, forgot-password)
- Audit logging for sensitive security actions
- **Role-based Access Control** (USER / ADMIN)
- **Global Exception Handling** with consistent API responses
- **Unit + Integration Testing** (JUnit 5, Mockito, MockMvc)
- **CI Pipeline with Jenkins**
- MySQL + JPA + Hibernate

---

## 🏗️ Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Security  
- JWT (io.jsonwebtoken)  
- MySQL 8  
- Redis 7  
- Spring Data JPA / Hibernate  
- Maven  
- Lombok  
- JUnit 5, Mockito, MockMvc  
- Jenkins  

---

## 🔁 Authentication Flow

Register → Login → Receive Access & Refresh Token → Send JWT in Authorization Header → JWT Filter Validates → Access Granted / Denied

---

## 📦 API Endpoints

### Public

POST /api/auth/register  
POST /api/auth/login  
POST /api/auth/forgot-password  
POST /api/auth/refresh-token  

### Protected

GET /api/test/user  
GET /api/test/admin  
GET /api/test/secure  

Header:  
Authorization: Bearer <JWT_TOKEN>

---

## 🧪 Sample API Responses

Success:

```json
{
  "success": true,
  "message": "Login successful",
  "data": {
    "accessToken": "jwt-token",
    "refreshToken": "refresh-token"
  }
}
```

Error:

```json
{
  "success": false,
  "message": "Invalid credentials",
  "data": null
}
```

Rate Limit:

```json
{
  "success": false,
  "message": "Too many requests. Try again later",
  "data": null
}
```

---

## ⚙️ Setup Instructions

Clone repository:

```bash
git clone https://github.com/neeraj552/authservice.git
cd authservice
```

Create database:

```sql
CREATE DATABASE auth_service;
```

application.properties:

```properties
spring.application.name=authservice
server.port=8080

spring.datasource.url=jdbc:mysql://localhost:3306/auth_service
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.data.redis.host=localhost
spring.data.redis.port=6379

jwt.secret=your-secret-key
jwt.expiration.access=3600000
jwt.expiration.refresh=2592000000
```

Insert roles:

```sql
INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
```

Run Redis:

```bash
redis-server
```

Run application:

```bash
mvn spring-boot:run
```

---

## 🧪 Testing

```bash
mvn test
```

Includes:
- Service tests (Mockito)
- Security & filter tests (MockMvc)
- Redis rate limit tests
- Verified by Maven Surefire

---

## 🔄 CI with Jenkins

- Jenkins pipeline configured
- Maven build + test execution
- Build fails on test failure
- Enforces production-level quality

---

## 🛡️ Security Highlights

- BCrypt password hashing
- Stateless JWT authentication
- Request-level JWT validation
- Redis-backed brute-force protection
- Audit logging

---

## 📁 Project Structure

```
src/main/java
├── controller
├── service
├── security
├── ratelimit
├── repository
├── entity
└── util

src/test/java
├── service
├── security
└── ratelimit
```

---

## 👨‍💻 Author

Neeraj Sharma  
Java Backend Developer  
Focused on production-grade backend systems, security, and scalability

---

## 📊 Project Status

COMPLETED  
Stable, tested, CI-verified authentication service.

---

## ⭐ Support

If you find this project useful, give it a star on GitHub.
