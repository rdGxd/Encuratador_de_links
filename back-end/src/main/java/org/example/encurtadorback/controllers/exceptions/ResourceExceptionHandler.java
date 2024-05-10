package org.example.encurtadorback.controllers.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.example.encurtadorback.services.exceptions.DatabaseException;
import org.example.encurtadorback.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    // Manipulador de exceções para ResourceNotFoundException
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found"; // Mensagem de erro
        HttpStatus status = HttpStatus.NOT_FOUND; // Status HTTP 404 Not Found
        // Cria um objeto StandardError com informações sobre o erro
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        // Retorna uma resposta com o status HTTP e o corpo contendo o erro
        return ResponseEntity.status(status).body(err);
    }

    // Manipulador de exceções para DatabaseException
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request) {
        String error = "Database error"; // Mensagem de erro
        HttpStatus status = HttpStatus.BAD_REQUEST; // Status HTTP 400 Bad Request
        // Cria um objeto StandardError com informações sobre o erro
        StandardError err = new StandardError(Instant.now(), status.value(), error, e.getMessage(), request.getRequestURI());
        // Retorna uma resposta com o status HTTP e o corpo contendo o erro
        return ResponseEntity.status(status).body(err);
    }
}
