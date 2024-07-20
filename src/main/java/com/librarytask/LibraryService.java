package com.librarytask;

import java.sql.*;
import java.util.LinkedList;
import java.util.Stack;

public class LibraryService {

    private Stack<Transaction> transactions = new Stack<>();

    private static DatabaseUtil databaseUtil;

    public LibraryService() {
        databaseUtil = new DatabaseUtil();
    }

    // Constructor for testing (dependency injection)
    public LibraryService(DatabaseUtil databaseUtil) {
        LibraryService.databaseUtil = databaseUtil;
    }

    public static boolean borrowBook(int bookId, int patronId) throws SQLException {
        try (Connection connection = databaseUtil.getConnection()) {
            // Check if the book is available
            String checkAvailabilityQuery = "SELECT isavailable FROM book WHERE bookid = ?";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkAvailabilityQuery)) {
                checkStmt.setInt(1, bookId);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next() && resultSet.getBoolean("isavailable")) {
                    // Update the book availability
                    String updateBookQuery = "UPDATE book SET isavailable = FALSE WHERE bookid = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateBookQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                    }
                    // Insert transaction record
                    String insertTransactionQuery = "INSERT INTO transaction (bookid, patronid, date_borrowed) VALUES (?, ?, CURDATE())";
                    try (PreparedStatement insertStmt = connection.prepareStatement(insertTransactionQuery)) {
                        insertStmt.setInt(1, bookId);
                        insertStmt.setInt(2, patronId);
                        insertStmt.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean returnBook(int bookId, int patronId) throws SQLException {
        try (Connection connection = databaseUtil.getConnection()) {
            // Check if the book was borrowed by the patron
            String checkTransactionQuery = "SELECT * FROM transaction WHERE bookid = ? AND patronid = ? AND date_returned IS NULL";
            try (PreparedStatement checkStmt = connection.prepareStatement(checkTransactionQuery)) {
                checkStmt.setInt(1, bookId);
                checkStmt.setInt(2, patronId);
                ResultSet resultSet = checkStmt.executeQuery();
                if (resultSet.next()) {
                    // Update the transaction record
                    String updateTransactionQuery = "UPDATE transaction SET date_returned = CURDATE() WHERE bookid = ? AND patronid = ? AND date_returned IS NULL";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateTransactionQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.setInt(2, patronId);
                        updateStmt.executeUpdate();
                    }
                    // Update the book availability
                    String updateBookQuery = "UPDATE book SET isavailable = TRUE WHERE bookid = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateBookQuery)) {
                        updateStmt.setInt(1, bookId);
                        updateStmt.executeUpdate();
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static ResultSet getAllBooks() throws SQLException {
        Connection connection = databaseUtil.getConnection();
        String query = "SELECT * FROM book";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getUserTransactions(int patronId) throws SQLException {
        Connection connection = databaseUtil.getConnection();
        String query = "SELECT * FROM transaction WHERE patronid = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, patronId);
        return statement.executeQuery();
    }

    public static boolean addBook(Book book) throws SQLException {
        try (Connection connection = databaseUtil.getConnection()) {
            String insertBookQuery = "INSERT INTO book (title, author, isbn, isavailable) VALUES (?, ?, ?, TRUE)";
            try (PreparedStatement stmt = connection.prepareStatement(insertBookQuery)) {
                stmt.setString(1, book.getTitle());
                stmt.setString(2, book.getAuthor());
                stmt.setString(3, book.getIsbn());
                //stmt.setBoolean(4, book.isAvailable());
                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
    }

    public static boolean removeBook(Book book) throws SQLException {
        try (Connection connection = databaseUtil.getConnection()) {
            String deleteBookQuery = "DELETE FROM book WHERE bookid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteBookQuery)) {
                stmt.setInt(1, book.getBookId());
                stmt.executeUpdate();
                return true;
            }
        }
    }

    public static boolean removeUser(Patron patron) throws SQLException {
        try (Connection connection = databaseUtil.getConnection()) {
            String deleteUserQuery = "DELETE FROM patron WHERE patronid = ?";
            try (PreparedStatement stmt = connection.prepareStatement(deleteUserQuery)) {
                stmt.setInt(1, patron.getPatronId());
                int affectedRow = stmt.executeUpdate();
                return affectedRow > 0;
            }
        }
    }

    public static int getPatronEmail(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = databaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT patronid FROM patron WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("patronid");
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static boolean validateUser(String email, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = databaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT * FROM patron WHERE email = ? AND password = ?");
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            return rs.next();
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static boolean isAdmin(String email) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = databaseUtil.getConnection();
            pstmt = conn.prepareStatement("SELECT role FROM patron WHERE email = ?");
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return "admin".equals(rs.getString("role"));
            } else {
                throw new SQLException("User not found");
            }
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        }
    }

    public static boolean addUser(Patron patron) throws SQLException {
        System.out.println("Connecting to database...");
        Connection conn = databaseUtil.getConnection();
        String sql = "INSERT INTO patron (name, email, password, role) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        System.out.println("Preparing statement...");
        pstmt.setString(1, patron.getName());
        pstmt.setString(2, patron.getEmail());
        pstmt.setString(3, patron.getPassword());
        pstmt.setString(4, patron.getRole());
        System.out.println("Executing statement...");
        int affectedRows = pstmt.executeUpdate();
        return affectedRows > 0;
    }

    public static ResultSet getAllUsers() throws SQLException {
        Connection conn = databaseUtil.getConnection();
        String sql = "SELECT * FROM patron";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public void openAddBookDialog() {
        AddBookDialog dialog = new AddBookDialog(this);
        dialog.showAndWait();
    }

    public void openAddUserDialog() {
        AddUserDialog dialog = new AddUserDialog(this);
        dialog.showAndWait();
    }

    public void openRemoveUserDialog() {
        RemoveUserDialog dialog = new RemoveUserDialog(this);
        dialog.showAndWait();
    }

    public Stack<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Stack<Transaction> transactions) {
        this.transactions = transactions;
    }
}

