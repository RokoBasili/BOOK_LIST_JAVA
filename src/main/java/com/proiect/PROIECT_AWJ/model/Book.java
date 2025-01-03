package com.proiect.PROIECT_AWJ.model;

import jakarta.validation.constraints.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/** Clasa pentru MODEL
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
//Getters and Setters lombark
@Setter
@Getter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //Plasarea constrangerilor pt variabile/w erori si definirea var de identificare
    private Long id;

    @NotBlank(message = "Este nevoie de un titlu!")
    //@Size(min = 1, max = 100, message = "Title must be between 1 and 100 characters")
    private String title;

    @NotBlank(message = "Este nevoie de un autor!")
   //@Size(min = 1, max = 150, message = "Author must be between 1 and 150 characters")
    private String author;

    //@Max(value = 2025, message = "Anul nu poate fi in viitor")
    private int year;

    @NotBlank(message = "Genul cartii este necesar")
    //@Size(min = 1, max = 30, message = "Genre must be between 1 and 30 characters")
    private String genre;

    //@DecimalMin(value = "0.00", message = "Price must be non-negative")
    @Digits(integer = 10, fraction = 2, message = "Pretul trebuie sa aiba maxim 2 cifre dupa virgula (restul se rotunjeste)")
    private double price;

    //@Min(value = 0, message = "Stocul nu poate fi negativ")
    private int stock;

    // Constructori
    public Book() {}
    public Book(Long id, String title, String author, int year, String genre, double price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.genre = genre;
        this.price = price;
        this.stock = stock;
    }
}