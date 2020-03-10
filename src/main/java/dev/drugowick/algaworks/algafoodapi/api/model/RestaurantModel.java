package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class RestaurantModel {

    private Long id;
    private String name;
    private BigDecimal delivery;
    private CuisineModel cuisine;
}
