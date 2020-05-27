package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderListModel;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.OrderCrudService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderCrudService orderCrudService;
    private final OrderRepository orderRepository;
    private final GenericModelAssembler<Order, OrderModel> orderModelAssembler;
    private final GenericModelAssembler<Order, OrderListModel> orderListModelAssembler;

    public OrderController(OrderCrudService orderCrudService, OrderRepository orderRepository, GenericModelAssembler<Order, OrderModel> orderModelAssembler, GenericModelAssembler<Order, OrderListModel> orderListModelAssembler) {
        this.orderCrudService = orderCrudService;
        this.orderRepository = orderRepository;
        this.orderModelAssembler = orderModelAssembler;
        this.orderListModelAssembler = orderListModelAssembler;
    }

    @GetMapping
    public List<OrderListModel> list() {
        return orderListModelAssembler.toCollectionModel(orderRepository.findAll(), OrderListModel.class);
    }

    @GetMapping("/{orderId}")
    public OrderModel get(@PathVariable Long orderId) {
        return orderModelAssembler.toModel(
                orderCrudService.findOrElseThrow(orderId), OrderModel.class);
    }
}
