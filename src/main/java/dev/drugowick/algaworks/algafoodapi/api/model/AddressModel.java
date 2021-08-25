package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    @ApiModelProperty(example = "13020240", required = true)
    private String postalCode;

    @ApiModelProperty(example = "Orders streets", required = true)
    private String addressLine_1;

    @ApiModelProperty(example = "Apartment 13")
    private String addressLine_2;

    @ApiModelProperty(example = "Central", required = true)
    private String region;

    private CityShortModel city;
}
