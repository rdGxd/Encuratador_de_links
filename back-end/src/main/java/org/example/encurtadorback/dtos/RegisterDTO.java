package org.example.encurtadorback.dtos;

import org.example.encurtadorback.models.user.UserRole;

// Classe DTO (Data Transfer Object) para registrar um novo usuário
public record RegisterDTO(String login, String password, UserRole role) {
    // Construtor automático fornecido pelo 'record'
}
