# 📚 Course Compass

Course Compass is a web application that allows users to browse courses, add courses to their personal list, and manage their course plan. The application is built using Spring Boot, Thymeleaf, and MySQL.

## ✨ Features

- 📝 User registration and login
- 🔍 Browse and search courses
- ➕ Add courses to personal list
- 📋 View and manage personal courses

## ⚙️ Prerequisites

- ☕ Java 17 or higher
- 🛠️ Maven 3.6.3 or higher
- 🐳 Docker with Docker Compose
- 📦 Postman if you want to test out the APIs

## 🚀 Getting Started

### 🔧 Clone the Repository

```bash
git clone https://github.com/your-username/course-compass.git
cd course-compass
```

### 🗄️ Start the Database

MySQL runs in Docker, so nothing needs to be installed or configured by hand. XAMPP and phpMyAdmin are not required.

```bash
docker compose up -d
```

This starts a MySQL 8.0 container and creates the `coursecompass` database. Data is kept in the `mysql-data` Docker volume, so it survives restarts. To wipe the database and start over:

```bash
docker compose down -v
```

The tables themselves are created by the application: on startup Spring Boot runs `src/main/resources/schema.sql` (`spring.sql.init.mode=always`), so no manual table creation is needed. The script uses `CREATE TABLE IF NOT EXISTS`, so existing data is preserved across restarts.

Connection settings live in `src/main/resources/application.properties` and match `docker-compose.yml`:

| Setting  | Default     |
|----------|-------------|
| Host     | `localhost` |
| Port     | `3306`      |
| Database | `coursecompass` |
| Username | `root`      |
| Password | `root`      |

They can be overridden with the `DB_URL`, `DB_USERNAME` and `DB_PASSWORD` environment variables.

### 🏗️ Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Open the web browser and navigate to `https://localhost:8080` to access the application.

### ✅ Running the Tests

```bash
mvn test
```

Tests use an in-memory H2 database in MySQL compatibility mode (see `src/test/resources/application.properties`), so Docker does not need to be running for them.

## ✨ View of the Application

### Home Page
![image](https://github.com/user-attachments/assets/2d6803d0-c4ac-4967-a101-d8f5e69c5441)

### Login Page
![image](https://github.com/user-attachments/assets/933599cd-dad7-4695-b1c0-4a620b67097a)

### Account Management
![image](https://github.com/user-attachments/assets/32faa259-5016-4503-82e9-4c90b6f96cec)

### Course Catalog
![image](https://github.com/user-attachments/assets/5e0b9be4-1365-4fa5-b478-3ad2fdcdd503)

### Course Cart
![image](https://github.com/user-attachments/assets/9e7a428c-aac5-477e-9ca1-1d10e521dd4a)

### Timetable Maker
![image](https://github.com/user-attachments/assets/a9ffdfda-3998-4c1e-aa87-931b47d66851)


## 🛠️ Important to Note 📌

- The MySQL container must be running before the application starts (`docker compose up -d`)
- All dependencies must be installed you can use the ``mvn clean install`` in the case that it is highlighted, indicating a dependency wasn't installed
- You might need to re-load the IDE
- If you wish to use this for another institution, you will need to adjust the WebScraping python file to match the structure of the page you are obtaining the course data from OR if you happen to have a JSON-file following the same structure you could just use that.
- Ensure project files are in the correct locations, if you move files around, ensure you refactor changes if necessary

## Debugging

If the application cannot connect to MySQL:

```bash
# Check that the container is running and healthy
docker compose ps

# Follow the MySQL logs
docker compose logs -f mysql
```

If the database was created with an incompatible schema, reset it and let the application recreate the tables:

```bash
docker compose down -v
docker compose up -d
```
