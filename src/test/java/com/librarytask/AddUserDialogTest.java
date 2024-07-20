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
class AddUserDialogTest {

    @Mock
    private DatabaseUtil mockDatabaseUtil;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private AddUserDialog dialog;
    private LibraryService libraryService;

    @Start
    private void start(Stage stage) throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        libraryService = new LibraryService(mockDatabaseUtil);

        // Replace the static LibraryService with our mocked instance
        dialog = new AddUserDialog(libraryService);
        dialog.show();
    }

    @Test
    void testAddUserSuccessful(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        robot.clickOn("#nameField").write("John Doe");
        robot.clickOn("#emailField").write("john@example.com");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#roleField").write("user");

        robot.clickOn("#addButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isFalse();
    }

    @Test
    void testAddUserFailure(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        robot.clickOn("#nameField").write("John Doe");
        robot.clickOn("#emailField").write("john@example.com");
        robot.clickOn("#passwordField").write("password123");
        robot.clickOn("#roleField").write("user");

        robot.clickOn("#addButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testAddUserEmptyFields(FxRobot robot) throws SQLException {
        robot.clickOn("#addButton");

        verify(mockPreparedStatement, never()).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testCancelButton(FxRobot robot) {
        robot.clickOn("#cancelButton");

        assertThat(dialog.isShowing()).isFalse();
    }
}