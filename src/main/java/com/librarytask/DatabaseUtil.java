/*
package com.librarytask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "root";
    private static final String PASSWORD = "test1234!";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}*/

package com.librarytask;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private String url;
    private String user;
    private String password;

    // Constructor for production use
    public DatabaseUtil() {
        this.url = "jdbc:mysql://localhost:3306/library_management";
        this.user = "root";
        this.password = "test1234!";
    }

    // Constructor for testing (dependency injection)
    public DatabaseUtil(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}