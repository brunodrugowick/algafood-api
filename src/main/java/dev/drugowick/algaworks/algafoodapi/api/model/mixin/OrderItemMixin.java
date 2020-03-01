package dev.drugowick.algaworks.algafoodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;

public abstract class OrderItemMixin {

    @JsonIgnore
    private Order order;
}
