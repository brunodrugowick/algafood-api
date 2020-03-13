package dev.drugowick.algaworks.algafoodapi.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProvinceInput {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 1, max = 2)
    private String abbreviation;
}
