# ReturnFlow

## Smart E-commerce Return, Inspection & Refund Management System

ReturnFlow is a full-stack web application designed to manage the complete
e-commerce product return lifecycle from return request to final refund.

## Features

- User Registration and Login
- JWT Authentication
- Role-Based Authorization
- Customer Dashboard
- Seller / Warehouse Dashboard
- Admin Dashboard
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

### Backend

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

### Frontend

- React
- Vite
- Axios
- React Router
- CSS

## Project Structure

```
ReturnFlow
│
├── returnflow-backend
│   ├── src
│   │   └── main
│   │       └── java
│   │           └── com.returnFlow
│   │               ├── config
│   │               ├── controller
│   │               ├── entity
│   │               ├── repository
│   │               ├── security
│   │               └── service
│   │
│   └── pom.xml
│
├── returnflow-frontend
│   ├── src
│   │   ├── pages
│   │   ├── services
│   │   └── App.jsx
│   │
│   └── package.json
│
└── README.md
```

## Authentication

ReturnFlow uses JWT-based authentication.

After successful login, the backend generates a JWT token.
The frontend stores the token and sends it with protected API requests.
```
Login
  ↓
JWT Token
  ↓
Frontend
  ↓
Authorization: Bearer <token>
  ↓
Spring Security
  ↓
Role-Based Access
```
## API Documentation

#### Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```
## Running the Backend
1. Create the MySQL database.
2. Configure database credentials in application.properties.
3. Open the backend project in IntelliJ IDEA.
4. Run the Spring Boot application.
5. Backend will start on:
```
http://localhost:8080
```
## Running the Frontend

Open the frontend folder in terminal:
```
npm install
```
Then:
```
npm run dev
```
Frontend will run on:
```
http://localhost:5173
```
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