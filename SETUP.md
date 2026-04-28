# BookStore Microservices - Setup Guide

## Prerequisites

Ensure you have the following installed on your system:

- **Java 17 or higher**
  ```bash
  java -version
  ```

- **Maven 3.8+**
  ```bash
  mvn -version
  ```

- **Docker & Docker Compose**
  ```bash
  docker --version
  docker-compose --version
  ```

- **Git**
  ```bash
  git --version
  ```

## Step-by-Step Setup

### 1. Clone the Repository

```bash
git clone https://github.com/BL-Balaji/book_store_app.git
cd book_store_app
```

### 2. Build All Services

Build all microservices using Maven:

```bash
mvn clean install -DskipTests
```

This will:
- Compile all services
- Create JAR files in each service's `target/` directory
- Install dependencies

### 3. Start Infrastructure Services

Start PostgreSQL, Redis, Kafka, and Zookeeper:

```bash
docker-compose up -d postgres-user postgres-admin postgres-product postgres-wishlist postgres-customer postgres-order postgres-feedback redis zookeeper kafka
```

Wait for services to be healthy (about 30 seconds):

```bash
docker-compose ps
```

### 4. Start Eureka Server

```bash
docker-compose up -d eureka-server
```

Wait for Eureka to start (check at http://localhost:8761):

```bash
# Wait about 30 seconds, then check
curl http://localhost:8761/actuator/health
```

### 5. Start Config Server

```bash
docker-compose up -d config-server
```

### 6. Start API Gateway

```bash
docker-compose up -d api-gateway
```

### 7. Start All Microservices

```bash
docker-compose up -d user-service admin-service product-service cart-service wishlist-service customer-details-service order-service feedback-service notification-service
```

### 8. Verify All Services

Check all services are running:

```bash
docker-compose ps
```

Check Eureka Dashboard to see registered services:
```
http://localhost:8761
```

## Alternative: Start Everything at Once

```bash
# Build first
mvn clean install -DskipTests

# Start all services
docker-compose up -d

# View logs
docker-compose logs -f
```

## Running Without Docker

If you prefer to run services locally without Docker:

### 1. Start Infrastructure

You'll need to install and start:
- PostgreSQL (create databases: user_db, admin_db, product_db, etc.)
- Redis
- Kafka & Zookeeper

### 2. Update Configuration

Update `application.yml` in each service to use `localhost` instead of container names.

### 3. Start Services

Start each service individually:

```bash
# Terminal 1 - Eureka Server
cd eureka-server
mvn spring-boot:run

# Terminal 2 - Config Server
cd config-server
mvn spring-boot:run

# Terminal 3 - API Gateway
cd api-gateway
mvn spring-boot:run

# Terminal 4 - User Service
cd user-service
mvn spring-boot:run

# ... and so on for other services
```

## Testing the Application

### 1. Check Health

```bash
# API Gateway
curl http://localhost:8080/actuator/health

# User Service
curl http://localhost:8081/actuator/health

# Product Service
curl http://localhost:8083/actuator/health
```

### 2. Register a User

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123",
    "firstName": "Test",
    "lastName": "User",
    "role": "USER"
  }'
```

### 3. Login

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

Save the JWT token from the response.

### 4. Access Protected Endpoint

```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

## Accessing Swagger Documentation

Each service has Swagger UI available:

- **User Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8083/swagger-ui.html
- **Cart Service**: http://localhost:8084/swagger-ui.html
- **Order Service**: http://localhost:8087/swagger-ui.html
- **Feedback Service**: http://localhost:8088/swagger-ui.html

## Stopping Services

```bash
# Stop all services
docker-compose down

# Stop and remove volumes (WARNING: This deletes all data)
docker-compose down -v
```

## Troubleshooting

### Services not registering with Eureka

1. Check Eureka is running: http://localhost:8761
2. Check service logs: `docker-compose logs service-name`
3. Verify network connectivity: `docker network inspect book_store_app_bookstore-network`

### Database connection errors

1. Check PostgreSQL is running: `docker-compose ps postgres-user`
2. Check credentials in `application.yml`
3. Verify database exists: `docker exec -it postgres-user psql -U bookstore -l`

### Kafka connection errors

1. Check Kafka is running: `docker-compose ps kafka`
2. Check Zookeeper is running: `docker-compose ps zookeeper`
3. View Kafka logs: `docker-compose logs kafka`

### Port already in use

If you get "port already in use" errors:

```bash
# Find process using port (example for 8080)
lsof -i :8080

# Kill the process
kill -9 <PID>
```

## Development Tips

### Hot Reload

For development with hot reload:

```bash
cd user-service
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
```

### View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f user-service

# Last 100 lines
docker-compose logs --tail=100 user-service
```

### Rebuild Single Service

```bash
# Rebuild and restart
cd user-service
mvn clean package -DskipTests
docker-compose up -d --build user-service
```

## Next Steps

1. Import Postman collection from `postman/` directory
2. Review API documentation in Swagger UI
3. Check out example requests in `docs/api-examples.md`
4. Read architecture documentation in `docs/architecture.md`

## Support

For issues or questions:
- Open an issue on GitHub
- Check existing issues for solutions
- Review logs for error messages

---

Happy coding! 🚀
