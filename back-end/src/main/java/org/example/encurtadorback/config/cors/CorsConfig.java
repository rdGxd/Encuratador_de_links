package org.example.encurtadorback.config.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // Método para configurar as regras CORS para a aplicação
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Adiciona uma regra CORS que permite que todas as origens acessem todos os endpoints da aplicação
        registry.addMapping("/**") // Mapeia todas as URLs da aplicação
                .allowedOrigins("*") // Permite todas as origens (qualquer domínio)
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // Permite os métodos HTTP GET, POST, PUT e DELETE
    }
}
