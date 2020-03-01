package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "cuisines";

        databaseCleaner.clearTables();
        insertData();
    }

    private void insertData() {
        Cuisine cuisine1 = new Cuisine();
        cuisine1.setName("Italian");
        cuisineRepository.save(cuisine1);

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Brazilian");
        cuisineRepository.save(cuisine2);
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
    public void shouldReturn2Items_WhenListingCuisines() {
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