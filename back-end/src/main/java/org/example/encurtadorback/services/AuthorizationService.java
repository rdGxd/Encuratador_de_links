package org.example.encurtadorback.services;

import org.example.encurtadorback.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Serviço responsável por fornecer detalhes do usuário para o Spring Security durante a autenticação
@Service // Indica que esta classe é um componente Spring sendo gerenciada pelo contêiner Spring
public class AuthorizationService implements UserDetailsService {

    final UserRepository repository; // Repositório para interagir com a entidade de usuário

    // Construtor que recebe uma instância do UserRepository
    public AuthorizationService(UserRepository repository) {
        this.repository = repository;
    }

    // Método para carregar detalhes do usuário com base no nome de usuário (login)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca um usuário pelo login no repositório. Se não encontrar, lança uma exceção UsernameNotFoundException
        return repository.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
