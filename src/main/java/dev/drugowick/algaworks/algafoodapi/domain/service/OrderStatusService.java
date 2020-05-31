package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderStatusService {

    private final OrderService orderService;

    public OrderStatusService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Transactional
    public void confirmOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        order.confirm();
    }

    @Transactional
    public void deliveryOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        order.deliver();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = findOrElseThrow(orderId);

        order.cancel();
    }

    private Order findOrElseThrow(Long orderId) {
        return orderService.findOrElseThrow(orderId);
    }
}
