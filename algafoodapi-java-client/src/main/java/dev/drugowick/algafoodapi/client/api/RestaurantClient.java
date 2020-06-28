package dev.drugowick.algafoodapi.client.api;

import dev.drugowick.algafoodapi.client.model.RestaurantSummaryModel;
import dev.drugowick.algafoodapi.client.model.input.RestaurantInput;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestaurantClient {

    private static final String RESOURCE_PATH = "/restaurants";

    private RestTemplate restTemplate;
    private String url;

    public List<RestaurantSummaryModel> list() {
        try {
            URI resourceUri = URI.create(url + RESOURCE_PATH);
            RestaurantSummaryModel[] restaurants = restTemplate.getForObject(resourceUri, RestaurantSummaryModel[].class);
            return Arrays.asList(restaurants);
        } catch (RestClientResponseException e) {
            throw new ClientApiException("Response Error", e);
        }
    }
    
    public RestaurantSummaryModel get(Long restaurantId) {
        try {
            URI resourceUri = URI.create(url + RESOURCE_PATH + "/" + restaurantId);
            RestaurantSummaryModel restaurant = restTemplate.getForObject(resourceUri, RestaurantSummaryModel.class);
            return restaurant;
        } catch (RestClientResponseException e) {
            throw new ClientApiException("Response Error", e);
        }
    }

    public RestaurantSummaryModel create(RestaurantInput restaurantInput) {
        try {
            URI resourceUri = URI.create(url + RESOURCE_PATH);
            return restTemplate.postForObject(
                    resourceUri, restaurantInput, RestaurantSummaryModel.class);
        } catch (RestClientResponseException e) {
            throw new ClientApiException("Response Error", e);
        }
    }
}
