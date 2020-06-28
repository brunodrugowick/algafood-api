package dev.drugowick.algafoodapi.client.model.input;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class RestaurantInput {

    private String name;
    private BigDecimal deliveryFee;
    private CuisineInput cuisine;
    private AddressInput address;
}
