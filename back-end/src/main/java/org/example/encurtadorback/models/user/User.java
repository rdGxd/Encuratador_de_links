package org.example.encurtadorback.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.encurtadorback.models.link.Link;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

// Entidade JPA que representa um usuário no sistema
@Table(name = "tb_user") // Mapeamento para a tabela no banco de dados
@Entity // Indica que essa classe é uma entidade JPA
@Setter // Anotação Lombok para gerar setters
@Getter // Anotação Lombok para gerar getters
@NoArgsConstructor // Cria um construtor padrão sem argumentos
@AllArgsConstructor // Cria um construtor que inicializa todos os campos
@EqualsAndHashCode(of = "id") // Implementa equals e hashCode baseado no campo id
public class User implements UserDetails, Serializable {
    @Id // Indica que esse campo é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.UUID) // Define a estratégia de geração de valores para o campo id
    private String id; // Identificador único do usuário

    @Column(unique = true) // Indica que o valor do campo deve ser único na tabela
    @NonNull // Indica que o campo não pode ser nulo
    private String login; // Login do usuário

    @NonNull // Indica que o campo não pode ser nulo
    private String password; // Senha do usuário

    @NonNull // Indica que o campo não pode ser nulo
    @Enumerated(EnumType.STRING)
    private UserRole role; // Papel (UserRole) do usuário

    @NonNull // Indica que o campo não pode ser nulo
    @CreationTimestamp // Anotação para preencher automaticamente o campo com a data de criação
    private Date createdAT; // Data de criação do usuário

    @NonNull // Indica que o campo não pode ser nulo
    @UpdateTimestamp // Anotação para preencher automaticamente o campo com a data de atualização
    private Date updatedAT; // Data de atualização do usuário

    @OneToMany(cascade = CascadeType.ALL) // Relacionamento um-para-muitos com a entidade Link
    private List<Link> links = new ArrayList<>(); // Lista de links associados ao usuário

    // Construtor para inicializar os campos login, password e role
    public User(@NonNull String login, @NonNull String encryptedPassword, @NonNull UserRole role) {
        this.login = login;
        this.password = encryptedPassword;
        this.role = role;
    }

    // Método para obter as autorizações do usuário (para o Spring Security)
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) { // Se o usuário tiver papel de ADMIN
            // Retorna uma lista de autoridades contendo ROLE_ADMIN e ROLE_USER
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else { // Se o usuário tiver outro papel
            // Retorna uma lista de autoridades contendo apenas ROLE_USER
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    // Método para obter o nome de usuário (para o Spring Security)
    @Override
    @JsonIgnore
    public String getUsername() {
        return getLogin();
    }

    // Método para obter a senha (para o Spring Security)
    @JsonIgnore
    public @NonNull String getPassword() {
        return password;
    }

    // Métodos para verificar se a conta do usuário está expirada, bloqueada, com credenciais expiradas ou ativada (para o Spring Security)
    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
