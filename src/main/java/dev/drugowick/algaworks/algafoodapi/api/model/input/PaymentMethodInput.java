package dev.drugowick.algaworks.algafoodapi.api.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PaymentMethodInput {

    @ApiModelProperty(example = "Debit Card", required = true)
    @NotBlank
    private String description;
}
