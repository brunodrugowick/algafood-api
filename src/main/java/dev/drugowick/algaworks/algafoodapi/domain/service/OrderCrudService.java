package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.OrderNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderCrudService {

    private OrderRepository orderRepository;

    public OrderCrudService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order findOrElseThrow(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }
}
