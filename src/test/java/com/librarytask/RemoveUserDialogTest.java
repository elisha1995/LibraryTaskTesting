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
class RemoveUserDialogTest {

    @Mock
    private DatabaseUtil mockDatabaseUtil;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private RemoveUserDialog dialog;
    private LibraryService libraryService;

    @Start
    private void start(Stage stage) throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        libraryService = new LibraryService(mockDatabaseUtil);

        // Replace the static LibraryService with our mocked instance
        dialog = new RemoveUserDialog(libraryService);
        dialog.show();
    }

    @Test
    void testRemoveUserSuccessful(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        robot.clickOn("#userIdTextField").write("1");
        robot.clickOn("#removeButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isFalse();
    }

    @Test
    void testRemoveUserFailure(FxRobot robot) throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(0);

        robot.clickOn("#userIdTextField").write("1");
        robot.clickOn("#removeButton");

        verify(mockPreparedStatement, times(1)).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testRemoveUserInvalidInput(FxRobot robot) throws SQLException {
        robot.clickOn("#userIdTextField").write("invalid");
        robot.clickOn("#removeButton");

        verify(mockPreparedStatement, never()).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }

    @Test
    void testRemoveUserEmptyInput(FxRobot robot) throws SQLException {
        robot.clickOn("#removeButton");

        verify(mockPreparedStatement, never()).executeUpdate();
        assertThat(dialog.isShowing()).isTrue();
    }
}