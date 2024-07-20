package com.librarytask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibraryServiceTest {

    @Mock
    private DatabaseUtil mockDatabaseUtil;
    @Mock
    private Connection mockConnection;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private LibraryService libraryService;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        when(mockDatabaseUtil.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        libraryService = new LibraryService(mockDatabaseUtil);
    }

    @Test
    void testBorrowBook_Available() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBoolean("isavailable")).thenReturn(true);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(LibraryService.borrowBook(1, 1));
    }

    @Test
    void testBorrowBook_Unavailable() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getBoolean("isavailable")).thenReturn(false);

        assertFalse(LibraryService.borrowBook(1, 1));
    }

    @Test
    void testReturnBook_Success() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        assertTrue(LibraryService.returnBook(1, 1));
    }

    @Test
    void testReturnBook_Failure() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        assertFalse(LibraryService.returnBook(1, 1));
    }

    @Test
    void testAddBook_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Book book = new Book("Test Book", "Test Author", "1234567890");

        assertTrue(LibraryService.addBook(book));
    }

    @Test
    void testRemoveBook_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Book book = new Book(1, "Test Book", "Test Author", "1234567890", true);

        assertTrue(LibraryService.removeBook(book));
    }

    @Test
    void testValidateUser_Success() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);

        assertTrue(LibraryService.validateUser("test@example.com", "password"));
    }

    @Test
    void testValidateUser_Failure() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(false);

        assertFalse(LibraryService.validateUser("test@example.com", "wrong_password"));
    }

    @Test
    void testIsAdmin_True() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("role")).thenReturn("admin");

        assertTrue(LibraryService.isAdmin("admin@example.com"));
    }

    @Test
    void testIsAdmin_False() throws SQLException {
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getString("role")).thenReturn("user");

        assertFalse(LibraryService.isAdmin("user@example.com"));
    }

    @Test
    void testAddUser_Success() throws SQLException {
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        Patron patron = new Patron("Test User", "test@example.com", "password", "user");

        assertTrue(LibraryService.addUser(patron));
    }
}
