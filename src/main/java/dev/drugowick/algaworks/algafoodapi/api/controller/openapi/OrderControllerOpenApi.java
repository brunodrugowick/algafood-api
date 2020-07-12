package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderListModel;
import dev.drugowick.algaworks.algafoodapi.api.model.OrderModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PageModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.OrderInput;
import dev.drugowick.algaworks.algafoodapi.domain.filter.OrderFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;

@Api(tags = "Orders")
public interface OrderControllerOpenApi {

    @ApiOperation("Paginated list of orders")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    public PageModel<OrderListModel> search(OrderFilter filter, Pageable pageable);

    @ApiOperation("Retrieves an order by ID")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid order ID", response = ApiError.class),
            @ApiResponse(code = 404, message = "Order not found", response = ApiError.class)
    })
    public OrderModel get(String orderCode);

    @ApiOperation("Creates a new order")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Order created")
    })
    public OrderModel create(OrderInput orderInput);
}
