package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderStatusService {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @Transactional
    public void confirmOrder(String orderCode) {
        Order order = findOrElseThrow(orderCode);
        order.confirm();

        /**
         * The implementation of Domain Events by Spring Data requires explicit calls to save()  on the  Repository to
         * actually an event (see the method Order.confirm() which register an event).
         */
        orderRepository.save(order);
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

        /**
         * The implementation of Domain Events by Spring Data requires explicit calls to save()  on the  Repository to
         * actually an event (see the method Order.confirm() which register an event).
         */
        orderRepository.save(order);
    }

    private Order findOrElseThrow(String orderCode) {
        return orderService.findOrElseThrow(orderCode);
    }
}
