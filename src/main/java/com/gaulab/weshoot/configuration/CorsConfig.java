package com.gaulab.weshoot.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Configuración de CORS global para permitir cualquier origen
        registry.addMapping("/**")  // Esto aplica a todos los endpoints
                .allowedOrigins("*") // Permite cualquier origen
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // Métodos permitidos
                .allowedHeaders("*"); // Permite cualquier encabezado
                // .allowCredentials(true); // Permite el uso de credenciales (opcional)
    }
}