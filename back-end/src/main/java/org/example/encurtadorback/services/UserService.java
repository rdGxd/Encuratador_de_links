package org.example.encurtadorback.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.encurtadorback.config.security.TokenService;
import org.example.encurtadorback.dtos.RegisterDTO;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.repositories.UserRepository;
import org.example.encurtadorback.services.exceptions.DatabaseException;
import org.example.encurtadorback.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Indica que esta classe é um componente Spring e é gerenciada pelo contêiner Spring
public class UserService {
    private final UserRepository repo; // Repositório para interagir com a entidade de usuário
    private final TokenService tokenService; // Serviço para manipulação de tokens JWT

    // Construtor que recebe instâncias do UserRepository e TokenService
    public UserService(UserRepository repo, TokenService tokenService) {
        this.repo = repo;
        this.tokenService = tokenService;
    }

    // Método para encontrar todos os usuários
    public List<User> findAll() {
        // Retorna uma lista de todos os usuários presentes no repositório
        return repo.findAll();
    }

    // Método para encontrar um usuário pelo seu ID
    public User findById(String id) {
        // Busca o usuário pelo ID no repositório. Se não encontrado, lança uma exceção ResourceNotFoundException
        Optional<User> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    // Método para inserir um novo usuário
    public void insert(User user) {
        try {
            // Salva o usuário no repositório
            repo.save(user);
        } catch (RuntimeException e) {
            // Em caso de exceção, lança uma RuntimeException com a mensagem original
            throw new RuntimeException(e.getMessage());
        }
    }

    // Método para deletar um usuário pelo seu ID
    public void delete(String id) {
        try {
            // Tenta excluir o usuário com o ID fornecido
            repo.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            // Lança uma exceção ResourceNotFoundException se o usuário não for encontrado
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção DatabaseException se ocorrer uma violação de integridade dos dados
            throw new DatabaseException(e.getMessage());
        }
    }

    // Método para atualizar um usuário
    public boolean update(String id, RegisterDTO user, String token) {
        // Obtém o ID do usuário associado ao token fornecido
        String userToken = findUserByToken(token).getId();
        // Obtém o ID do usuário associado ao ID fornecido
        String userId = findById(id).getId();
        // Verifica se o usuário associado ao token é o mesmo que está tentando atualizar
        if (!userId.equals(userToken)) return false;
        try {
            // Obtém uma referência do usuário no repositório
            User entity = repo.getReferenceById(id);
            // Atualiza os dados do usuário com os novos valores fornecidos
            updateData(entity, user);
            // Salva as alterações no repositório
            repo.save(entity);
            // Retorna true para indicar que a atualização foi bem-sucedida
            return true;
        } catch (EntityNotFoundException e) {
            // Lança uma exceção se o usuário não for encontrado
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    // Método para encontrar um usuário pelo seu login
    public User findByLogin(String login) {
        // Busca o usuário pelo nome de login no repositório. Se não encontrado, lança uma exceção ResourceNotFoundException
        return repo.findByLogin(login).orElseThrow(() -> new ResourceNotFoundException(login));
    }

    // Método para atualizar os dados de um usuário
    public void updateData(User entity, RegisterDTO user) {
        // Atualiza o nome de login da entidade com o novo nome fornecido
        entity.setLogin(user.login());
        // Atualiza a senha da entidade com a nova senha fornecida
        entity.setPassword(user.password());
    }

    // Método para criar um novo usuário
    public boolean createUser(RegisterDTO data) {
        // Verifica se já existe um usuário com o mesmo nome de login ou se os dados são inválidos
        Optional<User> user = repo.findByLogin(data.login());
        if (user.isPresent() || data.login().length() < 3 || data.login().length() > 16 || data.password().length() < 6 || data.password().length() > 16) {
            return false;
        }
        // Tenta criar um novo usuário
        try {
            // Criptografa a senha fornecida antes de salvar no banco de dados
            String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
            // Cria um novo usuário com os dados fornecidos
            User newUser = new User(data.login(), encryptedPassword, data.role());
            // Insere o novo usuário no banco de dados
            insert(newUser);
            // Retorna true para indicar que o usuário foi criado com sucesso
            return true;
        } catch (RuntimeException e) {
            // Em caso de erro ao criar o usuário, lança uma exceção
            throw new RuntimeException(e.getMessage());
        }
    }

    // Método para encontrar um usuário pelo token JWT
    public User findUserByToken(String token) {
        // Remove o prefixo "Bearer " do token
        token = token.replace("Bearer ", "");
        // Valida o token e obtém o nome de usuário associado a ele
        String username = tokenService.validateToken(token);
        // Encontra e retorna o usuário com base no nome de usuário obtido
        return findByLogin(username);
    }
}
