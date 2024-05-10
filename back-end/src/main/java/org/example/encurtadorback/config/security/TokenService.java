package org.example.encurtadorback.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.encurtadorback.models.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // Declara uma variável para armazenar o valor do token secreto
    // A anotação @Value é usada para injetar o valor da propriedade api.security.token.secret do arquivo de configuração
    @Value("${api.security.token.secret}")
    private String secret; // Variável para armazenar o token secreto

    // Método para gerar um token JWT com base no usuário fornecido
    public String generateToken(User user) {
        try {
            // Cria um algoritmo de assinatura HMAC256 com o segredo fornecido
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Cria e retorna o token JWT
            return JWT.create()
                    .withIssuer("auth-api") // Define o emissor do token
                    .withSubject(user.getLogin()) // Define o assunto do token como o nome de login do usuário
                    .withExpiresAt(genExpirationDate()) // Define a data de expiração do token
                    .sign(algorithm); // Assina o token com o algoritmo criado
        } catch (JWTCreationException e) {
            // Captura exceções de criação do token e lança uma RuntimeException
            throw new RuntimeException("Error while generating token", e);
        }
    }

    // Método para validar e decodificar um token JWT
    public String validateToken(String token) {
        try {
            // Cria um algoritmo de verificação HMAC256 com o segredo fornecido
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Verifica e decodifica o token JWT
            return JWT.require(algorithm) // Requer que o token seja assinado com o algoritmo fornecido
                    .withIssuer("auth-api") // Verifica se o emissor do token é "auth-api"
                    .build()
                    .verify(token) // Verifica o token JWT
                    .getSubject(); // Obtém o assunto (subject) do token (normalmente, o nome de usuário)
        } catch (JWTVerificationException e) {
            // Se houver uma exceção ao verificar o token, retorna null
            return null;
        }
    }

    // Método para gerar a data de expiração do token
    private Instant genExpirationDate() {
        // Obtém a data e hora atual
        LocalDateTime expirationDateTime = LocalDateTime.now().plusHours(2); // Adiciona 2 horas à data e hora atual
        // Converte a data e hora para um Instant usando o deslocamento de fuso horário correto
        // Retorna o Instant representando a data de expiração do token
        return expirationDateTime.toInstant(ZoneOffset.of("-03:00"));
    }
}
