# 📚 Learning Management System (LMS)

A full-featured web platform designed to facilitate online learning. This system enables **Admins**, **Instructors**, 
and **Students** to interact and manage learning content efficiently. It supports role-based access control, secure 
login with JWT, media uploads, and a dynamic, responsive frontend interface.

---

## 🚀 Features

### ✅ Authentication and Authorization
- Secure login and signup using JWT.
- Role-based access: Admins, Instructors, and Students have access to specific features.

### ✅ Admin Module
- Manage all users and courses.
- View, update, or delete users and courses.
- Approve or remove courses created by instructors.

### ✅ Instructor Module
- Create and manage courses.
- Add lessons to courses with text, videos, or PDFs.
- View students enrolled in courses.

### ✅ Student Module
- Browse and enroll in courses.
- Access course materials and lessons.
- Track learning progress.

### ✅ Course and Lesson Management
- Each course has a title, description, and multiple lessons.
- Lessons can include text, video, or PDF content.

### ✅ Enrollment Module
- Students can enroll in courses.
- Duplicate enrollments are prevented.

### ✅ Media Upload Module
- Upload course-related files like videos, PDFs, images, or audio.
- Supports AWS S3, Uploadcare, and Firebase Storage.

---

## 🛠 Tech Stack

**Frontend:**
- Thymeleaf with Tailwind CSS

**Backend:**
- Spring Boot 3, Spring Security, JWT, Spring Data JPA

**Database:**
- MySQL

**Media Storage:** Feature not built yet
- Firebase Storage

**API Documentation:**
- OpenAPI / Swagger

**Deployment:**
- Deployed on Digital Ocean

---

## ✅ Testing

- Unit tests with **JUnit 5** and **Mockito** for backend services (Controller, Service, Repository)
- End-to-end testing for user workflows

---

## 📦 Installation

### Prerequisites
- Java 17+
- MySQL
- Maven for backend dependency management

### Steps

1. Clone the repository:
    ```bash
    git clone <repository-url>
    cd lms
    ```

2. Set up the backend:
    - Configure `application.properties` with database and storage credentials.
    - Run the backend server:
    ```bash
    mvn spring-boot:run
    ```

3. Set up the frontend:
    ```bash
    cd frontend
    npm install
    npm start
    ```

4. Open the application in your browser:
    ```
    http://localhost:3000
    ```

---

## 🚀 Deployment
```bash
./mvnw package spring-boot:repackage -DskipTests
```

```bash
java -jar targer/*.jar > app.log
```

---

## 📂 Folder Structure

```angular2html
/backend
├── src
├   └── main
├       ├── java
├       ├   └── com.sarankirthic.lms
├       ├       └── code
├       └── resources
├           ├── static
├           ├── templates
├           └── application.properties
└── pom.xml
```

---

## 🔑 Environment Variables

### Backend (`application.properties`)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/lms
spring.datasource.username=root
spring.datasource.password=yourpassword

jwt.secret=your_jwt_secret
storage.aws.access-key=your-access-key
storage.aws.secret-key=your-secret-key

```

---

## 📜 License

This project is licensed under the MIT License.