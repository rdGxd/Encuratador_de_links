package org.example.encurtadorback.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.repositories.UserRepository;
import org.example.encurtadorback.services.exceptions.ResourceNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public SecurityFilter(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Recupera o token de autorização da requisição
        String token = this.recoverToken(request);
        // Valida o token para obter o nome de usuário associado a ele
        String login = tokenService.validateToken(token);
        // Verifica se o token é válido e se um nome de usuário foi obtido
        if (token != null) {
            // Busca o usuário correspondente no banco de dados pelo nome de login
            User user = userRepository.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException(login));
            // Cria uma autenticação com base no usuário e suas permissões
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            // Define a autenticação no contexto de segurança
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Encaminha a requisição para o próximo filtro na cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // Recupera o token do cabeçalho da requisição
    private String recoverToken(HttpServletRequest request) {
        // Obtém o cabeçalho de autorização da solicitação HTTP
        String authHeader = request.getHeader("Authorization");
        // Verifica se o cabeçalho de autorização não é nulo
        if (authHeader == null) {
            return null; // Retorna null se o cabeçalho de autorização não estiver presente
        }
        // Remove o prefixo "Bearer " do token, se presente, e retorna o token
        return authHeader.replace("Bearer ", "");
    }
}
