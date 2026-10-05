# ReturnFlow

## Smart E-commerce Return, Inspection & Refund Management System

ReturnFlow is a Spring Boot REST API designed to manage the complete e-commerce product return lifecycle from return request to final refund.

## Features

- User Registration and Login
- JWT Authentication
- Role-Based Authorization
- Product Management
- Order Management
- Order Delivery Management
- Return Request Management
- Return Approval / Rejection
- Pickup Scheduling
- Product Receipt Management
- Product Quality Inspection
- Refund Management
- Customer Dispute Management
- Return Status Tracking
- Swagger API Documentation

## Return Lifecycle

Order Placed
→ Order Delivered
→ Return Requested
→ Return Approved
→ Pickup Scheduled
→ Product Received
→ Product Inspection
→ Refund Initiated
→ Refund Completed

## User Roles

### Customer

- Register and login
- Browse products
- Place orders
- Request product returns
- Track return status
- Raise disputes

### Seller / Warehouse Staff

- View customer orders
- Mark orders as delivered
- Approve or reject returns
- Schedule pickups
- Mark returned products as received
- Inspect returned products
- Initiate refunds
- Complete refunds
- Review customer disputes

### Admin

- Manage users
- View products
- Review customer disputes
- Update dispute status

## Technology Stack

- Java 24
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- REST APIs
- MySQL
- Maven
- Lombok
- Swagger / OpenAPI

## Project Structure

```text
ReturnFlow
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.returnFlow
│   │   │       ├── config
│   │   │       ├── controller
│   │   │       ├── dto
│   │   │       ├── entity
│   │   │       ├── repository
│   │   │       ├── security
│   │   │       └── service
│   │   │
│   │   └── resources
│   │
│   └── test
│
├── mvnw
├── mvnw.cmd
└── README.md
```

## Authentication

ReturnFlow uses JWT-based authentication.

After successful login, the backend generates a JWT token. The token is used to authenticate protected REST API requests.

```text
Login
  ↓
JWT Token
  ↓
Authorization: Bearer <token>
  ↓
Spring Security
  ↓
Role-Based Access
```

## API Documentation

Swagger UI is available at:

```text
http://localhost:8080/swagger-ui/index.html
```

Swagger provides interactive documentation for the available REST APIs.

## Main API Modules

| Module | Endpoint |
|---|---|
| Authentication | `/api/auth` |
| Users | `/api/users` |
| Products | `/api/products` |
| Orders | `/api/orders` |
| Order Items | `/api/order-items` |
| Returns | `/api/returns` |
| Inspections | `/api/inspections` |
| Refunds | `/api/refunds` |
| Disputes | `/api/disputes` |

## Security

The application uses:

- JWT Authentication
- BCrypt Password Encoding
- Role-Based Authorization
- Protected REST APIs
- Spring Security Method-Level Authorization
- Stateless Session Management

## Running the Backend

### 1. Create MySQL Database

Create the required MySQL database for the application.

### 2. Configure Application Properties

Create/configure:

```text
src/main/resources/application.properties
```

Add your database and JWT configuration.

> `application.properties` is excluded from GitHub using `.gitignore` because it contains environment-specific and sensitive configuration.

### 3. Open the Project

Open the ReturnFlow backend project in IntelliJ IDEA.

### 4. Run the Application

Run:

```text
ReturnFlowApplication.java
```

The backend will start on:

```text
http://localhost:8080
```

## Testing

The REST APIs can be tested using:

- Swagger UI
- Postman

## Future Enhancements

- Image upload for return evidence
- Search and filtering
- Pagination
- Email notifications
- Payment gateway integration
- Docker deployment
- AWS deployment
- Production database configuration

## Author

Sakshi Bhagat
