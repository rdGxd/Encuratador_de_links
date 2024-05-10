package org.example.encurtadorback.repositories;

import org.example.encurtadorback.models.link.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Indica que esta interface é um componente Spring e gerência a persistência da entidade Link
public interface LinkRepository extends JpaRepository<Link, String> {
    // Método para encontrar todos os links de um usuário pelo seu ID
    List<Link> findLinksByUser_Id(String id);
}
