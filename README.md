# 🔐 Spring Boot JWT Authentication Service

A production-style authentication and authorization service built using **Spring Boot, Spring Security, JWT, and MySQL**.

This project implements a **real-world backend authentication system** with secure login, token-based authentication, role-based access, and global exception handling.

---

## 🚀 Features

- ✅ User Registration with BCrypt password encryption
- ✅ Login API with JWT token generation
- ✅ Stateless authentication using Spring Security
- ✅ Custom JWT filter to validate token on every request
- ✅ Role-based user model (USER / ADMIN)
- ✅ Protected APIs accessible only with Bearer token
- ✅ Global exception handling with clean API responses
- ✅ Standard API response structure
- ✅ MySQL + JPA + Hibernate integration

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


---

## 📦 API Endpoints

### 🔓 Public Endpoints

#### Register

#### Login

---

### 🔒 Protected Endpoints

#### Test Secured API

---

## 🧪 Sample API Response

### ✅ Success

```json
{
  "success": true,
  "message": "Login successful",
  "data": "eyJhbGciOiJIUzI1NiJ9..."
}
###❌ Error
{
  "success": false,
  "message": "Invalid credentials",
  "data": null
}

---

###⚙️ Setup Instructions
##1️⃣ Clone the repository
git clone https://github.com/<your-username>/springboot-jwt-auth-service.git

##2️⃣ Configure Database
CREATE DATABASE auth_service;

##Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/auth_service
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

##3️⃣ Run the application
mvn spring-boot:run

##4️⃣ Insert Roles (IMPORTANT)
INSERT INTO roles(name) VALUES ('ROLE_USER');
INSERT INTO roles(name) VALUES ('ROLE_ADMIN');

###🛡️ Security
Passwords are stored using BCrypt hashing

Authentication is stateless

JWT token is required for accessing protected endpoints

Token is validated on every request using a custom filter

###📈 Future Improvements
🔄 Refresh Token implementation

🧑‍⚖️ Role-based authorization using @PreAuthorize

📄 Swagger / OpenAPI documentation

📦 Dockerization

🧪 Unit & Integration tests

###👨‍💻 Author
Neeraj Sharma

Java Backend Developer

Focused on building production-grade backend systems

###⭐ If you like this project

Give it a ⭐ and feel free to fork or contribute!

