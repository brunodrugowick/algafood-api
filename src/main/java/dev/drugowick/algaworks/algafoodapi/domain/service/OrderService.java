package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.model.input.OrderInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.OrderNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.*;
import dev.drugowick.algaworks.algafoodapi.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final GenericInputDisassembler<OrderInput, Order> inputDisassembler;
    private final RestaurantCrudService restaurantCrudService;
    private final PaymentMethodCrudService paymentMethodCrudService;
    private final ProductCrudService productCrudService;
    private final CityCrudService cityCrudService;
    private final UserCrudService userCrudService;

    public OrderService(OrderRepository orderRepository,
                        GenericInputDisassembler<OrderInput, Order> inputDisassembler,
                        RestaurantCrudService restaurantCrudService,
                        PaymentMethodCrudService paymentMethodCrudService,
                        ProductCrudService productCrudService,
                        CityCrudService cityCrudService,
                        UserCrudService userCrudService) {
        this.orderRepository = orderRepository;
        this.inputDisassembler = inputDisassembler;
        this.restaurantCrudService = restaurantCrudService;
        this.paymentMethodCrudService = paymentMethodCrudService;
        this.productCrudService = productCrudService;
        this.cityCrudService = cityCrudService;
        this.userCrudService = userCrudService;
    }


    public Order findOrElseThrow(String orderCode) {
        return orderRepository.findByCode(orderCode)
                .orElseThrow(() -> new OrderNotFoundException(orderCode));
    }

    @Transactional
    public Order createOrder(Order order) {
        validateOrder(order);
        validateOrder_ProductList(order);

        order.setDeliveryFee(order.getRestaurant().getDeliveryFee());
        order.calculateTotal();

        return orderRepository.save(order);
    }

    private void validateOrder(Order order) {
        City city = cityCrudService.findOrElseThrow(order.getDeliveryAddress().getCity().getId());
        User customer = userCrudService.findOrElseThrow(order.getClient().getId());
        Restaurant restaurant = restaurantCrudService.findOrElseThrow(order.getRestaurant().getId());
        PaymentMethod paymentMethod = paymentMethodCrudService.findOrElseThrow(order.getPaymentMethod().getId());

        order.getDeliveryAddress().setCity(city);
        order.setClient(customer);
        order.setRestaurant(restaurant);
        order.setPaymentMethod(paymentMethod);

        if (restaurant.doesNotAccept(paymentMethod)) {
            throw new GenericBusinessException(
                    String.format("Payment Method %s is not accepted on this Restaurant",
                            paymentMethod.getDescription()));
        }
    }

    private void validateOrder_ProductList(Order order) {
        order.getItems().forEach(orderItem -> {
            Product product = productCrudService.findOrElseThrow(
                    order.getRestaurant().getId(), orderItem.getProduct().getId());

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setUnitPrice(product.getPrice());
        });
    }
}
