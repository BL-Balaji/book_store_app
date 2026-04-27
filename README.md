# 📚 BookStore Microservices Application

![Java](https://img.shields.io/badge/Java-17+-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)
![Microservices](https://img.shields.io/badge/Architecture-Microservices-blue.svg)
![Docker](https://img.shields.io/badge/Docker-Enabled-blue.svg)
![Kafka](https://img.shields.io/badge/Kafka-Enabled-black.svg)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue.svg)

A production-grade, enterprise-level Book Store application built using **Spring Boot Microservices Architecture** with Spring Cloud, demonstrating modern distributed system patterns and best practices.

## 🏗️ System Architecture

This application follows a **microservices architecture** pattern where each service is independently deployable, scalable, and maintainable.

```
┌─────────────┐
│   Client    │
└──────┬──────┘
       │
       ▼
┌─────────────────────────────────────┐
│       API Gateway (8080)            │
│  - JWT Validation                   │
│  - Request Routing                  │
│  - Load Balancing                   │
└──────┬──────────────────────────────┘
       │
       ├──────────────────┬──────────────────┬──────────────────┐
       ▼                  ▼                  ▼                  ▼
┌─────────────┐    ┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│User Service │    │Product Svc  │    │Cart Service │    │Order Service│
│   (8081)    │    │   (8083)    │    │   (8084)    │    │   (8087)    │
│ PostgreSQL  │    │ PostgreSQL  │    │   Redis     │    │ PostgreSQL  │
└─────────────┘    └─────────────┘    └─────────────┘    └──────┬──────┘
                                                                  │
       ┌──────────────────────────────────────────────────────────┘
       ▼
┌─────────────────────────────────────┐
│     Apache Kafka Message Bus        │
│  - order-events                     │
│  - user-events                      │
│  - inventory-events                 │
│  - review-events                    │
└──────┬──────────────────────────────┘
       │
       ▼
┌─────────────────────┐
│ Notification Service│
│      (8089)         │
└─────────────────────┘

       All Services Register With
              ▼
┌─────────────────────────────────────┐
│   Eureka Discovery Server (8761)    │
└─────────────────────────────────────┘

       Configuration Managed By
              ▼
┌─────────────────────────────────────┐
│     Config Server (8888)            │
└─────────────────────────────────────┘
```

## 🎯 Microservices Overview

| # | Service | Port | Database | Description |
|---|---------|------|----------|-------------|
| 1 | **Eureka Server** | 8761 | - | Service discovery and registration |
| 2 | **Config Server** | 8888 | - | Centralized configuration management |
| 3 | **API Gateway** | 8080 | - | Entry point, routing, JWT validation |
| 4 | **User Service** | 8081 | PostgreSQL | Authentication, authorization, JWT |
| 5 | **Admin Service** | 8082 | PostgreSQL | Admin operations, user management |
| 6 | **Product Service** | 8083 | PostgreSQL | Book catalog, inventory management |
| 7 | **Cart Service** | 8084 | Redis | Shopping cart operations |
| 8 | **Wishlist Service** | 8085 | PostgreSQL | User wishlist management |
| 9 | **Customer Details Service** | 8086 | PostgreSQL | Customer profiles, addresses |
| 10 | **Order Service** | 8087 | PostgreSQL | Order processing, tracking |
| 11 | **Feedback Service** | 8088 | PostgreSQL | Reviews, ratings |
| 12 | **Notification Service** | 8089 | - | Email/SMS notifications via Kafka |

## 🛠️ Technology Stack

### Core Technologies
- **Java 17+** - Programming language
- **Spring Boot 3.2.0** - Application framework
- **Spring Cloud 2023.0.0** - Microservices framework
- **Maven** - Build tool

### Spring Cloud Components
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Cloud Config** - Configuration management
- **Spring Cloud Gateway** - API gateway
- **Spring Cloud OpenFeign** - Declarative REST client
- **Resilience4j** - Circuit breaker, retry, rate limiter

### Security
- **Spring Security** - Security framework
- **JWT (JSON Web Tokens)** - Authentication & authorization
- **BCrypt** - Password encryption

### Databases
- **PostgreSQL** - Primary database (per service)
- **Redis** - Caching and cart service

### Messaging
- **Apache Kafka** - Event-driven communication
- **RabbitMQ** - Alternative message broker (optional)

### Documentation
- **SpringDoc OpenAPI 3** - API documentation
- **Swagger UI** - Interactive API explorer

### DevOps & Deployment
- **Docker** - Containerization
- **Docker Compose** - Multi-container orchestration
- **Kubernetes** - Container orchestration (optional)

### Additional Libraries
- **Lombok** - Reduce boilerplate code
- **MapStruct** - Object mapping
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **Testcontainers** - Integration testing

## 📋 Prerequisites

Before running this application, ensure you have:

- **Java 17+** installed
- **Maven 3.8+** installed
- **Docker & Docker Compose** installed
- **Git** installed
- **PostgreSQL** (if running without Docker)
- **Redis** (if running without Docker)
- **Kafka** (if running without Docker)

## 🚀 Quick Start

### 1. Clone the Repository

```bash
git clone https://github.com/BL-Balaji/book_store_app.git
cd book_store_app
```

### 2. Build All Services

```bash
mvn clean install -DskipTests
```

### 3. Run with Docker Compose

```bash
docker-compose up -d
```

This will start:
- PostgreSQL databases (one per service)
- Redis
- Kafka & Zookeeper
- All microservices

### 4. Access the Services

| Service | URL |
|---------|-----|
| API Gateway | http://localhost:8080 |
| Eureka Dashboard | http://localhost:8761 |
| User Service Swagger | http://localhost:8081/swagger-ui.html |
| Product Service Swagger | http://localhost:8083/swagger-ui.html |
| Order Service Swagger | http://localhost:8087/swagger-ui.html |

## 🔐 Security & Authentication

### JWT Authentication Flow

1. User registers via `/api/users/register`
2. User logs in via `/api/users/login` → receives JWT token
3. Include JWT in subsequent requests: `Authorization: Bearer <token>`
4. API Gateway validates JWT before routing to services

### User Roles

- **USER** - Regular customer (browse, cart, order)
- **ADMIN** - Manage products, view orders
- **SUPER_ADMIN** - Full system access

### Sample API Calls

#### Register User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "email": "john@example.com",
    "password": "SecurePass123",
    "role": "USER"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123"
  }'
```

#### Access Protected Resource
```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer <your-jwt-token>"
```

## 📡 Inter-Service Communication

### Synchronous Communication (Feign Clients)
- User Service ↔ Admin Service
- Order Service → Product Service (inventory check)
- Order Service → Customer Service (address validation)

### Asynchronous Communication (Kafka Events)

| Topic | Producer | Consumer | Event |
|-------|----------|----------|-------|
| `order-events` | Order Service | Notification Service | Order placed/updated |
| `user-events` | User Service | Notification Service | User registered |
| `inventory-events` | Product Service | Order Service | Stock updated |
| `review-events` | Feedback Service | Product Service | Rating updated |

## 🗂️ Project Structure

```
bookstore-microservices/
│
├── eureka-server/                 # Service discovery
├── config-server/                 # Configuration server
├── api-gateway/                   # API Gateway with JWT
│
├── user-service/                  # Authentication & user management
│   ├── src/main/java/com/bookstore/user/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── entity/
│   │   ├── dto/
│   │   ├── config/
│   │   ├── security/
│   │   ├── exception/
│   │   └── util/
│   ├── Dockerfile
│   └── pom.xml
│
├── admin-service/                 # Admin operations
├── product-service/               # Product catalog
├── cart-service/                  # Shopping cart (Redis)
├── wishlist-service/              # Wishlist management
├── customer-details-service/      # Customer profiles
├── order-service/                 # Order processing
├── feedback-service/              # Reviews & ratings
├── notification-service/          # Kafka-based notifications
│
├── common-lib/                    # Shared utilities
│   ├── dto/
│   ├── exception/
│   ├── security/
│   └── util/
│
├── docker-compose.yml             # Docker orchestration
├── pom.xml                        # Parent POM
├── .gitignore
└── README.md
```

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

### Test Coverage
```bash
mvn clean test jacoco:report
```

## 📊 Monitoring & Observability

### Health Checks
Each service exposes actuator endpoints:
```
http://localhost:<port>/actuator/health
http://localhost:<port>/actuator/info
http://localhost:<port>/actuator/metrics
```

### Eureka Dashboard
Monitor all registered services:
```
http://localhost:8761
```

## 🔄 CI/CD Pipeline

The project includes GitHub Actions workflows for:
- Automated testing on push/PR
- Docker image building
- Deployment to staging/production

## 📚 API Documentation

Each service provides Swagger UI documentation:

- **User Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8083/swagger-ui.html
- **Cart Service**: http://localhost:8084/swagger-ui.html
- **Order Service**: http://localhost:8087/swagger-ui.html

Aggregated API docs available through API Gateway:
```
http://localhost:8080/swagger-ui.html
```

## 🎯 Key Features Implemented

### User Management
- ✅ User registration with email verification
- ✅ JWT-based authentication
- ✅ Role-based access control (USER, ADMIN, SUPER_ADMIN)
- ✅ Password encryption with BCrypt
- ✅ Profile management

### Product Management
- ✅ CRUD operations for books
- ✅ Category management
- ✅ Inventory tracking
- ✅ Search and filtering
- ✅ Pagination support

### Shopping Experience
- ✅ Add/remove items to cart
- ✅ Cart persistence with Redis
- ✅ Wishlist management
- ✅ Price calculation

### Order Processing
- ✅ Order placement
- ✅ Order status tracking
- ✅ Order history
- ✅ Inventory validation
- ✅ Kafka event publishing

### Feedback System
- ✅ Product reviews
- ✅ Star ratings
- ✅ Review moderation

### Notifications
- ✅ Order confirmation emails
- ✅ Registration welcome emails
- ✅ Event-driven via Kafka

## 🔧 Configuration

### Application Properties
Each service has its own `application.yml` in the config-server repository.

### Environment Variables
```bash
# Database
POSTGRES_USER=bookstore
POSTGRES_PASSWORD=bookstore123
POSTGRES_DB=bookstore_db

# Redis
REDIS_HOST=localhost
REDIS_PORT=6379

# Kafka
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# JWT
JWT_SECRET=your-secret-key-here
JWT_EXPIRATION=86400000
```

## 🐛 Troubleshooting

### Services not registering with Eureka
- Ensure Eureka Server is running first
- Check network connectivity
- Verify `eureka.client.service-url.defaultZone` in application.yml

### Database connection issues
- Verify PostgreSQL is running
- Check database credentials
- Ensure database exists

### Kafka connection issues
- Verify Kafka and Zookeeper are running
- Check `spring.kafka.bootstrap-servers` configuration

## 🚀 Future Enhancements

- [ ] Implement payment gateway integration
- [ ] Add recommendation engine
- [ ] Implement distributed tracing (Zipkin/Jaeger)
- [ ] Add centralized logging (ELK Stack)
- [ ] Implement API rate limiting
- [ ] Add GraphQL support
- [ ] Implement CQRS pattern
- [ ] Add event sourcing
- [ ] Kubernetes deployment manifests
- [ ] Implement service mesh (Istio)

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👨‍💻 Author

**BL Balaji**

- GitHub: [@BL-Balaji](https://github.com/BL-Balaji)
- Repository: [book_store_app](https://github.com/BL-Balaji/book_store_app)

## 🙏 Acknowledgments

- Spring Boot Team for excellent documentation
- Spring Cloud Team for microservices tools
- Reference Architecture: [BookStore-Microservices](https://github.com/SKarthik12321/BookStore-Microservices)

## 📞 Support

For issues, questions, or contributions, please open an issue in the GitHub repository.

---

⭐ If you find this project helpful, please give it a star!
