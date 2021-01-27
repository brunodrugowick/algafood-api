package dev.drugowick.algaworks.algafoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CuisineInput {

    @ApiModelProperty(example = "Brazilian", required = true)
    @NotBlank
    private String name;
}
