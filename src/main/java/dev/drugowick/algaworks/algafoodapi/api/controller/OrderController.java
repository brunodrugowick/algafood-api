package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderListModel;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.OrderInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final GenericModelAssembler<Order, OrderModel> orderModelAssembler;
    private final GenericInputDisassembler<OrderInput, Order> orderInputDisassembler;
    private final GenericModelAssembler<Order, OrderListModel> orderListModelAssembler;

    public OrderController(OrderService orderService, OrderRepository orderRepository, GenericModelAssembler<Order, OrderModel> orderModelAssembler, GenericInputDisassembler<OrderInput, Order> orderInputDisassembler, GenericModelAssembler<Order, OrderListModel> orderListModelAssembler) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderModelAssembler = orderModelAssembler;
        this.orderInputDisassembler = orderInputDisassembler;
        this.orderListModelAssembler = orderListModelAssembler;
    }

    @GetMapping
    public List<OrderListModel> list() {
        return orderListModelAssembler.toCollectionModel(orderRepository.findAll(), OrderListModel.class);
    }

    @GetMapping("/{orderId}")
    public OrderModel get(@PathVariable Long orderId) {
        return orderModelAssembler.toModel(
                orderService.findOrElseThrow(orderId), OrderModel.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderModel create(@Valid @RequestBody OrderInput orderInput) {
        try {
            Order newOrder = orderInputDisassembler.toDomain(orderInput, Order.class);

            //TODO get authenticated user (there's no security right now, so I'm setting always the same user)
            newOrder.setClient(new User());
            newOrder.getClient().setId(1L);

            newOrder = orderService.createOrder(newOrder);

            return orderModelAssembler.toModel(newOrder, OrderModel.class);
        } catch (EntityNotFoundException e) {
            throw new GenericBusinessException(e.getMessage(), e);
        }
    }
}
