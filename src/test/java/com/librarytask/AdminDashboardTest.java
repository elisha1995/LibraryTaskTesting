package com.librarytask;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class AdminDashboardTest {

    @Mock
    private LibraryService mockLibraryService;
    @Mock
    private DatabaseUtil mockDatabaseUtil;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private LibraryService libraryService;
    private AdminDashboard adminDashboard;

    @Start
    private void start(Stage stage) throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
//        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
//        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock the getAllBooks and getAllUsers methods
        when(mockLibraryService.getAllBooks()).thenReturn(mockResultSet);
        when(mockLibraryService.getAllUsers()).thenReturn(mockResultSet);

        // Mock other methods as needed
        when(mockLibraryService.removeBook(any(Book.class))).thenReturn(true);
        // libraryService = new LibraryService(mockDatabaseUtil);
        adminDashboard = new AdminDashboard(mockLibraryService);
        adminDashboard.start(stage);
    }

    @BeforeEach
    void setUp() throws SQLException {
        // Reset the mock result set before each test
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getInt("bookid")).thenReturn(1, 2);
        when(mockResultSet.getString("title")).thenReturn("Book 1", "Book 2");
        when(mockResultSet.getString("author")).thenReturn("Author 1", "Author 2");
        when(mockResultSet.getString("isbn")).thenReturn("ISBN1", "ISBN2");
        when(mockResultSet.getBoolean("isavailable")).thenReturn(true, false);

        when(mockResultSet.getInt("patronid")).thenReturn(1, 2);
        when(mockResultSet.getString("name")).thenReturn("User 1", "User 2");
        when(mockResultSet.getString("email")).thenReturn("user1@example.com", "user2@example.com");
        when(mockResultSet.getString("role")).thenReturn("user", "admin");
    }

    @Test
    void testInitialBookListLoading(FxRobot robot) {
        ListView<String> bookListView = robot.lookup("#bookListView").queryListView();
        assertThat(bookListView.getItems()).hasSize(2);
        assertThat(bookListView.getItems().get(0)).contains("Book 1");
        assertThat(bookListView.getItems().get(1)).contains("Book 2");
    }

    @Test
    void testInitialUserListLoading(FxRobot robot) {
        ListView<String> userListView = robot.lookup("#userListView").queryListView();
        assertThat(userListView.getItems()).hasSize(2);
        assertThat(userListView.getItems().get(0)).contains("User 1");
        assertThat(userListView.getItems().get(1)).contains("User 2");
    }

    @Test
    void testAddBookButton(FxRobot robot) {
        robot.clickOn("#addBookButton");
        verify(mockLibraryService, times(1)).openAddBookDialog();
    }

    @Test
    void testRemoveBookButton(FxRobot robot) throws SQLException {
        ListView<String> bookListView = robot.lookup("#bookListView").queryListView();
        robot.clickOn(bookListView.getItems().get(0));
        robot.clickOn("#removeBookButton");
        verify(mockLibraryService, times(1)).removeBook(any(Book.class));
    }

    @Test
    void testAddUserButton(FxRobot robot) {
        robot.clickOn("#addUserButton");
        verify(mockLibraryService, times(1)).openAddUserDialog();
    }

    @Test
    void testRemoveUserButton(FxRobot robot) {
        robot.clickOn("#removeUserButton");
        verify(mockLibraryService, times(1)).openRemoveUserDialog();
    }

    @Test
    void testViewAllUsersButton(FxRobot robot) throws SQLException {
        robot.clickOn("#viewUsersButton");
        verify(mockLibraryService, times(2)).getAllUsers(); // Once on init, once on button click
    }
}
