package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import io.swagger.annotations.*;

@Api(tags = "Order Status")
public interface OrderStatusControllerOpenApi {

    @ApiOperation("Order confirmation")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order successfully confirmed"),
            @ApiResponse(code = 404, message = "Order not found", response = ApiError.class)
    })
    public void confirmOrder(
            @ApiParam(value = "Order ID", example = "aff37747-f651-4463-a6c3-03af003ccde9",
                    required = true) String orderCode);

    @ApiOperation("Order delivery")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order successfully delivered"),
            @ApiResponse(code = 404, message = "Order not found", response = ApiError.class)
    })
    public void deliveryOrder(
            @ApiParam(value = "Order ID", example = "aff37747-f651-4463-a6c3-03af003ccde9",
                    required = true) String orderCode);

    @ApiOperation("Order cancellation")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Order successfully cancelled"),
            @ApiResponse(code = 404, message = "Order not found", response = ApiError.class)
    })
    public void cancelOrder(
            @ApiParam(value = "Order ID", example = "aff37747-f651-4463-a6c3-03af003ccde9",
                    required = true) String orderCode);
}
