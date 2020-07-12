package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityShortModel {

    @ApiModelProperty(example = "1")
    private String name;

    @ApiModelProperty(example = "NY")
    private String province;
}
