package dev.drugowick.algaworks.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import dev.drugowick.algaworks.algafoodapi.api.model.view.RestaurantView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class CuisineModel {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;

    @ApiModelProperty(example = "Brazilian")
    @JsonView(RestaurantView.Summary.class)
    @NotBlank
    private String name;
}
