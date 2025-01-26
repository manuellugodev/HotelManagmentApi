# Hotel Management API

Welcome to the **Hotel Management API**! This Spring Boot-based RESTful API powers the Hotel Client Android App and provides backend functionality for managing hotel reservations, user authentication, and more. Below, you'll find the essential details about this project, including setup, architecture, and endpoints.

---

## Features

- **User Management**: Secure authentication and user role-based access control.
- **Reservation System**: Create, update, and cancel hotel reservations.
- **Reports**: Generate reports for reservations and user activity.

---

## Tech Stack

### Languages and Frameworks
- **Java**: Primary programming language.
- **Spring Boot**: Framework for building the API.
- **Spring Security**: For securing endpoints and managing authentication and authorization.
- **Hibernate**: ORM for database management.
- **PostgreSQL**: Database for storing application data.
- **JWT**: Secure authentication using JSON Web Tokens.

### Architecture
This project follows a **Clean Architecture** design to ensure scalability and maintainability:

- **Controller Layer**: Handles HTTP requests and responses.
- **Service Layer**: Contains business logic.
- **Repository Layer**: Interacts with the database.
- **Model Layer**: Defines the data structures and database entities.

---

## Prerequisites

Ensure you have the following installed before setting up the project:

- **Java 11 or higher**
- **Maven**: For managing dependencies and building the project.
- **PostgreSQL**: For database management.
- **Postman** (optional): For testing API endpoints.

---

## Setup Guide

1. Clone the repository:
   ```bash
   git clone https://github.com/manuellugodev/HotelManagmentApi.git
   ```

2. Navigate to the project directory:
   ```bash
   cd HotelManagmentApi
   ```

3. Configure the database:
   - Create a new PostgreSQL database (e.g., `hotel_management`).
   - Update the `application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:postgresql://localhost:5432/hotel_management
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```

4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the API:
   - Base URL: `http://localhost:8080`

---

## API Endpoints

### Authentication
- **POST** `/login`: Authenticate a user and return a JWT token.
- **POST** `/user/register`: Register a new user.

### Profile
- **GET** `/user/{username}`: Retrieve user profile by username.

### Appointments
- **POST** `/appointment`: Create a new appointment.
- **DELETE** `/appointment`: Cancel an appointment by ID.
- **GET** `/appointment`: Get all appointments.
- **GET** `/appointment?id={id}`: Get appointment details by ID.
- **GET** `/appointment/guest/{guestId}`: Retrieve appointments by guest ID.
- **GET** `/appointment/guest/{guestId}/upcoming`: Retrieve upcoming appointments for a guest.
- **GET** `/appointment/guest/{guestId}/past`: Retrieve past appointments for a guest.

### Rooms
- **GET** `/rooms`: Get available rooms based on guest count and dates.

---

## License

This project is licensed for **personal use only**. Commercial use, redistribution, or inclusion in paid services is strictly prohibited. By using this software, you agree to the following terms:

```
Copyright (c) 2025 Manuel Lugo

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to use
the Software for personal, non-commercial purposes only, subject to the following conditions:

- Commercial use, redistribution, and sublicensing of the Software are prohibited.
- This license must be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES, OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
```

