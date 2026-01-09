# 🔐 Spring Boot JWT Authentication Service

A production-style authentication and authorization service built using **Spring Boot, Spring Security, JWT, and MySQL**.

This project implements a real-world backend authentication system with secure login, token-based authentication, role-based access control, and global exception handling.

---

## 🚀 Features

- User Registration with BCrypt password encryption
- Login API with JWT token generation
- Stateless authentication using Spring Security
- Custom JWT filter to validate token on every request
- Role-based user model (USER / ADMIN)
- Protected APIs accessible only with Bearer token
- Global exception handling with clean API responses
- Standard API response structure
- MySQL + JPA + Hibernate integration

---

## 🏗️ Tech Stack

- Java 17+
- Spring Boot
- Spring Security
- JWT (io.jsonwebtoken)
- MySQL
- JPA / Hibernate
- Maven
- Lombok

---

## 🔁 Authentication Flow

```
Register → Login → Get JWT → Send JWT in Authorization Header → JWT Filter Validates → Access Granted / Denied
```

---

## 📦 API Endpoints

### 🔓 Public

```http
POST /api/auth/register
POST /api/auth/login
```

### 🔒 Protected

```http
GET /api/test/secure
```

Header:
```
Authorization: Bearer <JWT_TOKEN>
```

---

## 🧪 Sample API Response

### ✅ Success

```json
{
  "success": true,
  "message": "Login successful",
  "data": "eyJhbGciOiJIUzI1NiJ9..."
}
```

### ❌ Error

```json
{
  "success": false,
  "message": "Invalid credentials",
  "data": null
}
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the repository

```bash
git clone https://github.com/neeraj552/auth-service-springboot-jwt.git
cd auth-service-springboot-jwt
```

---

### 2️⃣ Create Database

```sql
CREATE DATABASE auth_service;
```

---

### 3️⃣ Configure application.properties

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/auth_service
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
```

---

### 4️⃣ Run the application

```bash
mvn spring-boot:run
```

---

### 5️⃣ Insert Roles (IMPORTANT)

```sql
INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');
```

---

## 🛡️ Security

- Passwords are stored using **BCrypt hashing**
- Authentication is **stateless**
- JWT token is required for accessing protected endpoints
- Token is validated on every request using a **custom JWT filter**

---

## 📈 Future Improvements

- Refresh Token implementation
- Role-based authorization using `@PreAuthorize`
- Swagger / OpenAPI documentation
- Dockerization
- Unit & Integration tests

---

## 👨‍💻 Author

**Neeraj Sharma**  
Java Backend Developer  
Focused on building production-grade backend systems

---

## ⭐ If you like this project

Give it a ⭐ and feel free to fork or contribute!
