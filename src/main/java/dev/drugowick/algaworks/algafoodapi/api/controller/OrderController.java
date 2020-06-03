package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.PageModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderListModel;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PageModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.OrderInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.filter.OrderFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.OrderService;
import dev.drugowick.algaworks.algafoodapi.infrastructure.repository.spec.OrderSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final GenericModelAssembler<Order, OrderModel> orderModelAssembler;
    private final GenericInputDisassembler<OrderInput, Order> orderInputDisassembler;
    private final GenericModelAssembler<Order, OrderListModel> orderListModelAssembler;
    private final PageModelAssembler<Order, OrderListModel> pageModelAssembler;

    public OrderController(OrderService orderService, OrderRepository orderRepository, GenericModelAssembler<Order, OrderModel> orderModelAssembler, GenericInputDisassembler<OrderInput, Order> orderInputDisassembler, GenericModelAssembler<Order, OrderListModel> orderListModelAssembler, PageModelAssembler<Order, OrderListModel> pageModelAssembler) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
        this.orderModelAssembler = orderModelAssembler;
        this.orderInputDisassembler = orderInputDisassembler;
        this.orderListModelAssembler = orderListModelAssembler;
        this.pageModelAssembler = pageModelAssembler;
    }

    /**
     * OrderFilter contains several properties. Each property can be passed as URL Query Param and Spring is able to
     * build the OrderFilter based on just that, filling properties that the client sent and letting others null.
     *
     * @param filter
     * @return
     */
    @GetMapping
    public PageModel<OrderListModel> search(OrderFilter filter, @PageableDefault(size = 8) Pageable pageable) {
        Page<Order> orderPage = orderRepository.findAll(OrderSpecs.usingFilter(filter), pageable);

        return pageModelAssembler.toCollectionModelPage(orderPage, OrderListModel.class);
    }

    @GetMapping("/{orderCode}")
    public OrderModel get(@PathVariable String orderCode) {
        return orderModelAssembler.toModel(
                orderService.findOrElseThrow(orderCode), OrderModel.class);
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
