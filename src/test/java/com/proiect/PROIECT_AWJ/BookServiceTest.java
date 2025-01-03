package com.proiect.PROIECT_AWJ;

import com.proiect.PROIECT_AWJ.service.BookService;
import com.proiect.PROIECT_AWJ.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Clasa pentru TESTE
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
class BookServiceTest {
    private BookService bookService;
    @BeforeEach
    void setUp() {
        bookService = new BookService();
    }

    @Test
    void testAddAndRetrieveBook() {
        // Arrange
        Book newBook = new Book(11L, "Test Title", "Test Author", 2023, "Test Genre", 19.99, 10);
        // Act
        bookService.addOrUpdateBook(newBook);
        Book retrievedBook = bookService.getBookById(11L);
        // Assert
        assertNotNull(retrievedBook, "The retrieved book should not be null.");
        assertEquals(newBook.getId(), retrievedBook.getId(), "Book IDs should match.");
        assertEquals(newBook.getTitle(), retrievedBook.getTitle(), "Book titles should match.");
        assertEquals(newBook.getAuthor(), retrievedBook.getAuthor(), "Book authors should match.");
    }

    @Test
    void testDeleteBook() {
        // Arrange
        Book bookToDelete = new Book(12L, "Delete Title", "Delete Author", 2020, "Delete Genre", 15.99, 5);
        bookService.addOrUpdateBook(bookToDelete);
        // Act
        bookService.deleteBook(12L);
        Book retrievedBook = bookService.getBookById(12L);
        // Assert
        assertNull(retrievedBook, "The book should be null after deletion.");
    }
}
