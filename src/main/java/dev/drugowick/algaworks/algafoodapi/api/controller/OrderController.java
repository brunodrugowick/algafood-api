package dev.drugowick.algaworks.algafoodapi.api.controller;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
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
    public MappingJacksonValue list(@RequestParam(required = false) String fields) {
        List<Order> orderList = orderRepository.findAll();
        List<OrderListModel> orderListModels = orderListModelAssembler.toCollectionModel(orderList, OrderListModel.class);

        MappingJacksonValue orderListModelWrapper = new MappingJacksonValue(orderListModels);

        SimpleFilterProvider filterProvider = new SimpleFilterProvider();
        filterProvider.addFilter("orderFieldsFilter", SimpleBeanPropertyFilter.serializeAll());
        if (StringUtils.isNoneBlank(fields)) {
            filterProvider.addFilter("orderFieldsFilter", SimpleBeanPropertyFilter.filterOutAllExcept(csvToArray(fields)));
        }
        orderListModelWrapper.setFilters(filterProvider);

        return orderListModelWrapper;
    }

    private String[] csvToArray(String fields) {
        String[] fieldsArray = fields.split(",");
        for (int i = 0, fieldsArrayLength = fieldsArray.length; i < fieldsArrayLength; i++) {
            fieldsArray[i] = fieldsArray[i].trim();
        }
        return fieldsArray;
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
