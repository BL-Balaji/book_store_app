# BookStore Microservices - API Documentation

## Base URL

All requests go through the API Gateway:
```
http://localhost:8080
```

## Authentication

Most endpoints require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your-jwt-token>
```

## API Endpoints

### 1. User Service (Port 8081)

#### Register User
```http
POST /api/users/register
Content-Type: application/json

{
  "username": "john.doe",
  "email": "john@example.com",
  "password": "SecurePass123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890",
  "role": "USER"
}
```

**Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "john.doe",
    "email": "john@example.com",
    "role": "USER"
  },
  "timestamp": "2024-01-15T10:30:00"
}
```

#### Login
```http
POST /api/users/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "SecurePass123"
}
```

#### Get User Profile
```http
GET /api/users/{id}
Authorization: Bearer <token>
```

#### Update User Profile
```http
PUT /api/users/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890"
}
```

### 2. Product Service (Port 8083)

#### Get All Products
```http
GET /api/products?page=0&size=10&sort=title,asc
Authorization: Bearer <token>
```

#### Get Product by ID
```http
GET /api/products/{id}
Authorization: Bearer <token>
```

#### Create Product (Admin Only)
```http
POST /api/products
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "title": "Clean Code",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "description": "A Handbook of Agile Software Craftsmanship",
  "price": 39.99,
  "stockQuantity": 100,
  "publisher": "Prentice Hall",
  "publicationYear": 2008,
  "language": "English",
  "pages": 464,
  "categoryId": 1
}
```

#### Update Product (Admin Only)
```http
PUT /api/products/{id}
Authorization: Bearer <admin-token>
Content-Type: application/json
```

#### Delete Product (Admin Only)
```http
DELETE /api/products/{id}
Authorization: Bearer <admin-token>
```

#### Search Products
```http
GET /api/products/search?keyword=clean&category=programming
Authorization: Bearer <token>
```

### 3. Cart Service (Port 8084)

#### Get Cart
```http
GET /api/cart
Authorization: Bearer <token>
```

#### Add Item to Cart
```http
POST /api/cart/items
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### Update Cart Item
```http
PUT /api/cart/items/{productId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 3
}
```

#### Remove Item from Cart
```http
DELETE /api/cart/items/{productId}
Authorization: Bearer <token>
```

#### Clear Cart
```http
DELETE /api/cart
Authorization: Bearer <token>
```

### 4. Wishlist Service (Port 8085)

#### Get Wishlist
```http
GET /api/wishlist
Authorization: Bearer <token>
```

#### Add to Wishlist
```http
POST /api/wishlist
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1
}
```

#### Remove from Wishlist
```http
DELETE /api/wishlist/{productId}
Authorization: Bearer <token>
```

### 5. Customer Details Service (Port 8086)

#### Get Customer Profile
```http
GET /api/customers/profile
Authorization: Bearer <token>
```

#### Add Address
```http
POST /api/customers/addresses
Authorization: Bearer <token>
Content-Type: application/json

{
  "addressLine1": "123 Main St",
  "addressLine2": "Apt 4B",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "country": "USA",
  "isDefault": true
}
```

#### Get All Addresses
```http
GET /api/customers/addresses
Authorization: Bearer <token>
```

#### Update Address
```http
PUT /api/customers/addresses/{id}
Authorization: Bearer <token>
```

#### Delete Address
```http
DELETE /api/customers/addresses/{id}
Authorization: Bearer <token>
```

### 6. Order Service (Port 8087)

#### Create Order
```http
POST /api/orders
Authorization: Bearer <token>
Content-Type: application/json

