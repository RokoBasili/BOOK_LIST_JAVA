package com.proiect.PROIECT_AWJ.service;
import com.proiect.PROIECT_AWJ.model.Book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Clasa pentru SERVICE (business logic)
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
@Service
public class BookService {
    private final List<Book> carti = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File fisier = new File("PROIECT_AWJ\\books.json");
                            //ATENTIE la calea fisierului ^^^^^
    public BookService() {
        // Folosirea unui fisier books.json pt a manipula colectia de carti
        // si apelarea constructorului
        if (!fisier.exists()) {
            //Daca nu exista se creaza noul fisier si se initializeaza cu 10 carti noi de inceput
            carti.addAll(getInitialBooks());
            saveBooks();
        }
        else {
            // Daca exista se incarca fisierul drept colectia de carti
            loadBooks();
        }
    }
    // Functia de incarcare a fisierului si citire in json
    public void loadBooks() {
        try {
            if (fisier.exists()) {
                List<Book> cartiCitite = objectMapper.readValue(fisier, new TypeReference<List<Book>>(){});
                carti.clear(); // Stergerea cartilor duplicate daca exista
                carti.addAll(cartiCitite);
            }
        } catch (IOException e) {
            System.err.println("Eroare la initializarea cartilor: " + e.getMessage());
        }
    }
    // Functia de salvare a cartilor
    public void saveBooks() {
        try {
            objectMapper.writeValue(fisier, carti);
        } catch (IOException e) {
            System.err.println("Eroare la salvarea cartilor: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(carti);
    }
    // Adaugare carte noua / update
    public Book addOrUpdateBook(Book carte) {
        Optional<Book> existingBook = carti.stream()
                .filter(b -> b.getId().equals(carte.getId()))
                .findFirst();
        if (existingBook.isPresent()) {
            // Update
            int index = carti.indexOf(existingBook.get());
            carti.set(index, carte);
        }
        else {
            // Adaugare carte noua
            carti.add(carte);
        }
        saveBooks(); //Apelare la functia de save pt salvarea modificarilor
        return carte;
    }
    //Stergere carte dupa ID
    public void deleteBook(Long id) {
        carti.removeIf(book -> book.getId().equals(id));
        saveBooks(); // Apelare la functia de save pt modificare
    }
    // Gasirea cartii dupa ID pentru update la carte
    public Book getBookById(Long id) {
        return carti.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    // Incarcarea valorilor initiale daca fisierul nu este gasit (metoda privata)
    private List<Book> getInitialBooks() {
        List<Book> CartiInitiale = new ArrayList<>();
        CartiInitiale.add(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic", 10.99, 20));
        CartiInitiale.add(new Book(2L, "1984", "George Orwell", 1949, "Dystopian", 8.99, 15));
        CartiInitiale.add(new Book(3L, "To Kill a Mockingbird", "Harper Lee", 1960, "Classic", 12.99, 10));
        CartiInitiale.add(new Book(4L, "The Hobbit", "J.R.R. Tolkien", 1937, "Fantasy", 15.99, 25));
        CartiInitiale.add(new Book(5L, "Moby Dick", "Herman Melville", 1851, "Adventure", 9.99, 5));
        CartiInitiale.add(new Book(6L, "Pride and Prejudice", "Jane Austen", 1813, "Romance", 7.99, 18));
        CartiInitiale.add(new Book(7L, "The Catcher in the Rye", "J.D. Salinger", 1951, "Fiction", 6.99, 30));
        CartiInitiale.add(new Book(8L, "The Lord of the Rings", "J.R.R. Tolkien", 1954, "Fantasy", 20.99, 12));
        CartiInitiale.add(new Book(9L, "Harry Potter and the Sorcerer's Stone", "J.K. Rowling", 1997, "Fantasy", 10.99, 40));
        CartiInitiale.add(new Book(10L, "The Chronicles of Narnia", "C.S. Lewis", 1956, "Fantasy", 14.99, 8));
        return CartiInitiale;
    }
}