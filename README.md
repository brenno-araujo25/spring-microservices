# Spring Boot Microservices: User & Email

This project is a **microservices-based system** using **Spring Boot**, designed to manage users and email notifications. It utilizes **Docker, RabbitMQ and PostgreSQL**.

## Features
- **User Service**: Handles user registration.
- **Email Service**: Listens to RabbitMQ and sends transactional emails.
- **Containerized Deployment**: Using **Docker & Docker Compose**.
- **Asynchronous Messaging**: RabbitMQ via **CloudAMQP**.
- **Unit Testing**: JUnit & Mockito.
- **Database**: PostgreSQL.
- **Documentation**: OpenAPI (Swagger UI)

## Technologies Used
- **Java 21**
- **Spring Boot 3+** (Spring Web, Spring Data JPA, Spring AMQP)
- **PostgreSQL** (via Docker)
- **RabbitMQ** (via CloudAMQP)
- **Docker & Docker Compose**
- **JUnit & Mockito** (for testing)
- **Swagger/OpenAPI** (for API documentation)
- **Maven** (for dependency management)

---
## Installation & Setup

### **Clone the Repository**
```sh
 git clone https://github.com/brenno-araujo25/spring-microservices.git
 cd spring-microservices
```

### **Create the `.env` file** in the project root:
```sh
USER_DB_URL=jdbc:postgresql://user-db:5432/user
USER_DB_USERNAME=postgres
USER_DB_PASSWORD=postgres

EMAIL_DB_URL=jdbc:postgresql://email-db:5432/email
EMAIL_DB_USERNAME=postgres
EMAIL_DB_PASSWORD=postgres

RABBITMQ_URL=your-cloudamqp-url
RABBITMQ_QUEUE_NAME=default.email

SPRING_MAIL_HOST=your-spring-mail-host
SPRING_MAIL_PORT=587
SPRING_MAIL_USERNAME=your-spring-mail-username
SPRING_MAIL_PASSWORD=your-spring-mail-password
```

### **Start the Services with Docker**
```sh
 docker-compose up --build
```

This will start:
- **User Service** on `http://localhost:8081`
- **Email Service** on `http://localhost:8082`
- **PostgreSQL** on port `5432` (user-db) and `5433` (email-db)
- **RabbitMQ** on `CloudAMQP`

### **Access Swagger Documentation**
User Service: http://localhost:8081/swagger-ui.html

---
## API Endpoints (User Service)
| Method   | Endpoint       | Description                  | Request Params / Body                  |
|----------|----------------|------------------------------|----------------------------------------|
| `POST`   | `/users`       | Register a new user          | `@RequestBody UserDTO userDTO`         |
| `GET`    | `/users`       | Find all users               | None                                   |
| `GET`    | `/users/email` | Find user by email           | `@RequestParam String email`           |
| `GET`    | `/users/{id}`  | Find user by id              | `@PathVariable UUID id`                |
| `GET`    | `/users/exists`| Check if user exists by email| `@RequestParam String email`           |

