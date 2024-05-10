package org.example.encurtadorback.controllers;

import org.example.encurtadorback.config.security.TokenService;
import org.example.encurtadorback.dtos.AuthenticationDTO;
import org.example.encurtadorback.dtos.LoginResponseDTO;
import org.example.encurtadorback.dtos.RegisterDTO;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    // Injeta os serviços necessários no construtor
    public AuthenticationController(UserService userService, TokenService tokenService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    // Endpoint para login de usuário
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationDTO data) {
        // Busca o usuário pelo nome de login fornecido
        User user = userService.findByLogin(data.login());
        // Verifica se a senha fornecida corresponde à senha armazenada para o usuário
        if (passwordEncoder.matches(data.password(), user.getPassword())) {
            // Gera um token de autenticação para o usuário
            String token = tokenService.generateToken(user);
            // Retorna uma resposta de sucesso com o token de autenticação
            return ResponseEntity.ok().body(new LoginResponseDTO(token));
        }
        // Se as credenciais não corresponderem, retorna uma resposta de erro
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para registro de novo usuário
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterDTO data) {
        // Tenta criar um novo usuário com base nos dados fornecidos
        if (userService.createUser(data)) {
            // Se o usuário for criado com sucesso, retorna uma resposta de sucesso
            return ResponseEntity.ok().build();
        }
        // Se ocorrer algum erro durante o registro, retorna uma resposta de erro
        return ResponseEntity.badRequest().build();
    }
}
