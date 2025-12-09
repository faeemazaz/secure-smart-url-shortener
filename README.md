# Secure Smart URL Shortener

A Spring Boot application that provides secure URL shortening with authentication, authorization, expiry logic, role‑based access, analytics support, and a strategy‑based redirection mechanism.

---

## 🚀 Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Data JPA**
* **Spring Security (JWT Authentication)**
* **MySQL**
* **Maven**

---

## 🔐 User Roles

The system supports two roles:

* **ADMIN**
* **USER**

---

## 🔑 Access Types

Each shortened URL supports one of the following access types:

* **PUBLIC** → accessible by anyone without JWT
* **PRIVATE** → only the creator can access (JWT required)
* **ROLE_BASED** → only users with a specific role can access

---

# 1. Authentication APIs

## ➤ **Signup**

**POST** `localhost:8080/auth/signup`

### Request

```json
{
  "email": "admin@gmail.com",
  "password": "123456",
  "role": "ADMIN"
}
```

### Description

* Creates a new user
* Password is encrypted using **BCrypt**

---

## ➤ **Login**

**POST** `localhost:8080/auth/login`

### Request

```json
{
  "email": "admin@gmail.com",
  "password": "123456"
}
```

### Response

* On success, the server returns a **JWT token**
* This token is required for all APIs except public redirect URLs

---

# 2. URL Shortening API

## ➤ **Create Short URL**

**POST** `localhost:8080/url/shorten`

### Request Example

```json
{
  "originalUrl": "https://gmail.com",
  "accessType": "ROLE_BASED",
  "allowedRole": "USER",
  "expiryInMinutes": 60,
  "maxClicks": 5
}
```

### Response Example

```json
{
  "shortUrl": "http://localhost:8080/r/AbC123"
}
```

### Notes

* Requires valid JWT
* Expiry time is stored internally as an **Instant**
* `maxClicks` can be used later for analytics/rate limiting

---

# 3. Redirection Endpoint (Strategy Pattern)

**GET** `localhost:8080/r/{code}`

This endpoint uses strategy‑based redirection depending on `accessType`.

### Rules

| Access Type    | Requires JWT  | Extra Rules                          |
| -------------- | ------------  | ------------------------------------ |
| **PUBLIC**     | ❌ No        | Anyone can access                    |
| **PRIVATE**    | ✔️ Yes       | Only creator can access              |
| **ROLE_BASED** | ✔️ Yes       | User's role must match `allowedRole` |

### Expiry Rule

* If the URL is expired → **HTTP 410 (Gone)**

### Example

**GET** `localhost:8080/r/AbC123`

---

# 4. Compress API

## ➤ **Run‑Length Encoding Compression**

**GET** `localhost:8080/logic/compress?input=aaabbccccdaa`

### Response

```json
{
  "result": "a3b2c4d1a2"
}
```

### Description

* Implements a simple run‑length compression logic
* No authentication required

---

# 🗄 Database Structure

## **Users Table**

* id
* email
* password
* role
* created_at

## **UrlRecord Table**

* id
* ownerId
* originalUrl
* shortCode
* accessType
* allowedRole
* expiryTime
* createdAt
* maxClicks

---

# ▶️ Run the Project

### 1. Create MySQL Database

```sql
CREATE DATABASE url_shortener;
```

### 2. Update `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/url_shortener
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=your_jwt_secret
jwt.expiration=7200000
```

### 3. Build & Run

```bash
mvn clean install
mvn spring-boot:run
```

---

# 📦 Deliverables

* Spring Boot Project
* MySQL schema
* Postman Collection
* README (this file)
* Proper Strategy Pattern Implementation
* JWT Authentication Working End‑to‑End

---

# ✔️ Summary

This project covers:

* User authentication
* Secure URL shortening
* Role‑based and private access
* Expiry logic handling
* Redirect strategy pattern
* Additional utility compression API

You now have a complete backend‑ready implementation with clear documentation.

