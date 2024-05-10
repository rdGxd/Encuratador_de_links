package org.example.encurtadorback.services.exceptions;

// Exceção personalizada para erros relacionados ao banco de dados
public class DatabaseException extends RuntimeException {
    // Construtor que recebe uma mensagem de erro
    public DatabaseException(String message) {
        super(message);
    }
}
