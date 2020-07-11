package dev.drugowick.algaworks.algafoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityInput {

    @ApiModelProperty(example = "Campinas")
    @NotBlank
    private String name;

    @ApiModelProperty(example = "1")
    @NotNull
    @Valid
    private ProvinceIdInput province;
}
