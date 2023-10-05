package com.viewnext.kidaprojects.apipersonajes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * La clase {@code WebClientConfig} proporciona configuración para crear un WebClient utilizado
 * para realizar solicitudes HTTP a una API remota.
 *
 * <p>
 * El autor de esta clase es Víctor Colorado "Kid A".
 * </p>
 *
 * @version 1.0
 * @since 04 de Octubre de 2023
 */
@Configuration
public class WebClientConfig {

    private static final String BASE_URL_MISION = "http://localhost:8084";
    private static final String BASE_URL_ENEMIGO = "http://localhost:8083";

    /**
     * Crea y configura un WebClient para interactuar con una API remota en la
     * ubicación especificada por BASE_URL.
     *
     * @return Un objeto WebClient configurado.
     */
    @Bean
    WebClient misionWebClient() {
        String apiUrl = BASE_URL_MISION;
        return WebClient.create(apiUrl);
    }
    
    

    /**
     * Crea y configura un WebClient para interactuar con una API remota en la
     * ubicación especificada por BASE_URL.
     *
     * @return Un objeto WebClient configurado.
     */
    @Bean
    WebClient enemigoWebClient() {
        String apiUrl = BASE_URL_ENEMIGO;
        return WebClient.create(apiUrl);
    }
    
}
