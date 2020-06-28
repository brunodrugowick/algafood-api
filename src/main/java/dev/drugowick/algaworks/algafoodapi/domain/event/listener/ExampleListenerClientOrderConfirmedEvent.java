package dev.drugowick.algaworks.algafoodapi.domain.event.listener;

import dev.drugowick.algaworks.algafoodapi.domain.event.OrderConfirmedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class ExampleListenerClientOrderConfirmedEvent {

    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void whenOrderConfirmed(OrderConfirmedEvent event) {
        log.info("I'm an example of a listener for OrderConfirmedEvent. I got an order to: " + event.getOrder().getClient().getName());
    }
}
