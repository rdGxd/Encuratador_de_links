package org.example.encurtadorback.services.exceptions;

// Exceção personalizada para indicar que um recurso não foi encontrado
public class ResourceNotFoundException extends RuntimeException {
    // Construtor que recebe o identificador do recurso que não foi encontrado
    public ResourceNotFoundException(Object id) {
        // Chama o construtor da superclasse com uma mensagem que inclui o identificador do recurso
        super("Resource not found. id: " + id);
    }
}
