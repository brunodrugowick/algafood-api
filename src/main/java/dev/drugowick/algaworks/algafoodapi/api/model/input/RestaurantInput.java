package dev.drugowick.algaworks.algafoodapi.api.model.input;

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
public class RestaurantInput {

    @NotBlank
    private String name;

    @Multiple(number = 3)
    @DeliveryFee
    private BigDecimal deliveryFee;

    @Valid
    @NotNull
    private CuisineIdInput cuisine;

    @Valid
    @NotNull
    private AddressInput address;

}
