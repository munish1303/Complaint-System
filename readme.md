# Online Complaint Registration and Tracking System

**Course:** UCS2601 Internet Programming  
**Author:** Munish Madhav M  
---

## 1. Project Overview

The Online Complaint Registration and Tracking System is a full-stack web application designed to streamline the grievance redressal process. It facilitates interaction between users and administrators by allowing users to register complaints and track their status in real-time. Simultaneously, it provides administrators with a secure dashboard to view, analyze, and resolve complaints efficiently.

This solution is developed in compliance with the functional requirements of the UCS2601 Internet Programming assignment, focusing on secure data handling, session management, and efficient client-server interaction.

### Key Features
* **User Module:** Complaint registration with input validation, unique Complaint ID generation, and real-time status tracking.
* **Admin Module:** Secure authentication, dashboard for complaint management, and status update workflow.
* **Security:** Session management to protect administrative routes and JDBC PreparedStatement implementation to prevent SQL injection.
* **Duplicate Prevention:** Logic to detect and restrict duplicate complaint submissions.

---

## 2. Technology Stack

* **Frontend:** HTML5, CSS3, JavaScript (Client-side validation).
* **Backend:** Java Servlets (Jakarta EE), JSP.
* **Database:** MySQL (Relational Database Management System).
* **Server:** Apache Tomcat 10.0+ (Required for Jakarta Servlet API support).
* **Build Tool:** Maven.

---

## 3. Directory Structure and File Descriptions

This section outlines the purpose of each file within the application architecture.

### Java Source Files (`src/main/java/com/complaint/`)

#### **Package: `com.complaint.util`**
* **`DBConnection.java`**: A singleton utility class that manages the JDBC connection to the MySQL database. It creates and returns a `Connection` object using the configured credentials.

#### **Package: `com.complaint.servlet`**
* **`RegisterServlet.java`**: Handles HTTP POST requests from the complaint registration form. It performs server-side validation, checks for duplicate entries in the database, generates a unique Complaint ID, and persists the complaint data.
* **`LoginServlet.java`**: Manages admin authentication. It verifies credentials against the database and initializes an `HttpSession` for authenticated administrators.
* **`AdminDashboardServlet.java`**: A protected Servlet that renders the admin dashboard. It verifies the active session before retrieving and displaying a tabular view of all complaints.
* **`TrackServlet.java`**: Handles status tracking requests. It queries the database using a Complaint ID and displays the current status (Pending/In Progress/Resolved) and admin remarks.
* **`UpdateStatusServlet.java`**: Processes status updates submitted by the administrator. It modifies the complaint status and remarks in the database and redirects back to the dashboard.
* **`LogoutServlet.java`**: Handles the logout process by invalidating the current `HttpSession` and redirecting the administrator to the login page.

### Web Resources (`src/main/webapp/`)

* **`index.html`**: The landing page containing the Complaint Registration Form.
* **`login.html`**: The administrative login interface.
* **`track.html`**: A dedicated page for users to input their Complaint ID and view status.
* **`css/style.css`**: Contains all Cascading Style Sheets to ensure a consistent and responsive user interface.
* **`js/script.js`**: Contains JavaScript functions for client-side form validation (e.g., email format checks, mandatory field validation).
* **`WEB-INF/web.xml`**: Deployment descriptor (optional if using Servlet 3.0+ annotations).

---

## 4. Database Configuration

The application requires a MySQL database setup. Execute the following SQL script to initialize the schema.

```sql
CREATE DATABASE complaint_db;
USE complaint_db;

-- Table 1: Admin Credentials
CREATE TABLE admins (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL
);

-- Insert Default Admin (Username: admin, Password: admin123)
INSERT INTO admins (username, password) VALUES ('admin', 'admin123');

-- Table 2: Complaints
CREATE TABLE complaints (
    id INT AUTO_INCREMENT PRIMARY KEY,
    complaint_id VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'Pending',
    remarks TEXT,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

```
> **Note:** Update the `DBConnection.java` file with your local MySQL username and password before building the project.

## 5. Installation and Execution Instructions

### Prerequisites
* **Java Development Kit (JDK):** Version 11 or higher.
* **Apache Tomcat:** Version 10.0 or higher (Essential for Jakarta EE compatibility).
* **MySQL Server**
* **Maven**

### Steps to Run

1.  **Clone the Repository**
    ```bash
    git clone [https://github.com/techieRahul17/OnlineComplaintSystem.git](https://github.com/techieRahul17/OnlineComplaintSystem.git)
    ```

2.  **Configure Database**
    * Open **MySQL Workbench** or **Command Line**.
    * Run the SQL script provided in Section 4.
    * Update `src/main/java/com/complaint/util/DBConnection.java` with your specific database credentials.

3.  **Build the Project**
    * Open the project in an IDE (**VS Code**, **Eclipse**, or **IntelliJ**).
    * Run the following Maven command in the terminal to generate the WAR file:
    ```bash
    mvn clean package
    ```

4.  **Deploy to Server**
    * Copy the generated WAR file from the `target/` directory.
    * Paste it into the `webapps/` folder of your **Apache Tomcat** installation directory.
    * Start the Tomcat server:
        * **Windows:** Run `bin/startup.bat`
        * **Mac/Linux:** Run `bin/startup.sh`

5.  **Access the Application**
    * Open a web browser and navigate to:
        `http://localhost:8080/OnlineComplaintSystem/`

## 6. Security and Validation Measures

* **Client-Side Validation:** JavaScript ensures that no empty forms are submitted and that email addresses follow standard formats.
* **Server-Side Validation:** Servlets perform redundant checks to ensure data integrity before database insertion.
* **Session Management:** The `AdminDashboardServlet` implements a strict session check using `request.getSession(false)`. Unauthenticated access attempts are automatically redirected to the login page.
* **SQL Injection Prevention:** All database queries utilize `PreparedStatement` to separate code from data, ensuring secure database interactions.
