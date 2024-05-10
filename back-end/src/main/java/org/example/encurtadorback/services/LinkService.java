package org.example.encurtadorback.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.encurtadorback.models.link.Link;
import org.example.encurtadorback.models.user.User;
import org.example.encurtadorback.models.user.UserRole;
import org.example.encurtadorback.repositories.LinkRepository;
import org.example.encurtadorback.services.exceptions.DatabaseException;
import org.example.encurtadorback.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

@Service // Indica que esta classe é um componente Spring sendo gerenciada pelo contêiner Spring
public class LinkService {

    private final LinkRepository linkRepository; // Repositório para interagir com a entidade de link
    private final JdbcTemplate jdbcTemplate; // JdbcTemplate para executar consultas SQL personalizadas

    // Construtor que recebe instâncias do LinkRepository e JdbcTemplate
    public LinkService(LinkRepository linkRepository, JdbcTemplate jdbcTemplate) {
        this.linkRepository = linkRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    // Método para encontrar todos os links de um usuário pelo seu ID
    public List<Link> findAll(String id) {
        try {
            // Busca todos os links associados ao usuário com o ID fornecido
            return linkRepository.findLinksByUser_Id(id);
        } catch (RuntimeException e) {
            // Em caso de exceção, lança uma nova exceção com a mensagem original
            throw new RuntimeException(e.getMessage());
        }
    }

    // Método para encontrar um link pelo seu ID, verificando se o usuário tem permissão para acessá-lo
    public Link findById(String id, User user) {
        try {
            // Verifica se o link com o ID fornecido existe e se o usuário tem permissão para acessá-lo
            if (linkRepository.findById(id).isPresent() && verifyUserAndRole(id, user)) {
                // Retorna o link correspondente ao ID fornecido
                return linkRepository.findById(id).get();
            }
        } catch (RuntimeException e) {
            // Lança uma exceção de recurso não encontrado se ocorrer um erro durante a busca
            throw new ResourceNotFoundException(id);
        }
        // Retorna null se o link não for encontrado ou se o usuário não tiver permissão
        return null;
    }

    // Método para inserir um novo link
    public boolean insert(Link link) {
        try {
            // Verifica se as URLs fornecidos são válidos
            new URL(link.getOriginal()).toURI();
            new URL(link.getLink()).toURI();
            // Salva o link no repositório
            linkRepository.save(link);
            // Retorna true para indicar que a inserção foi bem-sucedida
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            // Lida com exceções relacionadas a URLs mal formadas
            return false;
        }
    }

    // Método para atualizar um link
    public Link update(String id, String link, String original, User user) {
        try {
            // Verifica se o usuário tem permissão para atualizar o link
            if (verifyUserAndRole(id, user)) {
                // Verifica se as URLs fornecidos são válidos
                new URL(link).toURI();
                new URL(original).toURI();
                // Obtém a referência do link a ser atualizado
                Link entity = linkRepository.getReferenceById(id);
                // Atualiza os dados do link com os novos valores
                updateData(entity, link, original);
                // Salva e retorna o link atualizado
                return linkRepository.save(entity);
            }
        } catch (EntityNotFoundException e) {
            // Lança uma exceção se o link não for encontrado
            throw new EntityNotFoundException(e.getMessage());
        } catch (MalformedURLException | URISyntaxException e) {
            // Lida com exceções relacionadas a URLs mal formadas
            return null;
        }
        // Retorna null se o usuário não tiver permissão ou se ocorrer algum erro
        return null;
    }


    // Método para deletar um link
    public boolean delete(User user, String id) {
        try {
            // Verifica se o usuário tem permissão para excluir o link com o ID fornecido
            if (verifyUserAndRole(id, user)) {
                // Exclui o link da tabela de junção entre usuário e links
                jdbcTemplate.update("DELETE FROM TB_USER_LINKS WHERE LINKS_ID = ?", id);
                // Exclui o link do repositório
                linkRepository.deleteById(id);
            }
            // Retorna true para indicar que a exclusão foi bem-sucedida
            return true;
        } catch (EmptyResultDataAccessException e) {
            // Lança uma exceção de recurso não encontrado se o link não for encontrado
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            // Lança uma exceção de banco de dados se ocorrer uma violação de integridade
            throw new DatabaseException(e.getMessage());
        }
    }

    // Método para atualizar os dados de um link
    public void updateData(Link entity, String link, String original) {
        // Atualiza o link da entidade com o novo link fornecido
        entity.setLink(link);
        // Atualiza o link original da entidade com o novo link original fornecido
        entity.setOriginal(original);
    }

    // Método para verificar se o usuário tem permissão para acessar o link
    public boolean verifyUserAndRole(String id, User user) {
        // Verifica se o link com o ID fornecido existe
        if (linkRepository.findById(id).isPresent()) {
            // Obtém o usuário associado ao link
            User linkUser = linkRepository.findById(id).get().getUser();
            // Verifica se o usuário é o proprietário do link ou se é um administrador
            return linkUser.getId().equals(user.getId()) || linkUser.getRole().equals(UserRole.ADMIN);
        }
        // Retorna false se o link não for encontrado
        return false;
    }
}
