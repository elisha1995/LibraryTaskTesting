package com.librarytask;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LibraryApp extends Application {
    private final LibraryService libraryService;

    public LibraryApp(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Create the login form
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label emailLabel = new Label("Email:");
        grid.add(emailLabel, 0, 0);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 0);

        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 1);

        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 1);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 2);

        // Handle login button click
        loginButton.setOnAction(e -> {
            String email = emailTextField.getText();
            String password = passwordField.getText();
            try {
                if (LibraryService.validateUser(email, password)) {
                    int patronId = LibraryService.getPatronEmail(email);
                    if (LibraryService.isAdmin(email)) {
                        new AdminDashboard(libraryService).start(new Stage());
                    } else {
                        new UserDashboard(patronId).start(new Stage());
                    }
                    primaryStage.close();
                } else {
                    // Show error message
                    System.out.println("Invalid credentials");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

