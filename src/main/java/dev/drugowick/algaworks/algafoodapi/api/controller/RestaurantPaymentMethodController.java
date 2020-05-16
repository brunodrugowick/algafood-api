package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.PaymentMethodModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/payment-methods")
public class RestaurantPaymentMethodController {

	private RestaurantCrudService restaurantCrudService;
	private GenericModelAssembler<PaymentMethod, PaymentMethodModel> modelAssembler;

	public RestaurantPaymentMethodController(RestaurantCrudService restaurantCrudService, GenericModelAssembler<PaymentMethod, PaymentMethodModel> modelAssembler) {
		this.restaurantCrudService = restaurantCrudService;

		this.modelAssembler = modelAssembler;
	}

	@GetMapping
	public List<PaymentMethodModel> list(@PathVariable Long restaurantId) {
		return modelAssembler.toCollectionModel(restaurantCrudService.findOrElseThrow(restaurantId).getPaymentMethods(), PaymentMethodModel.class);
	}

	@DeleteMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void unbind(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantCrudService.unbindPaymentMethod(restaurantId, paymentMethodId);
	}

	@PutMapping("/{paymentMethodId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void bind(@PathVariable Long restaurantId, @PathVariable Long paymentMethodId) {
		restaurantCrudService.bindPaymentMethod(restaurantId, paymentMethodId);
	}

}
