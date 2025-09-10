# 🎬 BookMyShow Backend

A complete backend implementation of a **movie ticket booking system** inspired by BookMyShow.  
Built with **Java Spring Boot**, **JPA (Hibernate)**, and **MySQL**, this project provides REST APIs for managing **users, movies, theaters, shows, and bookings** with seat availability and booking validations.

> [Database Diagram](https://dbdiagram.io/d/68bddd2b61a46d388edaa6da)

---

## 🚀 Features
- 👤 **User Management** – Register and manage users  
- 🎥 **Movie Management** – Add, update, and list movies  
- 🏛️ **Theater & Show Management** – Map movies to theaters with showtimes  
- 🎟️ **Ticket Booking** – Book seats with validation to avoid double booking  
- 💳 **Payment Simulation** – Simulated flow for confirming bookings  
- 🔐 **Validation & Error Handling** – Prevent invalid operations  
- 📑 **RESTful APIs** – Clean and structured endpoints  

---

## 🛠️ Tech Stack
- **Language:** Java  
- **Framework:** Spring Boot  
- **ORM:** Spring Data JPA (Hibernate)  
- **Database:** MySQL  
- **Build Tool:** Maven  
- **API Documentation:** Swagger / OpenAPI  

---

## 📊 Database Design
Entities included in the system:
- **User**  
- **Movie**  
- **Theater**  
- **Show**  
- **Seat**  
- **Booking**  

---

## ⚡ API Endpoints (Sample)
### 🎥 Movies
- `POST /api/movies` → Add a new movie  
- `GET /api/movies` → Get all movies  
- `GET /api/movies/{id}` → Get movie by ID  

### 🏛️ Theaters & Shows
- `POST /api/theaters` → Add a theater  
- `POST /api/shows` → Add a show to a theater  
- `GET /api/shows/{theaterId}` → List shows for a theater  

### 🎟️ Bookings
- `POST /api/bookings` → Book tickets  
- `GET /api/bookings/{userId}` → Get all bookings of a user  

---

## 🛠️ Setup & Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/Gautam-aman/BookMyShow-Backend.git
   cd BookMyShow-Backend

   ## 🛠️ Setup & Installation

### 1. Configure MySQL Database
- Create a database in MySQL (e.g., `bookmyshow_db`).
- Update the `application.properties` file with your MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bookmyshow_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
