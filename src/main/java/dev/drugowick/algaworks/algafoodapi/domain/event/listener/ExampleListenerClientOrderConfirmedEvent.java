package dev.drugowick.algaworks.algafoodapi.domain.event.listener;

import dev.drugowick.algaworks.algafoodapi.domain.event.OrderConfirmedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ExampleListenerClientOrderConfirmedEvent {

    @EventListener
    public void whenOrderConfirmed(OrderConfirmedEvent event) {
        log.info("I'm an example of a listener for OrderConfirmedEvent. I got an order to: " + event.getOrder().getClient().getName());
    }
}
