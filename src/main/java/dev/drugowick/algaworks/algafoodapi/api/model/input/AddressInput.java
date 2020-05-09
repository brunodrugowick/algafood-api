package dev.drugowick.algaworks.algafoodapi.api.model.input;

import dev.drugowick.algaworks.algafoodapi.api.model.CityShortModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddressInput {

    @NotBlank
    private String postalCode;

    @NotBlank
    private String addressLine_1;

    private String addressLine_2;

    @NotBlank
    private String region;

    @Valid
    @NotNull
    private CityIdInput city;
}
