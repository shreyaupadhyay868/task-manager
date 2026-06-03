# Task Manager REST API

A production-grade REST API built with Spring Boot 3, featuring JWT authentication, role-based access control, and full task management capabilities.

## Tech Stack

- **Java 25** + **Spring Boot 3.2.5**
- **Spring Security** + **JWT** (JJWT 0.12.x)
- **PostgreSQL** + **Spring Data JPA** / **Hibernate**
- **Swagger / OpenAPI 3** (Springdoc)
- **Docker** + **Docker Compose**
- **Lombok**, **Maven**

## Features

-  JWT Authentication (register, login)
-  Role-based access control (USER / ADMIN)
- Full Task CRUD (create, read, update, delete)
-  Filtering by status and priority
-  Sorting and pagination
-  Swagger UI with Bearer token support
-  Dockerized with PostgreSQL container

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL (or Docker)

### Run locally

1. Clone the repo
```bash
   git clone https://github.com/shreyaupadhyay868/task-manager.git
   cd task-manager
```

2. Create a PostgreSQL database
```sql
   CREATE DATABASE taskdb;
```

3. Update `src/main/resources/application.properties`
```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
   spring.datasource.username=postgres
   spring.datasource.password=yourpassword
```

4. Run the app
```bash
   mvn spring-boot:run
```

### Run with Docker

```bash
docker-compose up --build
```

## API Endpoints

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### Tasks (requires Bearer token)
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/tasks` | Create a task |
| GET | `/api/tasks` | Get all tasks (paginated) |
| GET | `/api/tasks/{id}` | Get task by ID |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |

### Filtering & Pagination

GET /api/tasks?status=TODO&priority=HIGH&page=0&size=10&sort=dueDate,asc

## Swagger UI

Once the app is running, visit:
http://localhost:8080/swagger-ui/index.html

Click **Authorize** and enter your JWT token to test protected endpoints.

## Role-Based Access

| Role | Permissions |
|---|---|
| USER | Create tasks, view/edit/delete own tasks only |
| ADMIN | Full access to all tasks |

## Project Structure

src/main/java/com/example/task_manager/
├── controller/       # REST controllers
├── dto/              # Request/Response DTOs
├── entity/           # JPA entities
├── enums/            # Role, TaskStatus, Priority
├── repository/       # Spring Data repositories
├── security/         # JWT filter, config, Swagger
└── service/          # Business logic

