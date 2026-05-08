# Quantity Measurement API Using Spring Boot

A RESTful Spring Boot application for performing quantity measurements and unit conversions. This API allows users to convert values between different units such as length, weight, volume, and temperature.

---

# Project Overview

The Quantity Measurement API is designed to handle unit conversion operations through REST endpoints. It follows a layered Spring Boot architecture with Controller, Service, DTO, Entity, Repository, and Exception Handling layers.

The project demonstrates:

- REST API development
- Spring Boot layered architecture
- DTO-based request and response handling
- Input validation
- Unit conversion logic
- Exception handling
- Database persistence
- API testing using Postman

---

# Tech Stack

| Technology | Purpose |
|---|---|
| Java | Programming language |
| Spring Boot | Backend framework |
| Spring Web | REST API development |
| Spring Data JPA | Database interaction |
| Hibernate | ORM |
| MySQL | Database |
| Maven | Dependency management |
| Postman | API testing |

---

# Features

- Convert quantity values from one unit to another
- Support multiple measurement types
- Validate user input
- Store conversion history in database
- Fetch previous measurement records
- Handle invalid units gracefully
- RESTful API design

---

# Supported Measurement Types

| Type | Supported Units |
|---|---|
| Length | meter, kilometer, centimeter, millimeter, inch, foot |
| Weight | kilogram, gram, milligram, pound |
| Volume | liter, milliliter, gallon |
| Temperature | celsius, fahrenheit, kelvin |

---

# Project Structure

```bash
quantity-measurement-api/
│
├── src/main/java/com/example/quantitymeasurement/
│   │
│   ├── controller/
│   │   └── QuantityController.java
│   │
│   ├── dto/
│   │   ├── ConversionRequestDTO.java
│   │   └── ConversionResponseDTO.java
│   │
│   ├── entity/
│   │   └── Measurement.java
│   │
│   ├── repository/
│   │   └── MeasurementRepository.java
│   │
│   ├── service/
│   │   ├── QuantityService.java
│   │   └── QuantityServiceImpl.java
│   │
│   ├── exception/
│   │   ├── InvalidUnitException.java
│   │   └── GlobalExceptionHandler.java
│   │
│   └── QuantityMeasurementApplication.java
│
├── src/main/resources/
│   └── application.properties
│
├── pom.xml
└── README.md
```

---

# API Endpoints

## 1. Convert Quantity

### Endpoint

```http
POST /api/quantity/convert
```

### Request Body

```json
{
  "type": "length",
  "value": 10,
  "fromUnit": "meter",
  "toUnit": "centimeter"
}
```

### Response

```json
{
  "success": true,
  "type": "length",
  "inputValue": 10,
  "fromUnit": "meter",
  "toUnit": "centimeter",
  "result": 1000
}
```

---

## 2. Get All Measurements

### Endpoint

```http
GET /api/quantity/all
```

### Response

```json
[
  {
    "id": 1,
    "type": "length",
    "inputValue": 10,
    "fromUnit": "meter",
    "toUnit": "centimeter",
    "result": 1000
  }
]
```

---

## 3. Get Measurement By ID

### Endpoint

```http
GET /api/quantity/{id}
```

### Example

```http
GET /api/quantity/1
```

---

## 4. Delete Measurement

### Endpoint

```http
DELETE /api/quantity/{id}
```

### Response

```json
{
  "message": "Measurement deleted successfully"
}
```

---

# Example Conversions

## Length

```json
{
  "type": "length",
  "value": 5,
  "fromUnit": "kilometer",
  "toUnit": "meter"
}
```

Result:

```json
{
  "result": 5000
}
```

---

## Weight

```json
{
  "type": "weight",
  "value": 2,
  "fromUnit": "kilogram",
  "toUnit": "gram"
}
```

Result:

```json
{
  "result": 2000
}
```

---

## Volume

```json
{
  "type": "volume",
  "value": 3,
  "fromUnit": "liter",
  "toUnit": "milliliter"
}
```

Result:

```json
{
  "result": 3000
}
```

---

## Temperature

```json
{
  "type": "temperature",
  "value": 100,
  "fromUnit": "celsius",
  "toUnit": "fahrenheit"
}
```

Result:

```json
{
  "result": 212
}
```

---

# Database Configuration

Add the following configuration in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quantity_db
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

---

# Database Table

## measurements

| Column | Description |
|---|---|
| id | Primary key |
| type | Measurement type |
| input_value | Original value |
| from_unit | Source unit |
| to_unit | Target unit |
| result | Converted value |
| created_at | Conversion timestamp |

---

# Validation Rules

The API validates:

- Measurement type must not be empty
- Value must be numeric
- Value must be greater than or equal to zero
- Source unit must not be empty
- Target unit must not be empty
- Source and target units must belong to the same measurement type
- Invalid units are rejected

---

# Exception Handling

The application handles errors using a global exception handler.

## Example Error Response

```json
{
  "success": false,
  "message": "Invalid unit for selected measurement type"
}
```

Common handled errors:

- Invalid measurement type
- Invalid source unit
- Invalid target unit
- Missing request fields
- Database errors
- Resource not found

---

# How to Run

## 1. Clone Repository

```bash
git clone https://github.com/your-username/quantity-measurement-api.git
```

## 2. Open Project

Open the project in:

- IntelliJ IDEA
- Eclipse
- VS Code

## 3. Create MySQL Database

```sql
CREATE DATABASE quantity_db;
```

## 4. Update Database Credentials

Edit:

```text
src/main/resources/application.properties
```

Update:

```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

## 5. Run Application

```bash
mvn spring-boot:run
```

Application will start at:

```text
http://localhost:8080
```

---

# Testing with Postman

## Convert Quantity

Method:

```text
POST
```

URL:

```text
http://localhost:8080/api/quantity/convert
```

Body:

```json
{
  "type": "length",
  "value": 10,
  "fromUnit": "meter",
  "toUnit": "centimeter"
}
```

---

# Sample Success Response

```json
{
  "success": true,
  "type": "length",
  "inputValue": 10,
  "fromUnit": "meter",
  "toUnit": "centimeter",
  "result": 1000
}
```

---

# Sample Failure Response

```json
{
  "success": false,
  "message": "Unsupported conversion type"
}
```

---

# Key Spring Boot Concepts Used

| Concept | Usage |
|---|---|
| REST Controller | Exposes API endpoints |
| Service Layer | Contains business logic |
| Repository Layer | Performs database operations |
| DTO | Transfers request/response data |
| Entity | Maps Java class to database table |
| JPA/Hibernate | ORM and persistence |
| Validation | Validates user input |
| Exception Handling | Handles errors globally |

---

# Future Enhancements

- Add Swagger/OpenAPI documentation
- Add user authentication
- Add conversion history filters
- Add pagination for history records
- Add unit tests using JUnit and Mockito
- Add Docker support
- Deploy API on cloud platform

---

# Author

Developed as a Spring Boot REST API project for quantity measurement and unit conversion.
