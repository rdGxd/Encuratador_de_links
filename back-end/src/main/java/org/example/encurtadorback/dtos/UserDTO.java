package org.example.encurtadorback.dtos;

import org.example.encurtadorback.models.user.UserRole;

import java.util.Date;
import java.util.List;

// Classe DTO (Data Transfer Object) para representar um usuário com seus links associados
public record UserDTO(String login, UserRole role, List<LinkDTO> list, String id, Date createdAt, Date updatedAt) {
    // Construtor automático fornecido pelo 'record'
}
