/*
package com.librarytask;

import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDashboardTest extends ApplicationTest {

    private LibraryService libraryService;
    private int patronId = 1;

    @Override
    public void start(Stage stage) {
        libraryService = mock(LibraryService.class);
        UserDashboard userDashboard = new UserDashboard(patronId);
        userDashboard.start(stage);
    }

    @Test
    void testLoadBooks() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("bookid")).thenReturn(1);
        when(resultSet.getString("author")).thenReturn("Test Author");
        when(resultSet.getString("isbn")).thenReturn("123456789");
        when(resultSet.getBoolean("isavailable")).thenReturn(true);

        when(libraryService.getAllBooks()).thenReturn(resultSet);

        ListView<String> bookListView = lookup("#bookListView").query();
        assertNotNull(bookListView);
        assertEquals(1, bookListView.getItems().size());
        assertEquals("1 - Test Author - 123456789 - Available", bookListView.getItems().get(0));
    }

    @Test
    void testLoadTransactions() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getInt("bookid")).thenReturn(1);
        when(resultSet.getDate("date_borrowed")).thenReturn(java.sql.Date.valueOf("2023-01-01"));
        when(resultSet.getDate("date_returned")).thenReturn(java.sql.Date.valueOf("2023-01-10"));

        when(libraryService.getUserTransactions(patronId)).thenReturn(resultSet);

        ListView<String> transactionListView = lookup("#transactionListView").query();
        assertNotNull(transactionListView);
        assertEquals(1, transactionListView.getItems().size());
        assertEquals("Book ID: 1, Date Borrowed: 2023-01-01, Date Returned: 2023-01-10", transactionListView.getItems().get(0));
    }
}
*/
