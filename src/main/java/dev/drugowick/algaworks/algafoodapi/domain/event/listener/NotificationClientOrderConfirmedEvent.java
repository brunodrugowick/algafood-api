package dev.drugowick.algaworks.algafoodapi.domain.event.listener;

import dev.drugowick.algaworks.algafoodapi.domain.event.OrderConfirmedEvent;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.service.MailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotificationClientOrderConfirmedEvent {

    private final MailSenderService mailSender;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void whenOrderConfirmed(OrderConfirmedEvent event) {
        Order order = event.getOrder();
        String clientEmail = order.getClient().getEmail();
        log.info("Sending confirmation mail to " + clientEmail);
        mailSender.send(MailSenderService.MailMessage.builder()
                .subject(order.getRestaurant().getName() + " - Order Confirmation")
                .body("order-confirmation.html")
                .recipient(clientEmail)
                .variable("order", order)
                .build());
    }
}