{
  "addressId": 1,
  "paymentMethod": "CREDIT_CARD",
  "items": [
    {
      "productId": 1,
      "quantity": 2
    },
    {
      "productId": 3,
      "quantity": 1
    }
  ]
}
```

#### Get Order by ID
```http
GET /api/orders/{id}
Authorization: Bearer <token>
```

#### Get User Orders
```http
GET /api/orders/user?page=0&size=10
Authorization: Bearer <token>
```

#### Get All Orders (Admin Only)
```http
GET /api/orders?page=0&size=10
Authorization: Bearer <admin-token>
```

#### Update Order Status (Admin Only)
```http
PUT /api/orders/{id}/status
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "status": "SHIPPED"
}
```

**Order Status Values:**
- PENDING
- CONFIRMED
- PROCESSING
- SHIPPED
- DELIVERED
- CANCELLED

### 7. Feedback Service (Port 8088)

#### Add Review
```http
POST /api/reviews
Authorization: Bearer <token>
Content-Type: application/json

{
  "productId": 1,
  "rating": 5,
  "comment": "Excellent book! Highly recommended."
}
```

#### Get Product Reviews
```http
GET /api/reviews/product/{productId}?page=0&size=10
Authorization: Bearer <token>
```

#### Update Review
```http
PUT /api/reviews/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "rating": 4,
  "comment": "Good book, but a bit outdated."
}
```

#### Delete Review
```http
DELETE /api/reviews/{id}
Authorization: Bearer <token>
```

#### Get User Reviews
```http
GET /api/reviews/user
Authorization: Bearer <token>
```

### 8. Admin Service (Port 8082)

#### Get Dashboard Stats
```http
GET /api/admin/dashboard
Authorization: Bearer <admin-token>
```

#### Get All Users
```http
GET /api/admin/users?page=0&size=10
Authorization: Bearer <admin-token>
```

#### Update User Role
```http
PUT /api/admin/users/{id}/role
Authorization: Bearer <admin-token>
Content-Type: application/json

{
  "role": "ADMIN"
}
```

#### Deactivate User
```http
PUT /api/admin/users/{id}/deactivate
Authorization: Bearer <admin-token>
```

#### Get Sales Report
```http
GET /api/admin/reports/sales?startDate=2024-01-01&endDate=2024-01-31
Authorization: Bearer <admin-token>
```

## Error Responses

All error responses follow this format:

```json
{
  "success": false,
  "message": "Error description",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/endpoint"
}
```

### Common HTTP Status Codes

- **200 OK**: Request successful
- **201 Created**: Resource created successfully
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Missing or invalid authentication
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

## Pagination

List endpoints support pagination with these query parameters:

- `page`: Page number (0-indexed, default: 0)
- `size`: Items per page (default: 10)
- `sort`: Sort field and direction (e.g., `title,asc`)

**Example:**
```http
GET /api/products?page=0&size=20&sort=price,desc
```

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [...],
    "totalElements": 100,
    "totalPages": 5,
    "size": 20,
    "number": 0
  }
}
```

## Kafka Events

The system publishes events to Kafka topics:

### user-events
```json
{
  "eventType": "user-registered",
  "email": "john@example.com",
  "timestamp": "2024-01-15T10:30:00"
}
```

### order-events
```json
{
  "eventType": "order-placed",
  "orderId": 123,
  "userId": 456,
  "totalAmount": 99.99,
  "timestamp": "2024-01-15T10:30:00"
}
```

### inventory-events
```json
{
  "eventType": "stock-updated",
  "productId": 1,
  "quantity": 50,
  "timestamp": "2024-01-15T10:30:00"
}
```

### review-events
```json
{
  "eventType": "review-added",
  "productId": 1,
  "rating": 5,
  "timestamp": "2024-01-15T10:30:00"
}
```

## Rate Limiting

API Gateway implements rate limiting:
- **Authenticated users**: 100 requests per minute
- **Unauthenticated**: 20 requests per minute

## Swagger UI

Interactive API documentation available at:
- **User Service**: http://localhost:8081/swagger-ui.html
- **Product Service**: http://localhost:8083/swagger-ui.html
- **Cart Service**: http://localhost:8084/swagger-ui.html
- **Order Service**: http://localhost:8087/swagger-ui.html
- **Feedback Service**: http://localhost:8088/swagger-ui.html

## Postman Collection

Import the Postman collection from `postman/BookStore-API.postman_collection.json` for easy testing.

---

For more details, visit the Swagger UI or check individual service documentation.
