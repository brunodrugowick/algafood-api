package dev.drugowick.algaworks.algafoodapi.config;

import com.fasterxml.classmate.TypeResolver;
import dev.drugowick.algaworks.algafoodapi.api.controller.openapi.model.PageableModelOpenApi;
import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class OpenApiConfig {

    @Bean
    public static Docket apiDocket(OpenApiConfig openApiConfig) {
        var typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("dev.drugowick"))
                    .paths(PathSelectors.any())
                    .build()
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, openApiConfig.globalGetResponseMessages())
                .globalResponseMessage(RequestMethod.POST, openApiConfig.globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.PUT, openApiConfig.globalPostPutResponseMessages())
                .globalResponseMessage(RequestMethod.DELETE, openApiConfig.globalDeleteResponseMessages())
                .apiInfo(openApiConfig.apiInfo())
                .additionalModels(typeResolver.resolve(ApiError.class))
                .ignoredParameterTypes(ignoredClasses())
                .directModelSubstitute(Pageable.class, PageableModelOpenApi.class)
                .tags(tags()[0], tags());
    }

    private static Class[] ignoredClasses() {
        return new Class[]{
                ServletWebRequest.class,
                Principal.class
        };
    }

    private static Tag[] tags() {
        return new Tag[]{
                new Tag("Cities", "Manages cities."),
                new Tag("Groups", "Manages groups."),
                new Tag("Cuisines", "Manages cuisines"),
                new Tag("Payment Methods", "Manages payment methods"),
                new Tag("Orders", "Manages orders"),
                new Tag("Restaurants", "Manages restaurants")
        };
    }

    private List<ResponseMessage> globalGetResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .message("Internal server error")
                    .responseModel(new ModelRef("ApiError"))
                    .responseModel(new ModelRef("ApiError")).build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_ACCEPTABLE.value())
                    .message("There's no valid representation that the client accepts").build(),
                new ResponseMessageBuilder()
                    .code(HttpStatus.NOT_FOUND.value())
                    .message("Resource not found")
                    .responseModel(new ModelRef("ApiError")).build()
        );
    }

    private List<ResponseMessage> globalPostPutResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request (client error)")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.NOT_ACCEPTABLE.value())
                        .message("There's no valid representation that the client accepts")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value())
                        .message("Request body is in an unsupported format")
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.CONFLICT.value())
                        .message("Operation requested cnoflicts with another entity")
                        .build()
        );
    }

    private List<ResponseMessage> globalDeleteResponseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid request (client error)")
                        .responseModel(new ModelRef("ApiError"))
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message("Internal server error")
                        .responseModel(new ModelRef("ApiError"))
                        .build()
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
