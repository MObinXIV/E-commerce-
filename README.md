# 🛒 E-commerce Backend API

A robust and scalable backend API for an e-commerce platform built with **Spring Boot**, featuring JWT-based authentication, role-based access control, product management, and order processing.

🔗 **Live API Docs (Swagger UI)**  
https://comfortable-raphaela-mobinxiv-b5da9fca.koyeb.app/swagger-ui/index.html#/

---

## 🚀 Features

- ✅ JWT-based authentication and authorization
- ✅ Role system: `CUSTOMER`, `SELLER`, `ADMIN`
- ✅ Product CRUD operations
- ✅ Buy-now ordering (no cart)
- ✅ Stock management on purchase
- ✅ Order lock after 2 days
- ✅ Flyway-based schema migration
- ✅ Dockerized for deployment
- ✅ Live deployment on Koyeb

---

## ⚙️ Tech Stack

- **Java 21**, **Spring Boot**
- **Spring Security**, **JWT**
- **PostgreSQL**, **Flyway**
- **MapStruct**, **JPA**
- **Swagger / OpenAPI 3**
- **Docker & Docker Compose**
- **Deployed via Koyeb**

---

## 🔐 Authentication

This API is protected using JWT. To test:

1. Register/Login to get a token
2. Use `Authorization: Bearer <token>` for protected routes

> Test via the [Swagger UI](https://comfortable-raphaela-mobinxiv-b5da9fca.koyeb.app/swagger-ui/index.html#/)

---

## 🧪 Running Locally

```bash
# Clone the project
git clone https://github.com/MObinXIV/E-commerce-.git
cd E-commerce-

# Run with Maven
./mvnw spring-boot:run

# Or run with Docker
docker-compose up --build
