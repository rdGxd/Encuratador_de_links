package org.example.encurtadorback.controllers.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Setter
@Getter
public class StandardError implements Serializable {
    // Formato JSON para a serialização da data e hora usando o formato ISO 8601
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant timestamp; // Momento em que o erro ocorreu
    private Integer status; // Código de status HTTP associado ao erro
    private String error; // Descrição do tipo de erro
    private String message; // Mensagem detalhada sobre o erro
    private String path; // URI onde ocorreu o erro

    // Construtor padrão vazio
    public StandardError() {
    }

    // Construtor para criar um objeto StandardError com os detalhes do erro
    public StandardError(Instant timestamp, Integer status, String error, String message, String path) {
        this.timestamp = timestamp; // Define o momento do erro
        this.status = status; // Define o código de status do erro
        this.error = error; // Define a descrição do tipo de erro
        this.message = message; // Define a mensagem detalhada sobre o erro
        this.path = path; // Define a URI onde ocorreu o erro
    }

}
