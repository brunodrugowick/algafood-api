package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

//@ApiModel(value = "CityModel", description = "Representation of a city")
@Getter
@Setter
public class CityModel {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Campinas")
    private String name;

    private ProvinceModel province;
}
