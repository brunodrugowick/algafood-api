package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressModel {

    private String postalCode;
    private String addressLine_1;
    private String addressLine_2;
    private String region;
    private CityShortModel city;
}
