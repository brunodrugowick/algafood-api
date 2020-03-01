package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.util.DatabaseCleaner;
import dev.drugowick.algaworks.algafoodapi.util.ResourceUtils;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class CuisineControllerIT {

    private static final int INVALID_CUISINE_ID = -10;

    @LocalServerPort
    private int port;

    private int numCuisines;
    private String jsonPostCuisine;
    private Cuisine italianCuisine;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CuisineRepository cuisineRepository;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "cuisines";

        jsonPostCuisine = ResourceUtils.getContentFromResource("/json/post-cuisine.json");

        databaseCleaner.clearTables();
        insertData();
    }

    private void insertData() {
        italianCuisine = new Cuisine();
        italianCuisine.setName("Italian");
        cuisineRepository.save(italianCuisine);

        Cuisine cuisine2 = new Cuisine();
        cuisine2.setName("Brazilian");
        cuisineRepository.save(cuisine2);

        numCuisines = (int) cuisineRepository.count();
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
    public void shouldReturnCorrectNumberOfItems_WhenListingCuisines() {
        given()
                .accept(ContentType.JSON)
        .when()
                .get()
        .then()
                .body("", hasSize(numCuisines));
    }

    @Test
    public void shouldReturn201_WhenCreatingCuisine() {
        given()
            .body(jsonPostCuisine)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void shouldReturnDataAndHttpStatusCode_WhenGettingCuisine() {
        given()
            .accept(ContentType.JSON)
            .pathParam("cuisineId", italianCuisine.getId())
        .when()
            .get("/{cuisineId}")
        .then()
            .body("name", equalTo(italianCuisine.getName()))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldReturn404_WhenGettingNonExistentCuisine() {
        given()
            .accept(ContentType.JSON)
            .pathParam("cuisineId", INVALID_CUISINE_ID)
        .when()
            .get("/{cuisineId}")
        .then()
            .statusCode(HttpStatus.NOT_FOUND.value());
    }
}