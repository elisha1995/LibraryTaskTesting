package com.librarytask;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LibraryServiceParameterizedTest {

    @ParameterizedTest
    @CsvSource({
            "john@example.com, password, true",
            "invalid@example.com, password, false",
            "john@example.com, wrongpassword, false",
            "invalid@example.com, wrongpassword, false"
    })
    void testValidateUser(String email, String password, boolean expected) throws Exception {
        LibraryService libraryService = new LibraryService();

        boolean result = libraryService.validateUser(email, password);
        assertEquals(expected, result);
    }
}
