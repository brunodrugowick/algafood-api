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
    public void confirmOrder(String orderCode) {
        Order order = findOrElseThrow(orderCode);

        order.confirm();
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
