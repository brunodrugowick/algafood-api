package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityModel {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    @Valid
    private ProvinceModel province;
}
