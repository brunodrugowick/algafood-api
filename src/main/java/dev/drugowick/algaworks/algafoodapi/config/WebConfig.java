package dev.drugowick.algaworks.algafoodapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;

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

    /**
     * Register the ShallowEtagHeaderFilter, an implementation from Spring to provide Entity Tags on the response
     * headers and to automatically return 304 (not modified) when response content matches client's staled cached
     * data.
     *
     * @return
     */
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}
