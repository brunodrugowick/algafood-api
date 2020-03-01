package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;

public class CityMixin {

    @JsonIgnoreProperties(value = {"name", "abbreviation"}, allowGetters = true)
    private Province province;
}
