package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
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

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
class RestaurantControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CuisineRepository cuisineRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private int numRestaurants;
    private String jsonPostRestaurant;
    private String jsonPostRestaurant_WrongDeliveryFee;
    private Restaurant restaurant;
    private Cuisine cuisine;

    @BeforeEach
    void setUp() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "restaurants";

        databaseCleaner.clearTables();
        insertData();

        jsonPostRestaurant = ResourceUtils.getContentFromResource("/json/post-restaurant.json");
        jsonPostRestaurant_WrongDeliveryFee = ResourceUtils.getContentFromResource("/json/post-restaurant-wrong-delivery-fee.json");

        // Sets the cuisine id to a valid value
        jsonPostRestaurant = jsonPostRestaurant.replace("__cuisine_id__", cuisine.getId().toString());
    }

    private void insertData() {
        cuisine = new Cuisine();
        cuisine.setName("Cuisine Test");
        cuisine = cuisineRepository.save(cuisine);

        restaurant = new Restaurant();
        restaurant.setDeliveryFee(BigDecimal.TEN);
        restaurant.setName("Restaurant Test");
        restaurant.setCuisine(cuisine);
        restaurant = restaurantRepository.save(restaurant);

        numRestaurants = (int) restaurantRepository.count();
    }

    @Test
    void shouldReturn200_WhenGettingRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void shouldReturnCorrectNumberOfItems_WhenGettingRestaurants() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
        .body("", hasSize(numRestaurants));
    }

    @Test
    void shouldReturn201_WhenCreatingRestaurant() {
        given()
            .body(jsonPostRestaurant)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    void shouldReturn400_WhenCreatingRestaurantWithWrongDeliveryFee() {
        given()
            .body(jsonPostRestaurant_WrongDeliveryFee)
            .contentType(ContentType.JSON)
            .accept(ContentType.JSON)
        .when()
            .post()
        .then()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldReturnDataAndHttpStatusCode_WhenGettingRestaurant() {
        given()
            .accept(ContentType.JSON)
            .pathParam("restaurantId", restaurant.getId())
        .when()
            .get("/{restaurantId}")
        .then()
            .body("name", equalTo(restaurant.getName()))
            .statusCode(HttpStatus.OK.value());
    }

    @Test
    void update() {
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void delete() {
    }
}