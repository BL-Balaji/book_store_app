# 📚 BookStore Microservices Application

![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen?style=for-the-badge&logo=springboot)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.0-brightgreen?style=for-the-badge&logo=spring)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue?style=for-the-badge)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-Event%20Driven-231F20?style=for-the-badge&logo=apachekafka)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql)
![Redis](https://img.shields.io/badge/Redis-Cache-DC382D?style=for-the-badge&logo=redis)
![JWT](https://img.shields.io/badge/JWT-Security-000000?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven)

> A **production-grade, enterprise-level Book Store application** built using **Spring Boot 3.x Microservices Architecture** with Spring Cloud, demonstrating modern distributed system patterns, event-driven communication, and industry best practices.

---

## 📋 Table of Contents

- [Project Overview](#-project-overview)
- [System Architecture](#-system-architecture)
- [Microservices Overview](#-microservices-overview)
- [Tech Stack](#-tech-stack)
- [Branch Structure](#-branch-structure)
- [Getting Started](#-getting-started)
- [Run with Docker Compose](#-run-with-docker-compose)
- [API Gateway Routes](#-api-gateway-routes)
- [JWT Authentication Flow](#-jwt-authentication-flow)
- [Kafka Event Flow](#-kafka-event-flow)
- [Swagger Documentation](#-swagger-documentation)
- [Project Structure](#-project-structure)
- [Future Enhancements](#-future-enhancements)

---

## 🎯 Project Overview

The BookStore Microservices application is a complete e-commerce backend for an online bookstore. It decomposes a monolithic architecture into **12 independent microservices**, each owning its own database and communicating via REST (synchronous) or Apache Kafka (asynchronous).

**Key Highlights:**
- 🔐 Centralized JWT authentication via API Gateway
- 🗄️ Database-per-service pattern (PostgreSQL + Redis)
- 📨 Event-driven notifications via Apache Kafka
- 🔄 Service discovery with Netflix Eureka
- ⚡ Circuit breaker with Resilience4j
- 🐳 Fully containerized with Docker Compose
- 📖 OpenAPI/Swagger documentation per service

---

## 🏗️ System Architecture

```
                          ┌─────────────────────────────────────────────────┐
                          │              CLIENT (Browser / Mobile)           │
                          └──────────────────────┬──────────────────────────┘
                                                 │ HTTPS
                                                 ▼
                          ┌─────────────────────────────────────────────────┐
                          │           API GATEWAY  :8080                    │
                          │   • JWT Validation                               │
                          │   • Request Routing                              │
                          │   • Load Balancing                               │
                          │   • CORS Handling                                │
                          └──────┬──────────┬──────────┬────────────────────┘
                                 │          │          │
              ┌──────────────────┘          │          └──────────────────────┐
              ▼                             ▼                                  ▼
  ┌───────────────────┐       ┌───────────────────┐              ┌───────────────────┐
  │  User Service     │       │  Product Service  │              │  Order Service    │
  │     :8081         │       │     :8083         │              │     :8087         │
  │  PostgreSQL       │       │  PostgreSQL       │              │  PostgreSQL       │
  └───────────────────┘       └───────────────────┘              └────────┬──────────┘
                                                                           │
  ┌───────────────────┐       ┌───────────────────┐                       │ Kafka
  │  Cart Service     │       │  Wishlist Service │                       ▼
  │     :8084         │       │     :8085         │       ┌───────────────────────────┐
  │  Redis            │       │  PostgreSQL       │       │   Apache Kafka            │
  └───────────────────┘       └───────────────────┘       │   • order-events          │
                                                           │   • user-events           │
  ┌───────────────────┐       ┌───────────────────┐       │   • inventory-events      │
  │  Customer Service │       │  Feedback Service │       │   • review-events         │
  │     :8086         │       │     :8088         │       └──────────────┬────────────┘
  │  PostgreSQL       │       │  PostgreSQL       │                      │
  └───────────────────┘       └───────────────────┘                      ▼
                                                           ┌───────────────────────────┐
  ┌───────────────────┐       ┌───────────────────┐       │  Notification Service     │
  │  Admin Service    │       │  Config Server    │       │     :8089                 │
  │     :8082         │       │     :8888         │       │  Email / SMS via Kafka    │
  │  PostgreSQL       │       │  Git Config       │       └───────────────────────────┘
  └───────────────────┘       └───────────────────┘

                          ┌─────────────────────────────────────────────────┐
                          │        Eureka Discovery Server  :8761            │
                          │   All services register and discover here        │
                          └─────────────────────────────────────────────────┘
```

---

## 🧩 Microservices Overview

| # | Service | Port | Database | Responsibility |
|---|---------|------|----------|----------------|
| 1 | **Eureka Server** | 8761 | — | Service discovery & registration |
| 2 | **Config Server** | 8888 | Git/Native | Centralized configuration management |
| 3 | **API Gateway** | 8080 | — | Routing, JWT validation, CORS, load balancing |
| 4 | **User Service** | 8081 | PostgreSQL | Registration, login, JWT issuance, profile |
| 5 | **Admin Service** | 8082 | PostgreSQL | Admin management, role assignment, dashboard |
| 6 | **Product Service** | 8083 | PostgreSQL | CRUD for books, categories, inventory |
| 7 | **Cart Service** | 8084 | Redis | Shopping cart sessions & calculations |
| 8 | **Wishlist Service** | 8085 | PostgreSQL | User-specific wishlist management |
| 9 | **Customer Details Service** | 8086 | PostgreSQL | Delivery addresses, preferences |
| 10 | **Order Service** | 8087 | PostgreSQL | Order lifecycle, status tracking, Kafka events |
| 11 | **Feedback Service** | 8088 | PostgreSQL | Product reviews & ratings |
| 12 | **Notification Service** | 8089 | — | Event-driven emails/SMS via Kafka |

---

## 🛠️ Tech Stack

### Core
| Technology | Version | Purpose |
|-----------|---------|---------|
| Java | 17+ | Programming language |
| Spring Boot | 3.2.0 | Application framework |
| Spring Cloud | 2023.0.0 | Microservices framework |
| Maven | 3.8+ | Build tool |

### Spring Cloud Components
| Component | Purpose |
|-----------|---------|
| Netflix Eureka | Service discovery & registration |
| Spring Cloud Config | Centralized configuration |
| Spring Cloud Gateway | API Gateway with filters |
| OpenFeign | Declarative REST client |
| Resilience4j | Circuit breaker, retry, rate limiter |

### Security
| Technology | Purpose |
|-----------|---------|
| Spring Security | Security framework |
| JWT (jjwt 0.12.3) | Stateless authentication |
| BCrypt | Password encryption |

### Databases & Messaging
| Technology | Purpose |
|-----------|---------|
| PostgreSQL 15 | Primary relational database (per service) |
| Redis 7 | Cart caching & session storage |
| Apache Kafka | Async event-driven communication |
| Zookeeper | Kafka coordination |

### DevOps & Documentation
| Technology | Purpose |
|-----------|---------|
| Docker | Containerization |
| Docker Compose | Multi-container orchestration |
| SpringDoc OpenAPI 3 | API documentation |
| Swagger UI | Interactive API explorer |
| Lombok | Boilerplate reduction |

---

## 🌿 Branch Structure

This repository follows a **GitFlow-inspired branching strategy**:

```
main          →  README + documentation only (this branch)
develop       →  complete microservices project (all code)
feature/*     →  individual service code only
```

### Branch Map

```
main
 └── develop  (full project: all 12 services + docker + pom)
      ├── feature/eureka-server
      ├── feature/config-server
      ├── feature/api-gateway
      ├── feature/common-lib
      ├── feature/user-service
      ├── feature/admin-service
      ├── feature/product-service
      ├── feature/cart-service
      ├── feature/wishlist-service
      ├── feature/customer-details-service
      ├── feature/order-service
      ├── feature/feedback-service
      └── feature/notification-service
```

### Branch Rules

| Branch | Contains | Purpose |
|--------|----------|---------|
| `main` | README, docs only | Documentation & project overview |
| `develop` | All microservices code | Integration branch |
| `feature/<service>` | Single service code | Service development |

### Workflow

```
feature/user-service  ──┐
feature/order-service ──┤──► develop ──► main
feature/product-service─┘
```

---

## 🚀 Getting Started

### Prerequisites

```bash
java -version    # Java 17+
mvn -version     # Maven 3.8+
docker --version # Docker 20+
docker-compose --version
```

### Clone the Repository

```bash
git clone https://github.com/BL-Balaji/book_store_app.git
cd book_store_app

# Switch to develop branch for full project code
git checkout develop
```

### Build All Services

```bash
mvn clean install -DskipTests
```

---

## 🐳 Run with Docker Compose

```bash
# Switch to develop branch
git checkout develop

# Build all services
mvn clean install -DskipTests

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

### Service URLs After Startup

| Service | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| API Gateway | http://localhost:8080 |
| User Service Swagger | http://localhost:8081/swagger-ui.html |
| Product Service Swagger | http://localhost:8083/swagger-ui.html |
| Cart Service Swagger | http://localhost:8084/swagger-ui.html |
| Order Service Swagger | http://localhost:8087/swagger-ui.html |
| Feedback Service Swagger | http://localhost:8088/swagger-ui.html |

---

## 🔀 API Gateway Routes

All requests go through the API Gateway at `http://localhost:8080`

| Route Prefix | Target Service | Port |
|-------------|---------------|------|
| `/api/users/**` | User Service | 8081 |
| `/api/admin/**` | Admin Service | 8082 |
| `/api/products/**` | Product Service | 8083 |
| `/api/categories/**` | Product Service | 8083 |
| `/api/cart/**` | Cart Service | 8084 |
| `/api/wishlist/**` | Wishlist Service | 8085 |
| `/api/customers/**` | Customer Details Service | 8086 |
| `/api/orders/**` | Order Service | 8087 |
| `/api/reviews/**` | Feedback Service | 8088 |
| `/api/feedback/**` | Feedback Service | 8088 |

### Public Endpoints (No Auth Required)
- `POST /api/users/register`
- `POST /api/users/login`
- `GET /actuator/**`
- `GET /swagger-ui/**`
- `GET /v3/api-docs/**`

---

## 🔐 JWT Authentication Flow

```
1. User Registration / Login
   POST /api/users/register  or  POST /api/users/login
   └── Returns: { "token": "eyJhbGci..." }

2. Authenticated Request
   GET /api/products
   Header: Authorization: Bearer eyJhbGci...
   └── API Gateway validates JWT
   └── Extracts username + role
   └── Forwards X-User-Id and X-User-Role headers to service

3. Role-Based Access
   USER       → browse, cart, orders, wishlist
   ADMIN      → manage products, view all orders
   SUPER_ADMIN → full system access
```

### Sample Requests

```bash
# Register
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","email":"john@example.com","password":"pass123","role":"USER"}'

# Login
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"pass123"}'

# Use token
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer <your-token>"
```

---

## 📨 Kafka Event Flow

```
Order Service ──────► order-events ──────► Notification Service
User Service  ──────► user-events  ──────► Notification Service
Product Service ────► inventory-events ──► Order Service
Feedback Service ───► review-events ─────► Product Service
```

### Event Topics

| Topic | Producer | Consumer | Payload |
|-------|----------|----------|---------|
| `order-events` | Order Service | Notification Service | orderId, userId, amount, status |
| `user-events` | User Service | Notification Service | email, eventType |
| `inventory-events` | Product Service | Order Service | productId, quantity |
| `review-events` | Feedback Service | Product Service | productId, rating |

---

## 📖 Swagger Documentation

Each service exposes Swagger UI:

| Service | Swagger URL |
|---------|------------|
| User Service | http://localhost:8081/swagger-ui.html |
| Admin Service | http://localhost:8082/swagger-ui.html |
| Product Service | http://localhost:8083/swagger-ui.html |
| Cart Service | http://localhost:8084/swagger-ui.html |
| Wishlist Service | http://localhost:8085/swagger-ui.html |
| Customer Service | http://localhost:8086/swagger-ui.html |
| Order Service | http://localhost:8087/swagger-ui.html |
| Feedback Service | http://localhost:8088/swagger-ui.html |

---

## 📂 Project Structure

> Full code is in the `develop` branch. Each `feature/*` branch contains only that service's code.

```
book_store_app/  (develop branch)
│
├── pom.xml                          # Parent Maven POM (multi-module)
├── docker-compose.yml               # Full stack orchestration
├── .gitignore
│
├── eureka-server/                   # Service Discovery
│   ├── src/main/java/com/bookstore/eureka/
│   ├── src/main/resources/application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── config-server/                   # Config Management
│   ├── src/main/java/com/bookstore/config/
│   ├── src/main/resources/application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── api-gateway/                     # API Gateway + JWT Filter
│   ├── src/main/java/com/bookstore/gateway/
│   │   ├── ApiGatewayApplication.java
│   │   └── filter/JwtAuthenticationFilter.java
│   ├── src/main/resources/application.yml
│   ├── Dockerfile
│   └── pom.xml
│
├── common-lib/                      # Shared Library
│   ├── src/main/java/com/bookstore/common/
│   │   ├── dto/ApiResponse.java
│   │   ├── exception/
│   │   └── util/JwtUtil.java
│   └── pom.xml
│
├── user-service/                    # Auth + User Management
│   ├── src/main/java/com/bookstore/user/
│   │   ├── controller/UserController.java
│   │   ├── service/UserService.java
│   │   ├── repository/UserRepository.java
│   │   ├── entity/User.java
│   │   ├── dto/
│   │   ├── config/SecurityConfig.java
│   │   └── config/KafkaProducerConfig.java
│   ├── Dockerfile
│   └── pom.xml
│
├── admin-service/                   # Admin Operations
├── product-service/                 # Product Catalog
├── cart-service/                    # Shopping Cart (Redis)
├── wishlist-service/                # Wishlist
├── customer-details-service/        # Customer Profiles
├── order-service/                   # Order Processing
├── feedback-service/                # Reviews & Ratings
└── notification-service/            # Kafka Notifications
```

---

## 🔮 Future Enhancements

- [ ] Payment gateway integration (Stripe / Razorpay)
- [ ] Product recommendation engine (ML-based)
- [ ] Distributed tracing with Zipkin / Jaeger
- [ ] Centralized logging with ELK Stack
- [ ] Kubernetes deployment manifests (Helm charts)
- [ ] Service mesh with Istio
- [ ] GraphQL API support
- [ ] CQRS + Event Sourcing pattern
- [ ] CI/CD pipeline with GitHub Actions
- [ ] Rate limiting per user/IP at Gateway
- [ ] Frontend application (React / Angular)

---

## 👨‍💻 Author

**BL Balaji**
- GitHub: [@BL-Balaji](https://github.com/BL-Balaji)
- Repository: [book_store_app](https://github.com/BL-Balaji/book_store_app)

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud teams for excellent frameworks
- Reference architecture: [BookStore-Microservices](https://github.com/SKarthik12321/BookStore-Microservices) by S. Karthik

---

> ⭐ **Star this repository** if you find it helpful!
>
> 🔀 **Switch to `develop` branch** to see the full project code.
