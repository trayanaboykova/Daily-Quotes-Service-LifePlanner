# 🌟 Daily Quotes Microservice

I built this microservice as part of my journey to master the art of designing decoupled, RESTful services with Spring Boot. The Daily Quotes Microservice is a standalone application that provides CRUD operations for daily quote images. It is designed to be consumed by other applications (like LifePlanner) using a Feign client, ensuring seamless integration while maintaining independent deployment.

## 🚀 Project Overview  
This microservice focuses on offering a clean and robust API for managing daily quotes. It demonstrates key concepts such as Spring Data JPA for database interactions, Jakarta Bean Validation for ensuring data quality, and centralized exception handling for meaningful error responses. The service is lightweight, yet fully functional, supporting all basic operations needed to create, retrieve, update, and delete daily quote records.

## 🎯 What I Learned  
- 🏗️ Building standalone RESTful microservices with Spring Boot  
- 🔄 Effective use of Spring Data JPA for CRUD operations  
- 💬 Implementing input validation and error handling using Jakarta Bean Validation  
- 🔀 Creating clear and maintainable API endpoints  
- 📡 Integrating microservices with Feign Clients for seamless communication

## 🔧 Features  
- ✅ Create, view, update, and delete daily quote entries  
- ✅ Retrieve quotes by user ID  
- ✅ Input validation to enforce business rules  
- ✅ Centralized exception handling with meaningful HTTP error responses  
- ✅ Independently deployable with its own database configuration

## 📂 Project Structure  
- **Entities & Repositories:**  
  A dedicated `DailyQuote` entity paired with a repository for data persistence.
- **Service Layer:**  
  Encapsulates all business logic for managing daily quotes.
- **DTOs & Mappers:**  
  Custom Data Transfer Objects (DTOs) and mapper classes to convert between internal models and API data structures.
- **REST Controllers:**  
  Exposes endpoints under `/api/v1/daily-quotes` for external consumption.
- **Global Exception Handling:**  
  Centralized error management to provide consistent API responses.

## 🛠️ Technologies I Used  
[![Java](https://skillicons.dev/icons?i=java)](https://www.java.com/) [![Spring](https://skillicons.dev/icons?i=spring)](https://spring.io/) [![MySQL](https://skillicons.dev/icons?i=mysql)](https://www.mysql.com/)

## 🤔 How to Run  
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yourusername/daily-quotes-microservice.git
   cd daily-quotes-microservice
2. **Configure the Database:**
   ```bash
   Update src/main/resources/application.properties (or application.yml) with your database credentials.
3. **Build and Run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
3. **Access the API:**
   The service runs on http://localhost:8081 by default. Use Postman or your favorite REST client to test endpoints like:
   ```bash
   GET /api/v1/daily-quotes/user/{userId}
   POST /api/v1/daily-quotes
   PUT /api/v1/daily-quotes/{id}
   DELETE /api/v1/daily-quotes/{id}
   
## 📈 Learning Outcomes
Working on this microservice enhanced my understanding of designing independent, modular systems that interact seamlessly. I gained valuable experience in creating robust REST APIs, implementing validation and error handling, and integrating microservices using modern Spring technologies.
