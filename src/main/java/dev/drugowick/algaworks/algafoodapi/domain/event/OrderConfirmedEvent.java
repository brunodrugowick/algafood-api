package dev.drugowick.algaworks.algafoodapi.domain.event;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderConfirmedEvent {

    private final Order order;
}
