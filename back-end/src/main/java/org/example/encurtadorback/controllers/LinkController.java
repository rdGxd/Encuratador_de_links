package org.example.encurtadorback.controllers;

import org.example.encurtadorback.dtos.LinkDTO;
import org.example.encurtadorback.models.link.Link;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.services.LinkService;
import org.example.encurtadorback.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/links")
public class LinkController {

    private final LinkService linkService;
    private final UserService userService;

    // Injeta os serviços necessários no construtor
    public LinkController(LinkService linkService, UserService userService) {
        this.linkService = linkService;
        this.userService = userService;
    }

    // Endpoint para buscar todos os links do usuário
    @GetMapping
    public ResponseEntity<List<LinkDTO>> findAll(@RequestHeader(name = "Authorization") String token) {
        // Encontra o usuário com base no token de autorização fornecido
        User user = userService.findUserByToken(token);
        // Chama o serviço de link para encontrar todos os links associados ao usuário
        List<Link> list = linkService.findAll(user.getId());
        // Cria uma nova lista para armazenar os objetos LinkDTO
        List<LinkDTO> newList = new ArrayList<>();
        // Converte cada link encontrado em um objeto LinkDTO e adiciona à nova lista
        for (Link link : list) {
            newList.add(new LinkDTO(link.getLink(), link.getOriginal(), link.getId(), link.getCreatedAT(), link.getUpdatedAT()));
        }
        // Retorna uma resposta de sucesso com a lista de LinkDTO
        return ResponseEntity.ok().body(newList);
    }

    // Endpoint para buscar um link por ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<LinkDTO> findById(@PathVariable String id, @RequestHeader(name = "Authorization") String token) {
        // Encontra o usuário com base no token de autorização fornecido
        User user = userService.findUserByToken(token);
        // Chama o serviço de link para encontrar o link com o ID fornecido
        Link link = linkService.findById(id, user);
        // Verifica se o link foi encontrado
        if (link != null) {
            // Cria um objeto LinkDTO com os dados do link encontrado
            LinkDTO linkDTO = new LinkDTO(link.getLink(), link.getOriginal(), link.getId(), link.getCreatedAT(), link.getUpdatedAT());
            // Retorna uma resposta de sucesso com o objeto LinkDTO
            return ResponseEntity.ok().body(linkDTO);
        }
        // Retorna uma resposta de erro se o link não for encontrado
        return ResponseEntity.badRequest().build();
    }

    // Endpoint para inserir um novo link
    @PostMapping
    public ResponseEntity<String> insert(@RequestBody @Validated LinkDTO data, @RequestHeader(name = "Authorization") String token) {
        // Encontra o usuário com base no token de autorização fornecido
        User user = userService.findUserByToken(token);
        // Cria um novo link com os dados fornecidos
        Link newLink = new Link(data.link(), data.original(), user);
        // Adiciona o novo link à lista de links do usuário
        user.getLinks().add(newLink);
        // Chama o serviço de link para inserir o novo link
        if (linkService.insert(newLink)) {
            // Retorna uma resposta de sucesso se o link foi inserido com sucesso
            return ResponseEntity.ok().body("Link inserido com sucesso!");
        }
        // Retorna uma resposta de erro se ocorrer algum problema ao inserir o link
        return ResponseEntity.badRequest().body("ERRO AO INSERIR LINK!");
    }

    // Endpoint para atualizar um link existente
    @PutMapping(value = "/{id}")
    public ResponseEntity<String> update(@RequestBody @Validated LinkDTO data, @PathVariable String id, @RequestHeader(name = "Authorization") String token) {
        // Encontra o usuário com base no token de autorização fornecido
        User user = userService.findUserByToken(token);
        // Chama o serviço de link para atualizar o link com os novos dados fornecidos
        if (linkService.update(id, data.link(), data.original(), user) != null) {
            // Retorna uma resposta de sucesso se o link foi atualizado com sucesso
            return ResponseEntity.ok().body("Link Alterado com sucesso!");
        }
        // Retorna uma resposta de erro se ocorrer algum problema ao atualizar o link
        return ResponseEntity.badRequest().body("ERRO AO ATUALIZAR");
    }

    // Endpoint para excluir um link existente
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable String id, @RequestHeader(name = "Authorization") String token) {
        // Encontra o usuário com base no token de autorização fornecido
        User user = userService.findUserByToken(token);
        // Chama o serviço de link para excluir o link com o ID fornecido
        if (linkService.delete(user, id)) {
            // Retorna uma resposta de sucesso se o link foi excluído com sucesso
            return ResponseEntity.ok().body("DELETADO COM SUCESSO");
        }
        // Retorna uma resposta de erro se ocorrer algum problema ao excluir o link
        return ResponseEntity.badRequest().body("ERRO AO REMOVER");
    }
}
