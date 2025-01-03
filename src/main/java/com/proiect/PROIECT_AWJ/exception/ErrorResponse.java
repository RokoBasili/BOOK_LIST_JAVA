package com.proiect.PROIECT_AWJ.exception;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

/** Clasa pentru EXCEPTII
 * @author STEFAN-DUMITRU PITURU
 * @version 23 Decembrie 2024
 */
@Setter
@Getter
//Clasa pentru definirea erorilor sub un format (timestamp/status/eroare/mesaj/cale)
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    //Declarare variabile
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    //Constructor
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
    }

}
