package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.PaymentMethodModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Restaurants")
@RestController
@RequestMapping(path = "/restaurants/{restaurantId}/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantPaymentMethodController {

	private RestaurantCrudService restaurantCrudService;
	private GenericModelAssembler<PaymentMethod, PaymentMethodModel> modelAssembler;

	public RestaurantPaymentMethodController(RestaurantCrudService restaurantCrudService, GenericModelAssembler<PaymentMethod, PaymentMethodModel> modelAssembler) {
		this.restaurantCrudService = restaurantCrudService;

		this.modelAssembler = modelAssembler;
	}

	@ApiOperation("Lists payment methods oof a Restaurant")
	@ApiResponses({
			@ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
	})
	@GetMapping
	public List<PaymentMethodModel> list(
			@ApiParam(value = "Restaurant ID", example = "1", required = true)
			@PathVariable Long restaurantId) {
		return modelAssembler.toCollectionModel(restaurantCrudService.findOrElseThrow(restaurantId).getPaymentMethods(), PaymentMethodModel.class);
	}

	@ApiOperation("Unbinds restaurant and payment method")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Unbind successful"),
			@ApiResponse(code = 404, message = "Restaurant or payment method not found",
					response = ApiError.class)
	})
	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unbind(
			@ApiParam(value = "Restaurant ID", example = "1", required = true)
			@PathVariable Long restaurantId,
			@ApiParam(value = "Payment method ID", example = "1", required = true)
			@PathVariable Long paymentMethodId) {
		restaurantCrudService.unbindPaymentMethod(restaurantId, paymentMethodId);
	}

	@ApiOperation("Binds restaurant and payment method")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Bind successful"),
			@ApiResponse(code = 404, message = "Restaurant or payment method not found",
					response = ApiError.class)
	})
	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bind(
			@ApiParam(value = "Restaurant ID", example = "1", required = true)
			@PathVariable Long restaurantId,
			@ApiParam(value = "Payment method ID", example = "1", required = true)
			@PathVariable Long paymentMethodId) {
		restaurantCrudService.bindPaymentMethod(restaurantId, paymentMethodId);
	}

}
