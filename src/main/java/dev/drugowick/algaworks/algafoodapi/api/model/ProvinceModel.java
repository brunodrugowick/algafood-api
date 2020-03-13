package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class ProvinceModel {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank // Maybe not necessary with the size validation
    @Size(min = 1, max = 2)
    private String abbreviation;
}
