# Contact Management System - Windows Application


## Overview

The Contact Management System is a Windows desktop application developed using Core Java, Swing, and JDBC. It provides a user-friendly interface to manage and organize contact details efficiently. Users can perform CRUD operations (Create, Read, Update, Delete) to add, view, edit, and remove contact information. The application utilizes Java's Swing framework for the GUI and JDBC for seamless database connectivity with MySQL.

## Features

- Create and store contact details such as name, phone number, email, address, and date of birth.
- View the list of saved contacts with the ability to search and filter.
- Update contact information whenever required.
- Delete unwanted contacts from the database.
- Robust error handling and user-friendly error messages.

## Getting Started

Follow the steps below to run the Contact Management System on your local machine:

### Prerequisites

- JDK (Java Development Kit) installed on your machine.
- MySQL database server installed and running.

### Setup

1. Clone this repository to your local machine using the following command:

   ```
   git clone https://github.com/your-username/contact-management-system.git
   ```

2. Import the project into your favorite Java IDE (e.g., Eclipse, IntelliJ).

3. Set up the database:

   - Create a new database in MySQL named `contact`.
   - Create a table named `contacts` with the following schema:

     ```
     CREATE TABLE contacts (
       id INT PRIMARY KEY AUTO_INCREMENT,
       name VARCHAR(100) NOT NULL,
       phone_number BIGINT NOT NULL,
       email VARCHAR(100),
       address VARCHAR(200),
       date_of_birth DATE,
       tag VARCHAR(50)
     );
     ```

4. Configure the database connection in the project:

   - Open the `Database.java` file and replace the following values with your MySQL database credentials:

     ```java
     String url = "jdbc:mysql://localhost:3306/contact";
     String username = "your-username";
     String password = "your-password";
     ```

5. Build and run the project from your IDE.

## Usage

1. Upon running the application, the main window will open, displaying four buttons for Create, Read, Update, and Delete operations.

2. Click the "Create" button to add a new contact. A new window will appear, prompting you to enter contact details. After filling in the information, click the "OK" button to save the contact.

3. To view the list of saved contacts, click the "Read" button. The application will display the contacts in a table format, allowing you to search and filter the contact list.

4. To update a contact, select the contact from the list and click the "Update" button. A window will appear, allowing you to modify the contact details. After making the necessary changes, click the "OK" button to update the contact.

5. To delete a contact, select the contact from the list and click the "Delete" button. A confirmation dialog will appear to ensure the deletion. Click the "Yes" button to remove the contact from the database.

## Contributions

Contributions to this Contact Management System project are welcome. If you find any issues or have suggestions for improvement, feel free to submit a pull request or open an issue.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

Special thanks to the open-source community for providing helpful resources and libraries that made this project possible.

---

We hope you find this Contact Management System helpful for your organizational needs. Please don't hesitate to contact us if you have any questions or feedback. Happy organizing!
