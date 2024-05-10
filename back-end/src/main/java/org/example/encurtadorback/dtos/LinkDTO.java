package org.example.encurtadorback.dtos;

import java.util.Date;

// Classe DTO (Data Transfer Object) para representar um link com metadados
public record LinkDTO(String link, String original, String id, Date createdAt, Date updatedAt) {
    // Construtor automático fornecido pelo 'record'
}
