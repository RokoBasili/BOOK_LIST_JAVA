package com.proiect.PROIECT_AWJ;

import com.proiect.PROIECT_AWJ.controller.BookController;
import com.proiect.PROIECT_AWJ.model.Book;
import com.proiect.PROIECT_AWJ.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Clasa pentru TESTE
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
class BookControllerTest {
    @Mock
    private BookService bookService;
    @Mock
    private Model model;
    @InjectMocks
    private BookController bookController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        // Arrange
        Book book1 = new Book(1L, "Title 1", "Author 1", 2020, "Genre 1", 10.0, 5);
        Book book2 = new Book(2L, "Title 2", "Author 2", 2021, "Genre 2", 15.0, 3);
        List<Book> mockBooks = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(mockBooks);
        // Act
        String viewName = bookController.getAllBooks(model);
        // Assert
        assertEquals("book_list", viewName);
        verify(model, times(1)).addAttribute("books", mockBooks);
    }

    @Test
    void testAddBook() {
        // Arrange
        Book newBook = new Book(null, "New Title", "New Author", 2022, "New Genre", 20.0, 10);
        Book savedBook = new Book(1L, "New Title", "New Author", 2022, "New Genre", 20.0, 10);
        when(bookService.addOrUpdateBook(newBook)).thenReturn(savedBook);
        // Act
        ResponseEntity<Book> response = bookController.addBook(newBook);
        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(savedBook, response.getBody());
        verify(bookService, times(1)).addOrUpdateBook(newBook);
    }
}
