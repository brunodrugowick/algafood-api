package dev.drugowick.algaworks.algafoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductInput {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @PositiveOrZero
    @NotNull
    private BigDecimal price;

    @NotNull
    private Boolean active;
}
