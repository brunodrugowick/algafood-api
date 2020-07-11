package dev.drugowick.algaworks.algafoodapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class OpenApiConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("dev.drugowick"))
                    .paths(PathSelectors.any())
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalGetReponseMessages())
                .apiInfo(apiInfo())
                .tags(new Tag("Cities", "Manages cities."));
    }

    private List<ResponseMessage> globalGetReponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Internal server error").build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_ACCEPTABLE.value())
                    .message("There's no valid representation that the client accepts").build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Resource not found").build()
        );
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
