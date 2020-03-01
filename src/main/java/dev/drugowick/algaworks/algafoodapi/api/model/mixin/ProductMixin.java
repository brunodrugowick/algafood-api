package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;

public abstract class ProductMixin {

    @JsonIgnore
    private Restaurant restaurant;
}
