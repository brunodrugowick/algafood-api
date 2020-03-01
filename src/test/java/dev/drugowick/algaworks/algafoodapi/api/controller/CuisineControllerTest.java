package dev.drugowick.algaworks.algafoodapi.api.controller;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CuisineControllerTest {

    @LocalServerPort
    private int port;

    @Test
    public void shouldReturn200_WhenListingCuisines() {

        enableLoggingOfRequestAndResponseIfValidationFails();

        given()
            .basePath("/cuisines")
            .port(port)
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn4Items_WhenListingCuisines() {
        enableLoggingOfRequestAndResponseIfValidationFails();

        given()
                .basePath("/cuisines")
                .port(port)
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(2))
                .body("name", hasItems("Italian", "Brazilian"));
    }
}