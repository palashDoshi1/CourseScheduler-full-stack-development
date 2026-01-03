# Course Scheduler – Full Stack Java Application

## Project Overview
Course Scheduler is a full-stack Java desktop application designed to manage academic scheduling workflows.  
It provides an intuitive graphical interface for administrators and students to handle semesters, courses, classes, and enrollments efficiently.

The application supports semester-based scheduling, course creation, student enrollment management, and class roster viewing.  
All data is stored persistently using an embedded Apache Derby relational database.

---

## Features
- Role-based interface for Admin and Student users
- Semester creation and management
- Course and class management
- Student enrollment and drop functionality
- Class roster display
- Persistent data storage using Apache Derby
- Desktop GUI built using Java Swing (NetBeans Form Editor)

---

## Tech Stack
- **Programming Language:** Java  
- **UI Framework:** Java Swing  
- **IDE:** Apache NetBeans  
- **Database:** Apache Derby (Embedded)  
- **Build Tool:** Apache Ant  
- **Version Control:** Git & GitHub  

---

## Project Structure
CourseScheduler-full-stack-development/
│
├── src/                 # Java source files
├── nbproject/           # NetBeans project configuration
├── build.xml            # Ant build file
├── manifest.mf          # Project metadata
├── .gitignore           # Ignored files configuration

---

## How to Run the Project in NetBeans

### Prerequisites
- Java JDK 8 or later
- Apache NetBeans IDE
- Apache Derby (bundled with NetBeans or installed separately)

---

### Steps to Open and Run
1. Open **Apache NetBeans**
2. Click **File → Open Project**
3. Select the project directory:

CourseSchedulerPalashDoshipjd

4. Click **Open Project**
5. NetBeans will automatically load the Ant build configuration

---

## Database Setup (Apache Derby)

### Using NetBeans Built-in Derby (Recommended)
1. In NetBeans, navigate to **Services → Databases**
2. Start **Java DB (Derby) Server**
3. The application automatically initializes required tables if they do not exist
4. Database connection logic is implemented in:

src/DBConnection.java

### Database Connection String
```java
jdbc:derby:CourseSchedulerDB;create=true

Running the Application
	1.	Right-click the project in NetBeans
	2.	Select Run
	3.	The main application window (MainFrame) will launch
	4.	Use the Admin interface to manage semesters, courses, classes, and students

⸻

Notes
	•	Database files are intentionally excluded from version control
	•	The application creates required tables automatically on first run
	•	Derby binaries and ZIP files are not included by design

⸻

Author

Palash Doshi


