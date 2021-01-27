package dev.drugowick.algaworks.algafoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @ApiModelProperty(example = "13020240", required = true)
    @NotBlank
    private String postalCode;

    @ApiModelProperty(example = "Orders streets", required = true)
    @NotBlank
    private String addressLine_1;

    @ApiModelProperty(example = "Apartment 13")
    private String addressLine_2;

    @ApiModelProperty(example = "Central", required = true)
    @NotBlank
    private String region;

    @Valid
    @NotNull
    private CityIdInput city;
}
