/*
package com.librarytask;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.mockito.MockedStatic;

import java.sql.SQLException;

import static org.mockito.Mockito.*;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class AddBookDialogTest {

    private AddBookDialog dialog;

    @Start
    private void start(Stage stage) {
        dialog = new AddBookDialog();
        dialog.show();
    }

    @Test
    void testAddBookSuccessful(FxRobot robot) throws SQLException {
        // Mock LibraryService.addBook
        try (MockedStatic<LibraryService> mockedLibraryService = mockStatic(LibraryService.class)) {
            mockedLibraryService.when(() -> LibraryService.addBook(any(Book.class))).thenReturn(true);

            // Fill in the form
            robot.clickOn("#titleInput").write("Test Book");
            robot.clickOn("#authorInput").write("Test Author");
            robot.clickOn("#isbnInput").write("1234567890");

            // Click the add button
            robot.clickOn("#addButton");

            // Verify that LibraryService.addBook was called
            mockedLibraryService.verify(() -> LibraryService.addBook(any(Book.class)));

            // Verify that the dialog is closed
            assertThat(dialog.isShowing()).isFalse();
        }
    }

    @Test
    void testAddBookFailure(FxRobot robot) throws SQLException {
        // Mock LibraryService.addBook
        try (MockedStatic<LibraryService> mockedLibraryService = mockStatic(LibraryService.class)) {
            mockedLibraryService.when(() -> LibraryService.addBook(any(Book.class))).thenReturn(false);

            // Fill in the form
            robot.clickOn("#titleInput").write("Test Book");
            robot.clickOn("#authorInput").write("Test Author");
            robot.clickOn("#isbnInput").write("1234567890");

            // Click the add button
            robot.clickOn("#addButton");

            // Verify that LibraryService.addBook was called
            mockedLibraryService.verify(() -> LibraryService.addBook(any(Book.class)));

            // Verify that the dialog is still showing
            assertThat(dialog.isShowing()).isTrue();
        }
    }

    @Test
    void testAddBookEmptyFields(FxRobot robot) {
        // Click the add button without filling in the form
        robot.clickOn("#addButton");

        // Verify that the dialog is still showing
        assertThat(dialog.isShowing()).isTrue();
    }
}

*/
/*package com.librarytask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.testfx.api.FxRobot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
public class AddBookDialogTest extends ApplicationTest {

    @Mock
    private AddBookDialog dialog;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;
    @Mock
    private DatabaseUtil mockDatabaseUtil;

    private LibraryService libraryService;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        libraryService = new LibraryService(mockDatabaseUtil);
        dialog = new AddBookDialog();

    }

    @Start
    public void start(Stage stage) {
       dialog.show();
    }

    @Test
    void testAddBookSuccess(FxRobot robot) throws SQLException {

        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);

        // Fill in the form
        robot.clickOn("#titleInput").write("Test Book");
        robot.clickOn("#authorInput").write("Test Author");
        robot.clickOn("#isbnInput").write("1234567890");

        // Click the add button
        robot.clickOn("#addButton");

        // Verify that the PreparedStatement was executed
        verify(mockPreparedStatement).executeUpdate();

        // Verify that the dialog is closed
        assertThat(dialog.isShowing()).isFalse();
    }

    @Test
    void testAddBookFailure(FxRobot robot) throws SQLException {
        // Mock Connection and PreparedStatement
        Connection mockConnection = mock(Connection.class);
        PreparedStatement mockPreparedStatement = mock(PreparedStatement.class);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(any(String.class))).thenReturn(mockPreparedStatement);
        doThrow(new SQLException("Test exception")).when(mockPreparedStatement).executeUpdate();

        // Fill in the form
        robot.clickOn("#titleInput").write("Test Book");
        robot.clickOn("#authorInput").write("Test Author");
        robot.clickOn("#isbnInput").write("1234567890");

        // Click the add button
        robot.clickOn("#addButton");

        // Verify that the dialog is still open
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testAddBookEmptyFields(FxRobot robot) {
        // Try to submit the form with empty fields
        robot.clickOn("#addButton");

        // Verify that the dialog is still open
        assertThat(dialog.isShowing()).isTrue();
    }
}*//*


*/

package com.librarytask;


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
class AddBookDialogTest {

    @Mock
    private DatabaseUtil mockDatabaseUtil;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private AddBookDialog dialog;
    private LibraryService libraryService;

    @Start
    private void start(Stage stage) throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        libraryService = new LibraryService(mockDatabaseUtil);

        // Here we're replacing the static LibraryService with our mocked instance
        dialog = new AddBookDialog(libraryService);
        dialog.show();
    }

    @Test
    void testAddBookSuccessful(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        robot.clickOn("#titleInput").write("Test Book");
        robot.clickOn("#authorInput").write("Test Author");
        robot.clickOn("#isbnInput").write("1234567890");

        robot.clickOn("#addButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isFalse();
    }

    @Test
    void testAddBookFailure(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        robot.clickOn("#titleInput").write("Test Book");
        robot.clickOn("#authorInput").write("Test Author");
        robot.clickOn("#isbnInput").write("1234567890");

        robot.clickOn("#addButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testAddBookEmptyFields(FxRobot robot) throws SQLException {
        robot.clickOn("#addButton");

        verify(mockPreparedStatement, never()).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }
}