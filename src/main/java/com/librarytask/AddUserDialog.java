package com.librarytask;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddUserDialog extends Stage {
    private LibraryService libraryService;

    public AddUserDialog() {
        this(new LibraryService());
    }

    public AddUserDialog(LibraryService libraryService) {
        this.libraryService = libraryService;

        setTitle("Add User");

        VBox vbox = new VBox(20);
        vbox.setPadding(new javafx.geometry.Insets(10));

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        nameField.setId("nameField");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setId("emailField");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setId("passwordField");

        Label roleLabel = new Label("Role (user/admin):");
        TextField roleField = new TextField();
        roleField.setId("roleField");

        Button addButton = new Button("Add");
        addButton.setId("addButton");

        Button cancelButton = new Button("Cancel");
        cancelButton.setId("cancelButton");

        vbox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField, roleLabel, roleField, addButton, cancelButton);

        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String role = roleField.getText();
            if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !role.isEmpty()) {
                try {
                    System.out.println("Adding user: " + name + ", " + email + ", " + role);

                    Patron patron = new Patron(name, email, password, role);
                    boolean success = libraryService.addUser(patron);
                    if (success) {
                        System.out.println("User added successfully");
                        this.close();
                    } else {
                        System.out.println("Failed to add user");
                        // Don't close the dialog, maybe show an error message
                    }
                } catch (SQLException ex) {
                    System.err.println("Error adding user: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }else {
                System.out.println("All fields must be filled");
            }
        });

        cancelButton.setOnAction(e -> this.close());

        Scene scene = new Scene(vbox);
        this.setScene(scene);
        this.initModality(Modality.APPLICATION_MODAL);
    }
}
