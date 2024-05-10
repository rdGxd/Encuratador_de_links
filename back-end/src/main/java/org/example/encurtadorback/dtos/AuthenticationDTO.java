package org.example.encurtadorback.dtos;

// Classe DTO (Data Transfer Object) para autenticação de usuário
public record AuthenticationDTO(String login, String password) {
    // Construtor automático fornecido pelo 'record'
}
