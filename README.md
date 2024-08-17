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
- 🐬 MySQL 8.0 or higher
- 🔧 XAMPP or any other tool to manage MySQL databases (phpMyAdmin)
- 📦 Postman if you want to test out the APIs

## 🚀 Getting Started

### 🔧 Clone the Repository

```bash
git clone https://github.com/your-username/course-compass.git
cd course-compass
```

### 🗄️ Setup the Database

Ensure XAMPP is running, specifically the Apache and MySQL services. You can start XAMPP using the XAMPP Control Panel. Open phpMyAdmin and create a database course_compass. Then create the necessary tables:

#### User Table
```
Name      | Type          | Null | Default
id        | bigint(20)    | No   | None     -->  Auto_Incremenet
username  | varchar(255)  | No   | None
password  | varchar(255)  | No   | None
dob       | datetime(6)   | No   | None
email     | varchar(255)  | No   | None 
name      | varchar(255)  | NO   | None
```

#### Course Table
```
Name                | Type          | Null  | Default
id                  | bigint(20)    | No    | None     -->  Auto_Incremenet
course_code         | varchar(255)  | Yes   | NULL
course_description  | text          | Yes   | NULL
course_name         | varchar(255)  | Yes   | NULL
course_program      | varchar(255)  | Yes   | NULL
```

#### MyCourses Table
```
Name                | Type          | Null   | Default
user_id             | bigint(20)    | Yes    | NULL 
course_code         | varchar(255)  | No     | None
course_description  | text          | No     | None
course_name         | varchar(255)  | No     | None
course_program      | varchar(255)  | No     | None
```

#### Timetable Table
```
Name         | Type          | Null | Default
user_id      | bigint(20)    | No   | None    
year         | varchar(255)  | No   | None
semester     | text          | No   | None
course_name  | varchar(255)  | No   | None
```

### 🏗️ Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

Open the web browser and navigate to `https:localhost:8080` to access the application.

## ✨ View of the Application

![image](https://github.com/user-attachments/assets/32faa259-5016-4503-82e9-4c90b6f96cec)

![image](https://github.com/user-attachments/assets/5e0b9be4-1365-4fa5-b478-3ad2fdcdd503)

![image](https://github.com/user-attachments/assets/9e7a428c-aac5-477e-9ca1-1d10e521dd4a)

![image](https://github.com/user-attachments/assets/7f6562b8-cc8d-4e6c-bb17-c1a20af4db0c)


## 🛠️ Important to Note 📌

- MySQL must be running and the details for it in application.properties must be correct
- All dependencies must be installed you can use the ``mvn clean install`` in the case that it is highlighted, indicating a dependency wasn't installed
- You might need to re-load the IDE
- If you wish to use this for another institution, you will need to adjust the WebScraping python file to match the structure of the page you are obtaining the course data from OR if you happen to have a JSON-file following the same structure you could just use that.
- Ensure project files are in the correct locations, if you move files around, ensure you refactor changes if necessary

## Debugging
If not running at all, possibly due to XAMPP and phpMyAdmin (based on my experience) and if that's the case the following should help:
```
Rename folder mysql/data to mysql/data_old
Make a copy of mysql/backup folder and name it as mysql/data
Copy all your database folders from mysql/data_old into mysql/data (except mysql, performance_schema, and phpmyadmin folders)
Copy mysql/data_old/ibdata1 file into mysql/data folder
Start MySQL from XAMPP control panel, If it is started already then restart it.
```
