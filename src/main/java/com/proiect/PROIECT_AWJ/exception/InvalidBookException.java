package com.proiect.PROIECT_AWJ.exception;

/** Clasa pentru EXCEPTII
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
// Invalid Book Exception
public class InvalidBookException extends RuntimeException {
    public InvalidBookException(String message) {
        super(message);
    }
}
