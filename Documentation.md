# Library Management System Documentation

## Table of Contents
1. [Project Overview](#project-overview)
2. [Features](#features)
3. [Technologies Used](#technologies-used)
4. [Database Schema](#database-schema)
5. [Setup and Installation](#setup-and-installation)
6. [Usage](#usage)
7. [Class Structure](#class-structure)
8. [Entity-Relationship Diagram](#entity-relationship-diagram)
9. [Contributing](#contributing)
10. [License](#license)

## Project Overview

The Library Management System is a JavaFX desktop application that allows library admins to manage books and users, while patrons can borrow and return books. The system tracks book availability and user transactions.

## Features

- **Admin Dashboard**:
    - Login with admin credentials.
    - Add new books.
    - Update book information.
    - Delete books.
    - Add new users.
    - View all users.

- **User Dashboard**:
    - Login with user credentials.
    - View available books.
    - Borrow books.
    - Return books.
    - View transaction history.

## Technologies Used

- **Java**: Core programming language.
- **JavaFX**: UI framework for building the desktop application.
- **MySQL**: Relational database management system.
- **JDBC**: Java Database Connectivity for database operations.
- **PlantUML**: For creating the ERD.

## Database Schema

```sql
CREATE TABLE book (
    bookid INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    isavailable BOOLEAN DEFAULT TRUE
);

CREATE TABLE patron (
    patronid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL
);

CREATE TABLE transaction (
    transactionid INT AUTO_INCREMENT PRIMARY KEY,
    bookid INT NOT NULL,
    patronid INT NOT NULL,
    date_borrowed DATE NOT NULL,
    date_returned DATE,
    FOREIGN KEY (bookid) REFERENCES book(bookid),
    FOREIGN KEY (patronid) REFERENCES patron(patronid)
);

INSERT INTO book (title, author, isbn, isavailable) VALUES
('The Complete Software Tester', 'Anthony Tekk', '1234567890123', TRUE),
('Java: Programming Basics for Absolute Beginners','Nathan Clark', '2345678901234', TRUE),
('Python Programming for Absolute Beginners', 'Robin Wieruch', '3456789012345', TRUE),
('The Road to React: The React.js with Hooks in JavaScript', 'Stephan Goericke', '4567890123456', TRUE),
('Retail Store Design Basics', 'Kristin Jackvony', '5678901234567', TRUE);

INSERT INTO patron (name, email, password, role) VALUES
('Admin User', 'admin@example.com', 'adminpass', 'admin'),
('User One', 'user1@example.com', 'user1pass', 'user'),
('User Two', 'user2@example.com', 'user2pass', 'user');
```

## Usage
### Admin Dashboard
- **Login:** Use email admin@example.com and password adminpassword.
- **Add New User:** Fill in the user details and click the "Add User" button.
- **View All Users:** Navigate to the users' section to see all registered users.
- **Add New Book:** Enter book details and click the "Add Book" button.
- **Update Book:** Select a book, modify the details, and click "Update".
- **Delete Book:** Select a book and click the "Delete" button.

### User Dashboard
- **Login:** Use a registered user's email and password.
- **View Books:** See the list of available books.
- **Borrow Book:** Select a book and click "Borrow Book".
- **Return Book:** Select a borrowed book and click "Return Book".
- **View Transactions:** View the list of your past transactions.