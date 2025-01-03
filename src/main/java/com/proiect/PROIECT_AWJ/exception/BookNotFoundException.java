package com.proiect.PROIECT_AWJ.exception;

/** Clasa pentru EXCEPTII
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
// Book Not Found Exception
public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
