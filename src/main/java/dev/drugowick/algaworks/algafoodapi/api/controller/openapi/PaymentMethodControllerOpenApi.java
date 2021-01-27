package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.PaymentMethodModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.PaymentMethodInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import java.security.Principal;
import java.util.List;

@Api(tags = "Payment Methods")
public interface PaymentMethodControllerOpenApi {

    @ApiOperation("List of Payment Methods")
    public ResponseEntity<List<PaymentMethodModel>> list();

    @ApiOperation("Creates a new payment method")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Payment method created")
    })
    public ResponseEntity<PaymentMethodModel> save(
            @ApiParam(name = "body", value = "Representation of a payment method to be saved") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Retrieves a payment method by ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid payment method ID", response = ApiError.class),
            @ApiResponse(code = 404, message = "Payment method not found", response = ApiError.class)
    })
    public ResponseEntity<PaymentMethodModel> get(
            @ApiParam(value = "Payment method ID", example = "1")Long id,
            // Undocumented inputs (injected by Spring)
            ServletWebRequest request, Principal principal);

    @ApiOperation("Updates a payment method by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Payment method updated"),
            @ApiResponse(code = 404, message = "Payment method not found", response = ApiError.class)
    })
    public PaymentMethodModel update(
            @ApiParam(value = "Payment method ID", example = "1") Long id,
            @ApiParam(name = "body", value = "Representation of a payment method to be updated") PaymentMethodInput paymentMethodInput);

    @ApiOperation("Removes a payment method by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Payment method removed"),
            @ApiResponse(code = 404, message = "Payment method not found", response = ApiError.class)
    })
    public void delete(
            @ApiParam(value = "Payment method ID", example = "1") Long id);
}
