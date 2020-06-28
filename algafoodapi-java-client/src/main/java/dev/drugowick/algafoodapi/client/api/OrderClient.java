package dev.drugowick.algafoodapi.client.api;

import dev.drugowick.algafoodapi.client.model.OrderModel;
import dev.drugowick.algafoodapi.client.model.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
public class OrderClient {

    private static final String RESOURCE_PATH = "/orders";

    private RestTemplate restTemplate;
    private String url;

    public List<OrderModel> list() {
        URI resourceUri = URI.create(url + RESOURCE_PATH);
        OrderResponse orderResponse = restTemplate.getForObject(resourceUri, OrderResponse.class);
        return orderResponse.getContent();
    }
    
    public OrderResponse response() {
        URI resourceUri = URI.create(url + RESOURCE_PATH);
        return restTemplate.getForObject(resourceUri, OrderResponse.class);
    }
}
