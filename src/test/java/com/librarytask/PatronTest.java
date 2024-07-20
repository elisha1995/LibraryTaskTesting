package com.librarytask;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PatronTest {

    @Test
    public void testPatronCreation() {
        Patron patron = new Patron(1, "Admin User", "admin@example.com", "adminpass", "admin");
        assertEquals(1, patron.getPatronId());
        assertEquals("Admin User", patron.getName());
        assertEquals("admin@example.com", patron.getEmail());
        assertEquals("admin", patron.getRole());
    }
}
