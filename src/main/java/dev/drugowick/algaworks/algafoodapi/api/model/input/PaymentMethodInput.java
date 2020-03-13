package dev.drugowick.algaworks.algafoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodInput {

    @NotBlank
    private String description;
}
