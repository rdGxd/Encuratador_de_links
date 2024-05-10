package org.example.encurtadorback.repositories;

import org.example.encurtadorback.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Indica que esta interface é um componente Spring e gerência a persistência da entidade User
public interface UserRepository extends JpaRepository<User, String> {
    // Método para encontrar um usuário pelo seu login
    Optional<User> findByLogin(String login);
}
