package dev.drugowick.algafoodapi.client.model.input;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressInput {

    private String postalCode;
    private String addressLine_1;
    private String addressLine_2;
    private String region;
    private CityInput city;
}
