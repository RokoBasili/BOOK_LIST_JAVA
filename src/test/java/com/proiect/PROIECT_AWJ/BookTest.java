package com.proiect.PROIECT_AWJ;
import org.junit.jupiter.api.Test;
import com.proiect.PROIECT_AWJ.model.Book;

import jakarta.validation.*;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/** Clasa pentru TESTE
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
class BookTest {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();
    @Test
    void testBookCreationSuccess() {
        // Arrange
        Book book = new Book(1L, "Test Title", "Test Author", 2023, "Fiction", 19.99, 10);
        // Act
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        // Assert
        assertTrue(violations.isEmpty(), "There should be no validation errors for a valid book.");
    }

    @Test
    void testBookValidationFailure() {
        // Arrange
        Book book = new Book();
        book.setId(2L); // ID is valid
        book.setTitle(""); // Invalid title
        book.setAuthor(""); // Invalid author
        book.setYear(2022); // Valid year
        book.setGenre(""); // Invalid genre
        book.setPrice(10.00); // Valid price
        book.setStock(5); // Valid stock
        // Act
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        // Assert
        assertFalse(violations.isEmpty(), "There should be validation errors for an invalid book.");
        assertEquals(3, violations.size(), "There should be exactly 3 validation errors.");
    }
}

