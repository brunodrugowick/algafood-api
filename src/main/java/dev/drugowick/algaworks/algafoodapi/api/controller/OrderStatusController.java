package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.openapi.OrderStatusControllerOpenApi;
import dev.drugowick.algaworks.algafoodapi.domain.service.OrderStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/orders/{orderCode}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStatusController implements OrderStatusControllerOpenApi {

    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @PutMapping("/confirmation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmOrder(@PathVariable String orderCode) {
        orderStatusService.confirmOrder(orderCode);
    }

    @PutMapping("/delivery")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deliveryOrder(@PathVariable String orderCode) {
        orderStatusService.deliveryOrder(orderCode);
    }

    @PutMapping("/cancellation")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelOrder(@PathVariable String orderCode) {
        orderStatusService.cancelOrder(orderCode);
    }
}
