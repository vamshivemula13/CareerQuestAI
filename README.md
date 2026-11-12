# 🚀 Career Quest AI

> An AI-powered recruitment platform that connects job seekers with the right opportunities and helps recruiters hire faster.

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![React](https://img.shields.io/badge/React-18-61DAFB?logo=react)
![MySQL](https://img.shields.io/badge/MySQL-8-blue?logo=mysql)
![License](https://img.shields.io/badge/License-MIT-lightgrey)



---

## 📌 Overview

Career Quest AI is a full-stack recruitment platform. Job seekers can build profiles, upload resumes, search and apply for jobs, and get AI-assisted job recommendations. Recruiters can post jobs, review applicants, and manage the hiring pipeline from one dashboard.

## ✨ Features

### For Job Seekers
- Secure registration and login (JWT authentication)
- Profile and resume management
- Search and filter jobs by role, location, and skills
- One-click job applications and application status tracking
- AI-based job recommendations *(update to match your implementation)*

### For Recruiters
- Post, edit, and close job listings
- View and shortlist applicants
- Candidate profile and resume viewing
- Application management dashboard

### General
- Role-based access control (Job Seeker / Recruiter / Admin)
- RESTful API architecture
- Responsive UI

## 🛠️ Tech Stack

| Layer | Technologies |
|-------|--------------|
| **Frontend** | React.js, HTML5, CSS3, JavaScript, Axios |
| **Backend** | Java, Spring Boot, Spring Security, Spring Data JPA, Hibernate |
| **Database** | MySQL |
| **Tools** | Maven, Git, GitHub, Postman, IntelliJ IDEA / VS Code |

## 🏗️ Architecture

```
React Frontend  →  Spring Boot REST API  →  Service Layer  →  JPA/Hibernate  →  MySQL
                         ↑
                  Spring Security + JWT
```

## 📂 Project Structure

```
career-quest-ai/
├── backend/
│   ├── src/main/java/com/careerquest/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── security/
│   │   └── config/
│   ├── src/main/resources/
│   │   └── application.properties
│   └── pom.xml
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   └── services/
│   └── package.json
└── README.md
```

> Adjust folder names to match your actual repository.

## ⚙️ Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- Node.js 18+ and npm
- MySQL 8+

### 1. Clone the repository
```bash
git clone https://github.com/vamshivemula13/career-quest-ai.git
cd career-quest-ai
```

### 2. Set up the database
```sql
CREATE DATABASE careerquest;
```

### 3. Configure the backend
Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/careerquest
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY
jwt.expiration=86400000
```

### 4. Run the backend
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
Backend runs at `http://localhost:8080`

### 5. Run the frontend
```bash
cd frontend
npm install
npm start
```
Frontend runs at `http://localhost:3000`

## 🔌 API Endpoints (Sample)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT |
| GET | `/api/jobs` | List all jobs |
| GET | `/api/jobs/{id}` | Get job details |
| POST | `/api/jobs` | Create a job (Recruiter) |
| POST | `/api/jobs/{id}/apply` | Apply for a job |
| GET | `/api/applications/my` | View my applications |

> Update this table to match your actual endpoints.

## 📸 Screenshots

| Home | Job Search | Recruiter Dashboard |
|------|-----------|---------------------|
| ![Home](screenshots/home.png) | ![Jobs](screenshots/jobs.png) | ![Dashboard](screenshots/dashboard.png) |

*(Add screenshots to a `/screenshots` folder.)*

## 🔮 Future Enhancements
- Resume parsing and skill extraction
- Smarter AI-based candidate matching
- Email notifications for application updates
- Interview scheduling
- Docker deployment and CI/CD pipeline

## 🤝 Contributing

Contributions are welcome!
1. Fork the repository
2. Create a branch: `git checkout -b feature/your-feature`
3. Commit your changes: `git commit -m "Add your feature"`
4. Push: `git push origin feature/your-feature`
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**Vemula Vamshi Krishna**
Java Full Stack Developer

- LinkedIn: [vamshikrishnavemula](https://www.linkedin.com/in/vamshikrishnavemula)
- GitHub: [vamshivemula13](https://github.com/vamshivemula13)
  

⭐ If you found this project useful, consider giving it a star!
