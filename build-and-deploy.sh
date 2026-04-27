#!/bin/bash

# BookStore Microservices - Build and Deploy Script
# This script builds all services and deploys them using Docker Compose

set -e  # Exit on error

echo "========================================="
echo "BookStore Microservices Build & Deploy"
echo "========================================="
echo ""

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Function to print colored output
print_success() {
    echo -e "${GREEN}✓ $1${NC}"
}

print_warning() {
    echo -e "${YELLOW}⚠ $1${NC}"
}

print_error() {
    echo -e "${RED}✗ $1${NC}"
}

# Check prerequisites
echo "Checking prerequisites..."

if ! command -v java &> /dev/null; then
    print_error "Java is not installed. Please install Java 17 or higher."
    exit 1
fi
print_success "Java found: $(java -version 2>&1 | head -n 1)"

if ! command -v mvn &> /dev/null; then
    print_error "Maven is not installed. Please install Maven 3.8 or higher."
    exit 1
fi
print_success "Maven found: $(mvn -version | head -n 1)"

if ! command -v docker &> /dev/null; then
    print_error "Docker is not installed. Please install Docker."
    exit 1
fi
print_success "Docker found: $(docker --version)"

if ! command -v docker-compose &> /dev/null; then
    print_warning "docker-compose not found, checking for 'docker compose'..."
    if ! docker compose version &> /dev/null; then
        print_error "Docker Compose is not installed."
        exit 1
    fi
    DOCKER_COMPOSE="docker compose"
else
    DOCKER_COMPOSE="docker-compose"
fi
print_success "Docker Compose found"

echo ""
echo "========================================="
echo "Step 1: Building All Services"
echo "========================================="
echo ""

# Build all services
echo "Building with Maven..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    print_success "All services built successfully!"
else
    print_error "Build failed. Please check the errors above."
    exit 1
fi

echo ""
echo "========================================="
echo "Step 2: Starting Infrastructure Services"
echo "========================================="
echo ""

# Start infrastructure services first
echo "Starting PostgreSQL databases, Redis, Kafka, and Zookeeper..."
$DOCKER_COMPOSE up -d postgres-user postgres-admin postgres-product postgres-wishlist postgres-customer postgres-order postgres-feedback redis zookeeper kafka

print_success "Infrastructure services started"
echo "Waiting for services to be ready (30 seconds)..."
sleep 30

echo ""
echo "========================================="
echo "Step 3: Starting Eureka Server"
echo "========================================="
echo ""

$DOCKER_COMPOSE up -d eureka-server
print_success "Eureka Server started"
echo "Waiting for Eureka to be ready (30 seconds)..."
sleep 30

echo ""
echo "========================================="
echo "Step 4: Starting Config Server"
echo "========================================="
echo ""

$DOCKER_COMPOSE up -d config-server
print_success "Config Server started"
echo "Waiting for Config Server to be ready (20 seconds)..."
sleep 20

echo ""
echo "========================================="
echo "Step 5: Starting API Gateway"
echo "========================================="
echo ""

$DOCKER_COMPOSE up -d api-gateway
print_success "API Gateway started"
echo "Waiting for API Gateway to be ready (20 seconds)..."
sleep 20

echo ""
echo "========================================="
echo "Step 6: Starting All Microservices"
echo "========================================="
echo ""

$DOCKER_COMPOSE up -d user-service admin-service product-service cart-service wishlist-service customer-details-service order-service feedback-service notification-service

print_success "All microservices started"
echo "Waiting for services to register with Eureka (30 seconds)..."
sleep 30

echo ""
echo "========================================="
echo "Deployment Complete!"
echo "========================================="
echo ""

# Check service status
echo "Checking service status..."
$DOCKER_COMPOSE ps

echo ""
echo "========================================="
echo "Service URLs"
echo "========================================="
echo ""
echo "API Gateway:          http://localhost:8080"
echo "Eureka Dashboard:     http://localhost:8761"
echo "User Service Swagger: http://localhost:8081/swagger-ui.html"
echo "Product Service:      http://localhost:8083/swagger-ui.html"
echo "Cart Service:         http://localhost:8084/swagger-ui.html"
echo "Order Service:        http://localhost:8087/swagger-ui.html"
echo "Feedback Service:     http://localhost:8088/swagger-ui.html"
echo ""
echo "========================================="
echo "Quick Test Commands"
echo "========================================="
echo ""
echo "# Register a user:"
echo "curl -X POST http://localhost:8080/api/users/register \\"
echo "  -H 'Content-Type: application/json' \\"
echo "  -d '{\"username\":\"testuser\",\"email\":\"test@example.com\",\"password\":\"password123\",\"role\":\"USER\"}'"
echo ""
echo "# Login:"
echo "curl -X POST http://localhost:8080/api/users/login \\"
echo "  -H 'Content-Type: application/json' \\"
echo "  -d '{\"email\":\"test@example.com\",\"password\":\"password123\"}'"
echo ""
echo "========================================="
echo "View Logs"
echo "========================================="
echo ""
echo "# View all logs:"
echo "$DOCKER_COMPOSE logs -f"
echo ""
echo "# View specific service logs:"
echo "$DOCKER_COMPOSE logs -f user-service"
echo ""
echo "========================================="
echo "Stop Services"
echo "========================================="
echo ""
echo "# Stop all services:"
echo "$DOCKER_COMPOSE down"
echo ""
echo "# Stop and remove volumes (WARNING: deletes all data):"
echo "$DOCKER_COMPOSE down -v"
echo ""

print_success "Setup complete! Your BookStore Microservices are running."
