package dev.drugowick.algaworks.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import dev.drugowick.algaworks.algafoodapi.api.model.view.RestaurantView;
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

    @JsonView({ RestaurantView.Summary.class, RestaurantView.NameOnly.class })
    @NotNull
    private Long id;

    @JsonView({ RestaurantView.Summary.class, RestaurantView.NameOnly.class })
    @NotBlank
    private String name;

    @Multiple(number = 3)
    @DeliveryFee
    private BigDecimal delivery;

    @JsonView(RestaurantView.Summary.class)
    @NotNull
    @Valid
    private CuisineModel cuisine;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean opened;

    private AddressModel address;
}
