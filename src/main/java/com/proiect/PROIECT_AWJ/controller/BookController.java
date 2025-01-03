package com.proiect.PROIECT_AWJ.controller;
import com.proiect.PROIECT_AWJ.exception.*;
import com.proiect.PROIECT_AWJ.model.Book;
import com.proiect.PROIECT_AWJ.service.BookService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/** Clasa pentru CONTROLLER
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
@Controller
@RequestMapping("/")
public class BookController {
    @Autowired
    private BookService bookService;
    @GetMapping
    //Metoda pt pasarea paginii de frontend book_list si maparea valorilor in obiecte java
    public String getAllBooks(Model model) {
        try {
            List<Book> carti = bookService.getAllBooks();
            model.addAttribute("books", carti);
            return "book_list";
        } catch (Exception e) {
            throw new InvalidBookException("Eroare la incarcarea cartilor: " + e.getMessage());
        }
    }
    // Incarcarea cartilor din fisierul JSON 'books.json'
    @GetMapping("/api/books")
    public ResponseEntity<List<Book>> getBooks() {
        try {
            List<Book> carti = bookService.getAllBooks();
            return ResponseEntity.ok(carti);
        } catch (Exception e) {
            throw new InvalidBookException("Incarcarea din fisierul JSON nu a putut fi realizata: " + e.getMessage());
        }
    }
    // Metoda pt preluarea id ului unei carti pt adaugare/editare
    @GetMapping("/api/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        try {
            Book carte = bookService.getBookById(id);
            if (carte == null) {
                throw new BookNotFoundException("Cartea cu id-ul: "+id+ " nu a fost gasita");
            }
            return ResponseEntity.ok(carte);
        } catch (DataAccessException e) {
            throw new JsonFileException("Eroare la fetch pentru JSON: " + e.getMessage());
        }
    }
    // Metoda pentru cautarea unei carti dupa titlu sau autor
    @GetMapping("/api/books/search")
    public ResponseEntity<List<Book>> searchBooks(@RequestParam(required = false) String term) {
        try {
            //Daca nu este introdus nimic in search box atunci se returneaza toata lista de carti
            if (term == null || term.trim().isEmpty()) {
                return ResponseEntity.ok(bookService.getAllBooks());
            }
            //Daca este introdus ceva atunci se afiseaza doar ce are match pe term --> elementul introdus
            String param_introdus = term.toLowerCase();
            List<Book> Rezultate_param = bookService.getAllBooks().stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(param_introdus) ||
                                    book.getAuthor().toLowerCase().contains(param_introdus))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(Rezultate_param);
            //returneaza o lista de tip BOOK cu cartile care indeplinesc cerinta de cautare
        } catch (Exception e) {
            throw new InvalidBookException("Cautarea nu a putut fi indeplinita. Eroarea: " + e.getMessage());
        }
    }
    // Adaugarea cartilor noi in functie de postul din frontend
    @PostMapping("/api/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book carte) {
        try {
            validateBook(carte);
            Book CarteSalvata = bookService.addOrUpdateBook(carte);
            return ResponseEntity.status(HttpStatus.CREATED).body(CarteSalvata);
        } catch (DataAccessException e) {
            throw new JsonFileException("Cartea nu a putut fi salvata in fisierul JSON: " + e.getMessage());
        }
    }
    // Modificarea cartii in functie de id
    @PutMapping("/api/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book DetaliiCarte) {
        Book carte = bookService.getBookById(id);
        if (carte == null) {
            throw new BookNotFoundException("Cartea cu id-ul: "+ id +" nu a fost gasita");
        }
        //Daca cartea a fost gasit in functie de id se face o validare a datelor pt a respecta constrangerile
        validateBook(DetaliiCarte);
        try {
            // Update la atributele noi
            carte.setTitle(DetaliiCarte.getTitle());
            carte.setAuthor(DetaliiCarte.getAuthor());
            carte.setYear(DetaliiCarte.getYear());
            carte.setGenre(DetaliiCarte.getGenre());
            carte.setPrice(DetaliiCarte.getPrice());
            carte.setStock(DetaliiCarte.getStock());
            Book CarteUpdate = bookService.addOrUpdateBook(carte);
            return ResponseEntity.ok(CarteUpdate);
        } catch (Exception e) {
            throw new InvalidBookException("Eroare la update-ul cartii cu id-ul: " +id+"\n"+ e.getMessage());
        }
    }
    // Metoda pt stergerea unei cartii in functie de id
    @DeleteMapping("/api/books/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable Long id) {
        Book carte = bookService.getBookById(id);
        if (carte == null) {
            throw new BookNotFoundException("Cartea cu id-ul: " + id + " nu a fost gasita");
        }
        //Daca id-ul exista in JSON ul cu cartile salvate se continua la stergerea cartii
        try {
            bookService.deleteBook(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Cartea a fost stearsa cu succes!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new InvalidBookException("Eroare la stergere: " + e.getMessage());
        }
    }
    // Metoda pt validarea campurilor unei carti care este modificata + verificarea constrangerilor
    private void validateBook(Book carte) {
        List<String> errors = new ArrayList<>();
        if (carte.getId() == null)
        {
            errors.add("ID este NULL");
        }
        if (carte.getTitle() == null || carte.getTitle().trim().isEmpty()) {
            errors.add("Titlul cartii nu poate fi lasat liber");
        }
        if (carte.getAuthor() == null || carte.getAuthor().trim().isEmpty()) {
            errors.add("Autorul cartii nu poate fi lasat liber");
        }
        if (carte.getYear() < 0) {
            errors.add("Anul nu poate fi mai mic ca 0. Doar carti dupa 0 A.D. ");
        }
        if (carte.getYear() > LocalDateTime.now().getYear()) {
            errors.add("Anul nu poate fi in viitor (>2025)");
        }
        if (carte.getPrice() <= 0) {
            errors.add("Pretul trebuie sa fie mai mare ca 0");
        }
        if (carte.getStock() < 0) {
            errors.add("Stocul nu poate sa fie negativ");
        }
        //Aici se verifica daca totul este ok (fara erori) si daca exista erori se arunca o eroare
        //modificata + un system err log despre toate informatiile erori din GlobalExcepHandler
        if (!errors.isEmpty()) {
            ResponseEntity<ErrorResponse> error=new GlobalExceptionHandler().GlobalExcept(new Exception("EROARE"));
            System.err.println(error);
            throw new InvalidBookException("Exista campuri invalide introduse: " + String.join(", ", errors));
        }
    }
}