package org.example.encurtadorback.controllers;

import org.example.encurtadorback.dtos.LinkDTO;
import org.example.encurtadorback.dtos.RegisterDTO;
import org.example.encurtadorback.dtos.UserDTO;
import org.example.encurtadorback.models.link.Link;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        // Busca todos os usuários
        List<User> list = userService.findAll();
        List<UserDTO> listDTO = new ArrayList<>();
        // Para cada usuário encontrado, converte seus links em objetos LinkDTO
        for (User user : list) {
            List<LinkDTO> linkDTO = new ArrayList<>();
            for (Link link : user.getLinks()) {
                linkDTO.add(new LinkDTO(link.getLink(), link.getOriginal(), link.getId(), link.getCreatedAT(), link.getUpdatedAT()));
            }
            // Cria um objeto UserDTO com as informações do usuário e seus links associados
            listDTO.add(new UserDTO(user.getLogin(), user.getRole(), linkDTO, user.getId(), user.getCreatedAT(), user.getUpdatedAT()));
        }
        // Retorna uma resposta de sucesso contendo a lista de UserDTO
        return ResponseEntity.ok().body(listDTO);
    }

    // Endpoint para buscar um usuário por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable String id) {
        // Busca o usuário pelo ID
        User user = userService.findById(id);
        // Verifica se o usuário foi encontrado
        if (user != null) {
            // Converte os links do usuário em objetos LinkDTO
            List<LinkDTO> linkDTO = new ArrayList<>();
            for (Link link : user.getLinks()) {
                linkDTO.add(new LinkDTO(link.getLink(), link.getOriginal(), link.getId(), link.getCreatedAT(), link.getUpdatedAT()));
            }
            // Cria um objeto UserDTO com as informações do usuário e seus links associados
            UserDTO userDTO = new UserDTO(user.getLogin(), user.getRole(), linkDTO, user.getId(), user.getCreatedAT(), user.getUpdatedAT());
            // Retorna uma resposta de sucesso com o objeto UserDTO
            return ResponseEntity.ok().body(userDTO);
        }
        // Retorna uma resposta indicando que o usuário não foi encontrado
        return ResponseEntity.notFound().build();
    }

    // Endpoint para inserir um novo usuário
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody RegisterDTO data) {
        // Chama o serviço de usuário para criar um novo usuário com os dados fornecidos
        if (userService.createUser(data)) {
            // Retorna uma resposta de sucesso se o usuário foi criado com sucesso
            return ResponseEntity.ok().body("CRIADO COM SUCESSO");
        }
        // Retorna uma resposta de erro se ocorrer algum problema ao criar o usuário
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para excluir um usuário por ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        // Chama o serviço de usuário para excluir o usuário com o ID fornecido
        userService.delete(id);
        // Retorna uma resposta indicando que a operação foi realizada com sucesso
        return ResponseEntity.noContent().build(); // Retorna uma resposta vazia
    }

    // Endpoint para atualizar um usuário por ID
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody RegisterDTO data, @RequestHeader(name = "Authorization") String token) {
        // Chama o serviço de usuário para atualizar o usuário com o ID fornecido e os novos dados fornecidos
        if (userService.update(id, data, token)) {
            // Retorna uma resposta de sucesso se o usuário foi atualizado com sucesso
            return ResponseEntity.ok().body("ATUALIZADO COM SUCESSO");
        }
        // Retorna uma resposta de erro se ocorrer algum problema ao atualizar o usuário
        return ResponseEntity.badRequest().build();
    }
}
