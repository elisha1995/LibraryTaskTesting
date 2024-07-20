package com.librarytask;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboard {

    private final LibraryService libraryService;

    public AdminDashboard(LibraryService libraryService) {

        this.libraryService = libraryService;
    }


    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        ListView<String> bookListView = new ListView<>();
        bookListView.setId("bookListView");

        ListView<String> userListView = new ListView<>();
        userListView.setId("userListView");

        try {
            // Load books into the bookListView
            ResultSet books = libraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                    books.getInt("bookid") + " - " + books.getString("title") + " - " +
                            books.getString("author") + " - " + books.getString("isbn") + " - " +
                            (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }

            // Load users into the userListView
            ResultSet users = libraryService.getAllUsers();
            while (users.next()) {
                userListView.getItems().add(
                        users.getInt("patronid") + " - " + users.getString("name") + " - " + users.getString("email") + " - " + users.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button addBookButton = new Button("Add Book");
        addBookButton.setId("addBookButton");
        Button removeBookButton = new Button("Remove Book");
        removeBookButton.setId("removeBookButton");
        Button addUserButton = new Button("Add User");
        addUserButton.setId("addUserButton");
        Button removeUserButton = new Button("Remove User");
        removeUserButton.setId("removeUserButton");
        Button viewUsersButton = new Button("View All Users");
        viewUsersButton.setId("viewUsersButton");

        vbox.getChildren().addAll(bookListView, addBookButton, removeBookButton, userListView, addUserButton, removeUserButton, viewUsersButton);

        // Handle Add Book button action
        addBookButton.setOnAction(e -> {
            libraryService.openAddBookDialog();;
            // Refresh book list after adding a book
            refreshBookList(bookListView);
        });

        // Handle Remove Book button action
        removeBookButton.setOnAction(e -> {
            String selectedItem = bookListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int bookId = Integer.parseInt(selectedItem.split(" - ")[0]);
                try {
                    Book book = new Book(bookId);
                    if (libraryService.removeBook(book)) {
                        System.out.println("Book removed successfully");
                        refreshBookList(bookListView);
                    } else {
                        System.out.println("Failed to remove book");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Handle Add User button action
        addUserButton.setOnAction(e -> {
            System.out.println("Opening AddUserDialog...");
            libraryService.openAddUserDialog();
            // Refresh user list after adding a user
            refreshUserList(userListView);
        });

        // Handle Remove User button action
        removeUserButton.setOnAction(e -> {
            libraryService.openRemoveUserDialog();
            // Refresh user list after removing a user
            refreshUserList(userListView);
        });

        // Handle View All Users button action
        viewUsersButton.setOnAction(e -> {
            refreshUserList(userListView);
        });

        Scene scene = new Scene(vbox, 900, 700);
        stage.setScene(scene);
        stage.show();
    }

    private void refreshBookList(ListView<String> bookListView) {
        bookListView.getItems().clear();
        try {
            ResultSet books = libraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                        books.getInt("bookid") + " - " + books.getString("title") + " - " +
                                books.getString("author") + " - " + books.getString("isbn") + " - " +
                                (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshUserList(ListView<String> userListView) {
        userListView.getItems().clear();
        try {
            ResultSet users = libraryService.getAllUsers();
            while (users.next()) {
                userListView.getItems().add(
                        users.getInt("patronid") + " - " + users.getString("name") + " - " + users.getString("email") + " - " + users.getString("role")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
