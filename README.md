# Course Compass

Course Compass is a web application that allows users to browse courses, add courses to their personal list, and manage their course plan. The application is built using Spring Boot, Thymeleaf, HTML, CSS, JavaScript and MySQL.

## Features

- User registration and login
- Browse and search courses
- Add courses to personal list
- View and manage personal courses

## Prerequisites

- Java 17 or higher
- Maven 3.6.3 or higher
- MySQL 8.0 or higher
- XAMPP or any other tool to manage MySQL databases (phpMyAdmin)

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/your-username/course-compass.git
cd course-compass
```

### Setup the Database

Ensure XAMPP is running, specifically the Apache and MySQL services. You can start XAMPP using the XAMPP Control Panel. Open phpMyAdmin and create a database course_compass. Then create the necessary tables:

WILL PROVIDE NEARING SPRINT RELEASE

### Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Open the web browser and navigate to `https:localhost:8080` to access the application.

## Important to Note

- MySQL must be running and the details for it in application.properties must be correct
- All dependencies must be installed you can use the ``mvn clean install`` in the case that it is highlighted, indicating a dependency wasn't installed
- You might need to re-load the IDE
- If you wish to use this for another institution, you will need to adjust the WebScraping python file to match the structure of the page you are obtaining the course data from OR if you happen to have a JSON-file following the same structure you could just use that.
- Ensure project files are in the correct locations, if you move files around, ensure you refactor changes if necessary
