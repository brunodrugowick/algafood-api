package dev.drugowick.algaworks.algafoodapi.api.model;

import dev.drugowick.algaworks.algafoodapi.domain.validation.DeliveryFee;
import dev.drugowick.algaworks.algafoodapi.domain.validation.Multiple;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Setter
@Getter
public class RestaurantModel {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @Multiple(number = 3)
    @DeliveryFee
    private BigDecimal delivery;

    @NotNull
    @Valid
    private CuisineModel cuisine;
}
