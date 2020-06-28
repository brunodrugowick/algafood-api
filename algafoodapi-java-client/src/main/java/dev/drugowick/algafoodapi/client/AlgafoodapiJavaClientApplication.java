package dev.drugowick.algafoodapi.client;

import dev.drugowick.algafoodapi.client.api.ClientApiException;
import dev.drugowick.algafoodapi.client.api.OrderClient;
import dev.drugowick.algafoodapi.client.api.PaymentMethodClient;
import dev.drugowick.algafoodapi.client.api.RestaurantClient;
import dev.drugowick.algafoodapi.client.model.input.AddressInput;
import dev.drugowick.algafoodapi.client.model.input.CityInput;
import dev.drugowick.algafoodapi.client.model.input.CuisineInput;
import dev.drugowick.algafoodapi.client.model.input.RestaurantInput;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

//@SpringBootApplication
public class AlgafoodapiJavaClientApplication {
    
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String url = "http://api.algafood.local:8080";

    public static void main(String[] args) {
//        SpringApplication.run(AlgafoodapiJavaClientApplication.class, args);
        if (args.length < 1) return;
        switch (args[0]) {
            case "getRestaurants": {
                try {

                    RestaurantClient restaurantClient = new RestaurantClient(restTemplate, url);
                    restaurantClient.list()
                            .forEach(System.out::println);
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) System.out.println(e.getProblem().getUserMessage());
                    else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "getRestaurant": {
                try {
                    RestaurantClient restaurantClient = new RestaurantClient(restTemplate, url);
                    System.out.println(restaurantClient.get(Long.parseLong(args[1])));
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) System.out.println(e.getProblem().getUserMessage());
                    else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "getPaymentMethods": {
                try {
                    PaymentMethodClient paymentMethodClient = new PaymentMethodClient(new RestTemplate(), url);
                    paymentMethodClient.list()
                            .forEach(System.out::println);
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) System.out.println(e.getProblem().getUserMessage());
                    else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "getOrders": {
                try {
                    OrderClient orderClient = new OrderClient(new RestTemplate(), url);
                    orderClient.list()
                            .forEach(System.out::println);
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) System.out.println(e.getProblem().getUserMessage());
                    else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
                break;
            }
            case "getOrderResponse": {
                try {
                    OrderClient orderClient = new OrderClient(new RestTemplate(), url);
                    System.out.println(orderClient.response());
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) System.out.println(e.getProblem().getUserMessage());
                    else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
                break;
            } case "postRestaurant": {
                try {
                    RestaurantClient restaurantClient = new RestaurantClient(new RestTemplate(), url);
                    RestaurantInput restaurantInput = RestaurantInput.builder()
                            .name(args[1])
                            .cuisine(CuisineInput.builder().id(1L).build())
                            .deliveryFee(BigDecimal.valueOf(9L))
                            .address(AddressInput.builder()
                                    .postalCode("13012")
                                    .addressLine_1("Cocada Street, 123454")
                                    .addressLine_2("Around")
                                    .region("Barbada")
                                    .city(CityInput.builder().id(1L).build())
                                    .build())
                            .build();
                    System.out.println(restaurantClient.create(restaurantInput));
                } catch (ClientApiException e) {
                    if (e.getProblem() != null) {
                        System.out.println(e.getProblem().getUserMessage());
                        if (e.getProblem().getErrorObjects() != null) {
                            e.getProblem().getErrorObjects().forEach(
                                    object -> System.out.println("- " + object.getName() + " " + object.getUserMessage()));
                        }
                    } else {
                        System.out.println("Unknown error");
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
