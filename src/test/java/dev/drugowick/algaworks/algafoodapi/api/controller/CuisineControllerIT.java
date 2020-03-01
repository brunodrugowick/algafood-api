package dev.drugowick.algaworks.algafoodapi.api.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource("/application-test.properties")
class CuisineControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private Flyway flyway;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "cuisines";

        flyway.migrate();
    }

    @Test
    public void shouldReturn200_WhenListingCuisines() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn4Items_WhenListingCuisines() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(2))
                .body("name", hasItems("Italian", "Brazilian"));
    }

    @Test
    public void shouldReturn201_WhenCreatingCuisine() {
        given()
            .body("{\"name\": \"Taiwanese\"}")
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }
}