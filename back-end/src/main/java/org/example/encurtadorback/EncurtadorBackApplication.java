package org.example.encurtadorback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Esta anotação informa ao Spring Boot que esta é a classe principal da aplicação e que deve ser inicializada
public class EncurtadorBackApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncurtadorBackApplication.class, args); // Este método inicia a aplicação Spring Boot
    }
}
