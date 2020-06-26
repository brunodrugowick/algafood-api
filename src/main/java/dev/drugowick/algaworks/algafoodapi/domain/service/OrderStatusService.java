package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class OrderStatusService {

    private final OrderService orderService;
    private final MailSenderService mailSender;

    public OrderStatusService(OrderService orderService, MailSenderService mailSender) {
        this.orderService = orderService;
        this.mailSender = mailSender;
    }

    @Transactional
    public void confirmOrder(String orderCode) {
        Order order = findOrElseThrow(orderCode);
        order.confirm();

        String clientEmail = order.getClient().getEmail();
        log.info("Sending confirmation mail to " + clientEmail);
        mailSender.send(MailSenderService.MailMessage.builder()
                .subject(order.getRestaurant().getName() + " - Order Confirmation")
                .body("order-confirmation.html")
                .recipient(clientEmail)
                .variable("order", order)
                .build());
    }

    @Transactional
    public void deliveryOrder(String orderCode) {
        Order order = findOrElseThrow(orderCode);

        order.deliver();
    }

    @Transactional
    public void cancelOrder(String orderCode) {
        Order order = findOrElseThrow(orderCode);

        order.cancel();
    }

    private Order findOrElseThrow(String orderCode) {
        return orderService.findOrElseThrow(orderCode);
    }
}
