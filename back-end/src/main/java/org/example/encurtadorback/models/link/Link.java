package org.example.encurtadorback.models.link;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.encurtadorback.models.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Setter
@Getter
@Table(name = "tb_link") // Mapeamento para a tabela no banco de dados
@Entity // Indica que essa classe é uma entidade JPA
@NoArgsConstructor // Cria um construtor padrão sem argumentos
@AllArgsConstructor // Cria um construtor que inicializa todos os campos
@EqualsAndHashCode(of = "id") // Implementa equals e hashCode baseado no campo id
public class Link {
    @Id // Indica que esse campo é a chave primária da entidade
    @GeneratedValue(strategy = GenerationType.UUID) // Define a estratégia de geração de valores para o campo id
    private String id; // Identificador único do link

    @NonNull // Indica que o campo não pode ser nulo
    private String link; // link com a URL encurtada

    @NonNull // Indica que o campo não pode ser nulo
    private String original; // link Original da url

    @JsonIgnore // Indica que esse campo não será serializado em JSON
    @ManyToOne // Mapeamento de muitos links para um usuário
    @NonNull // Indica que o campo não pode ser nulo
    private User user; // Usuário proprietário do link

    @NonNull // Indica que o campo não pode ser nulo
    @CreationTimestamp // Anotação para preencher automaticamente o campo com a data de criação
    private Date createdAT;

    @NonNull // Indica que o campo não pode ser nulo
    @UpdateTimestamp // Anotação para preencher automaticamente o campo com a data de atualização
    private Date updatedAT;

    // Construtor para inicializar os campos link e user
    public Link(@NonNull String link, @NonNull String original, @NonNull User username) {
        this.link = link;
        this.user = username;
        this.original = original;
    }
}
