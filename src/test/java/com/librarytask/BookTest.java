package com.librarytask;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book(1, "Effective Java", "Joshua Bloch", "978-0134685991", true);
    }

    @AfterEach
    void tearDown() {
        book = null;
    }

    @Test
    void getBookId() {
        assertEquals(1, book.getBookId());
    }

    @Test
    void getTitle() {
        assertEquals("Effective Java", book.getTitle());
    }

    @Test
    void getAuthor() {
        assertEquals("Joshua Bloch", book.getAuthor());
    }

    @Test
    void getIsbn() {
        assertEquals("978-0134685991", book.getIsbn());
    }

    @Test
    void isAvailable() {
        assertTrue(book.isAvailable());
    }

    @Test
    void setAvailable() {
        book.setAvailable(false);
    }
}