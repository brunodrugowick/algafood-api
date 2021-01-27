package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PaymentMethodModel {

    @ApiModelProperty(example = "1")
    @NotNull
    private Long id;

    @ApiModelProperty(example = "Debit Card")
    @NotBlank
    private String description;
}
