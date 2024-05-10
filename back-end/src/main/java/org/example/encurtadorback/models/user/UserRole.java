package org.example.encurtadorback.models.user;

import lombok.Getter;

@Getter // Anotação Lombok para gerar getters
public enum UserRole {
    ADMIN, // Papel de administrador
    USER // Papel de usuário padrão
}
