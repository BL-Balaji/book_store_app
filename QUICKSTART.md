# 🚀 Quick Start Guide

Get the BookStore Microservices application up and running in minutes!

## Prerequisites Check

Run these commands to verify you have everything installed:

```bash
java -version    # Should be 17 or higher
mvn -version     # Should be 3.8 or higher
docker --version
docker-compose --version  # or: docker compose version
```

If any command fails, install the missing software first.

## Option 1: Automated Setup (Recommended)

### For Linux/Mac:

```bash
# Make script executable
chmod +x build-and-deploy.sh

# Run the script
./build-and-deploy.sh
```

### For Windows:

```bash
# Using Git Bash or WSL
bash build-and-deploy.sh
```

The script will:
1. ✅ Check prerequisites
2. ✅ Build all services
3. ✅ Start infrastructure (PostgreSQL, Redis, Kafka)
4. ✅ Start Eureka Server
5. ✅ Start Config Server
6. ✅ Start API Gateway
7. ✅ Start all microservices

**Total time: ~5-7 minutes**

## Option 2: Manual Setup

### Step 1: Build

```bash
mvn clean install -DskipTests
```

### Step 2: Start Services

```bash
docker-compose up -d
```

### Step 3: Wait for Services

Wait about 2-3 minutes for all services to start and register with Eureka.

## Verify Installation

### Check Eureka Dashboard

Open: http://localhost:8761

You should see all services registered:
- API-GATEWAY
- USER-SERVICE
- ADMIN-SERVICE
- PRODUCT-SERVICE
- CART-SERVICE
- WISHLIST-SERVICE
- CUSTOMER-DETAILS-SERVICE
- ORDER-SERVICE
- FEEDBACK-SERVICE
- NOTIFICATION-SERVICE

### Check API Gateway

```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

## Test the Application

### 1. Register a User

```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john.doe",
    "email": "john@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "USER"
  }'
```

**Expected Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john.doe",
    "email": "john@example.com",
    "role": "USER"
  }
}
```

**Save the token!** You'll need it for authenticated requests.

### 2. Login

```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 3. Access Protected Endpoint

Replace `YOUR_TOKEN` with the token from registration/login:

```bash
curl -X GET http://localhost:8080/api/products \
  -H "Authorization: Bearer YOUR_TOKEN"
```

## Access Swagger UI

Interactive API documentation:

- **User Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8083/swagger-ui.html
- **Cart Service**: http://localhost:8084/swagger-ui.html
- **Order Service**: http://localhost:8087/swagger-ui.html
- **Feedback Service**: http://localhost:8088/swagger-ui.html

## View Logs

### All Services

```bash
docker-compose logs -f
```

### Specific Service

```bash
docker-compose logs -f user-service
```

### Last 100 Lines

```bash
docker-compose logs --tail=100 user-service
```

## Stop Services

### Stop All

```bash
docker-compose down
```

### Stop and Remove Data

**⚠️ WARNING: This deletes all data!**

```bash
docker-compose down -v
```

## Troubleshooting

### Services Not Starting

**Check Docker:**
```bash
docker ps
```

**Check logs:**
```bash
docker-compose logs service-name
```

### Port Already in Use

**Find process:**
```bash
# Linux/Mac
lsof -i :8080

# Windows
netstat -ano | findstr :8080
```

**Kill process:**
```bash
# Linux/Mac
kill -9 <PID>

# Windows
taskkill /PID <PID> /F
```

### Database Connection Issues

**Restart PostgreSQL:**
```bash
docker-compose restart postgres-user
```

**Check PostgreSQL logs:**
```bash
docker-compose logs postgres-user
```

### Eureka Registration Issues

1. Ensure Eureka is running: http://localhost:8761
2. Wait 30 seconds for services to register
3. Check service logs for errors

### Build Failures

**Clean and rebuild:**
```bash
mvn clean install -DskipTests -U
```

**Clear Maven cache:**
```bash
rm -rf ~/.m2/repository/com/bookstore
mvn clean install -DskipTests
```

## Common Issues

### Issue: "Cannot connect to Docker daemon"

**Solution:** Start Docker Desktop or Docker service

### Issue: "Port 8080 already in use"

**Solution:** Stop the process using port 8080 or change the port in `api-gateway/src/main/resources/application.yml`

### Issue: "Services not registering with Eureka"

**Solution:** 
1. Check Eureka is running
2. Wait 30-60 seconds
3. Restart the service: `docker-compose restart service-name`

### Issue: "Out of memory"

**Solution:** Increase Docker memory:
- Docker Desktop → Settings → Resources → Memory → Increase to 4GB+

## Next Steps

1. ✅ **Explore APIs**: Use Swagger UI to test endpoints
2. ✅ **Read Documentation**: Check `API_DOCUMENTATION.md`
3. ✅ **Import Postman**: Use the Postman collection (if available)
4. ✅ **Customize**: Modify services for your needs
5. ✅ **Deploy**: Follow deployment guides for production

## Useful Commands

```bash
# Check service status
docker-compose ps

# Restart a service
docker-compose restart user-service

# Rebuild a service
docker-compose up -d --build user-service

# View resource usage
docker stats

# Clean up everything
docker-compose down -v
docker system prune -a
```

## Getting Help

- 📖 **Documentation**: Check `README.md` and `SETUP.md`
- 🐛 **Issues**: Open an issue on GitHub
- 💬 **Discussions**: Use GitHub Discussions
- 📧 **Contact**: Reach out to the maintainer

## Success Checklist

- [ ] All services running (`docker-compose ps`)
- [ ] Eureka shows all services (http://localhost:8761)
- [ ] Can register a user
- [ ] Can login and get JWT token
- [ ] Can access protected endpoints with token
- [ ] Swagger UI accessible

---

**🎉 Congratulations!** Your BookStore Microservices application is running!

For detailed information, see:
- `README.md` - Complete project documentation
- `SETUP.md` - Detailed setup instructions
- `API_DOCUMENTATION.md` - API reference
- `CONTRIBUTING.md` - Contribution guidelines
