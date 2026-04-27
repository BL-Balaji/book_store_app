#!/bin/bash

# Script to generate skeleton structure for remaining microservices
# This creates the basic directory structure and placeholder files

echo "Generating BookStore Microservices Structure..."

# Array of services to generate
services=(
    "admin-service:8082:admin_db"
    "cart-service:8084:redis"
    "wishlist-service:8085:wishlist_db"
    "customer-details-service:8086:customer_db"
    "order-service:8087:order_db"
    "feedback-service:8088:feedback_db"
    "notification-service:8089:none"
)

# Function to create service structure
create_service_structure() {
    local service_name=$1
    local port=$2
    local db=$3
    
    echo "Creating structure for $service_name..."
    
    # Create directory structure
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/controller"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/service"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/repository"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/entity"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/dto"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/config"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/exception"
    mkdir -p "$service_name/src/main/java/com/bookstore/${service_name/-/}/util"
    mkdir -p "$service_name/src/main/resources"
    mkdir -p "$service_name/src/test/java/com/bookstore/${service_name/-/}"
    
    echo "✓ Created directory structure for $service_name"
}

# Generate structure for each service
for service_info in "${services[@]}"; do
    IFS=':' read -r service port db <<< "$service_info"
    create_service_structure "$service" "$port" "$db"
done

echo ""
echo "✓ Service structure generation complete!"
echo ""
echo "Next steps:"
echo "1. Implement controllers, services, and repositories for each service"
echo "2. Add application.yml configuration files"
echo "3. Create Dockerfile for each service"
echo "4. Build with: mvn clean install"
echo "5. Run with: docker-compose up -d"
