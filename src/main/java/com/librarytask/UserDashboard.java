package com.librarytask;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDashboard {
    private int patronId;

    public UserDashboard(int patronId) {
        this.patronId = patronId;
    }

    public void start(Stage stage) {
        VBox vbox = new VBox(10);
        ListView<String> bookListView = new ListView<>();
        ListView<String> transactionListView = new ListView<>();

        try {
            ResultSet books = LibraryService.getAllBooks();
            while (books.next()) {
                bookListView.getItems().add(
                        books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                );
            }

            ResultSet transactions = LibraryService.getUserTransactions(patronId);
            while (transactions.next()) {
                transactionListView.getItems().add(
                        "Book ID: " + transactions.getInt("bookid") + ", Date Borrowed: " + transactions.getDate("date_borrowed") + ", Date Returned: " + transactions.getDate("date_returned")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewTransactionsButton = new Button("View Transactions");

        vbox.getChildren().addAll(bookListView, borrowBookButton, returnBookButton, transactionListView, viewTransactionsButton);

        // Handle button actions
        borrowBookButton.setOnAction(e -> {
            String selectedItem = bookListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int bookId = Integer.parseInt(selectedItem.split(" - ")[0]);
                try {
                    if (LibraryService.borrowBook(bookId, patronId)) {
                        System.out.println("Book borrowed successfully");
                        bookListView.getItems().clear();
                        ResultSet books = LibraryService.getAllBooks();
                        while (books.next()) {
                            bookListView.getItems().add(
                                    books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                            );
                        }
                    } else {
                        System.out.println("Book is not available");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        returnBookButton.setOnAction(e -> {
            String selectedItem = bookListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                int bookId = Integer.parseInt(selectedItem.split(" - ")[0]);
                try {
                    if (LibraryService.returnBook(bookId, patronId)) {
                        System.out.println("Book returned successfully");
                        bookListView.getItems().clear();
                        ResultSet books = LibraryService.getAllBooks();
                        while (books.next()) {
                            bookListView.getItems().add(
                                    books.getInt("bookid") + " - " + books.getString("author") + " - " + books.getString("isbn") + " - " + (books.getBoolean("isavailable") ? "Available" : "Borrowed")
                            );
                        }
                    } else {
                        System.out.println("Failed to return book");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        viewTransactionsButton.setOnAction(e -> {
            transactionListView.getItems().clear();
            try {
                ResultSet transactions = LibraryService.getUserTransactions(patronId);
                while (transactions.next()) {
                    transactionListView.getItems().add(
                            "Book ID: " + transactions.getInt("bookid") + ", Date Borrowed: " + transactions.getDate("date_borrowed") + ", Date Returned: " + transactions.getDate("date_returned")
                    );
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Scene scene = new Scene(vbox, 400, 300);
        stage.setScene(scene);
        stage.show();
    }
}

