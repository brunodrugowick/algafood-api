package dev.drugowick.algaworks.algafoodapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class OpenApiConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("dev.drugowick"))
                    .paths(PathSelectors.any())
                    .build()
                .apiInfo(apiInfo())
                .tags(new Tag("Cities", "Manages cities."));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Drugo AlgafoodAPI")
                .description("Algafood API is a REST API for a food delivery solution")
                .version("1.0.0")
                .contact(new Contact("Bruno Drugowick", "http://drugo.dev", "bruno.drugowick@gmail.com"))
                .build();
    }
}
