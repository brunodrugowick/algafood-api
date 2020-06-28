package dev.drugowick.algafoodapi.client.api;

import dev.drugowick.algafoodapi.client.model.PaymentMethodModel;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class PaymentMethodClient {

    private static final String RESOURCE_PATH = "/payment-methods";

    private RestTemplate restTemplate;
    private String url;

    public List<PaymentMethodModel> list() {
        URI resourceUri = URI.create(url + RESOURCE_PATH);
        PaymentMethodModel[] paymentMethods = restTemplate.getForObject(resourceUri, PaymentMethodModel[].class);
        return Arrays.asList(paymentMethods);
    }
}
