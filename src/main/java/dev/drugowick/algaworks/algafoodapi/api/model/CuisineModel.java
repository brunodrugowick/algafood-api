package dev.drugowick.algaworks.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import dev.drugowick.algaworks.algafoodapi.api.model.view.RestaurantView;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CuisineModel {

    @NotNull
    private Long id;

    @JsonView(RestaurantView.Summary.class)
    @NotBlank
    private String name;
}
