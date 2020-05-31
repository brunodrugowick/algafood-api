package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.model.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class OrderStatusService {

    private final OrderService orderService;

    public OrderStatusService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void confirmOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new GenericBusinessException(
                    String.format("Impossible to transition order %d from %s to %s.",
                            orderId, order.getStatus().getDescription(), OrderStatus.CONFIRMED.getDescription()));
        }

        order.setConfirmationDate(OffsetDateTime.now(ZoneOffset.UTC));
        order.setStatus(OrderStatus.CONFIRMED);
    }

    @Transactional
    public void deliveryOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
            throw new GenericBusinessException(
                    String.format("Impossible to transition order %d from %s to %s.",
                            orderId, order.getStatus().getDescription(), OrderStatus.DELIVERED.getDescription()));
        }

        order.setDeliveryDate(OffsetDateTime.now(ZoneOffset.UTC));
        order.setStatus(OrderStatus.DELIVERED);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        if (!order.getStatus().equals(OrderStatus.CREATED)) {
            throw new GenericBusinessException(
                    String.format("Impossible to transition order %d from %s to %s.",
                            orderId, order.getStatus().getDescription(), OrderStatus.CANCELLED.getDescription()));
        }

        order.setCancellationDate(OffsetDateTime.now(ZoneOffset.UTC));
        order.setStatus(OrderStatus.CANCELLED);
    }

    private Order findOrElseThrow(Long orderId) {
        return orderService.findOrElseThrow(orderId);
    }
}
