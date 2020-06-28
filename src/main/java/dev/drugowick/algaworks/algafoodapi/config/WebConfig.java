package dev.drugowick.algaworks.algafoodapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                // Kept for reference. This is default.
                .allowedOrigins("*")
                // Kept for reference. This is default.
                .maxAge(30);
    }
}
