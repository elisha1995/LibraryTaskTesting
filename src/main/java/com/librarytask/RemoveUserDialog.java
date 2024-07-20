package com.librarytask;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class RemoveUserDialog extends Stage {
    private LibraryService libraryService;

    public RemoveUserDialog() {
        this(new LibraryService());
    }
    public RemoveUserDialog(LibraryService libraryService) {
        this.libraryService = libraryService;

        setTitle("Remove User");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label userIdLabel = new Label("User ID:");
        grid.add(userIdLabel, 0, 0);

        TextField userIdTextField = new TextField();
        userIdTextField.setId("userIdTextField");
        grid.add(userIdTextField, 1, 0);

        Button removeButton = new Button("Remove User");
        removeButton.setId("removeButton");
        grid.add(removeButton, 1, 1);

        removeButton.setOnAction(e -> {
            try {
                int patronId = Integer.parseInt(userIdTextField.getText());
                try {
                    Patron patron = new Patron(patronId);

                    if (libraryService.removeUser(patron)) {
                        System.out.println("User removed successfully");
                        close();
                    } else {
                        System.out.println("Failed to remove user");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid user ID");
            }
        });

        Scene scene = new Scene(grid, 300, 150);
        setScene(scene);
        initModality(Modality.APPLICATION_MODAL);
    }
}
