package dev.drugowick.algaworks.algafoodapi.domain.event.listener;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderCancelledEvent {

    private final Order order;
}
