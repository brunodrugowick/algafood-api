package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;

import java.util.List;

public class CuisineMixin {

    @JsonIgnore
    List<Restaurant> restaurants;
}
