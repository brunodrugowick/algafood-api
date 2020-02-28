package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private RestaurantCrudService restaurantCrudService;
	private ValidationService validationService;

	public RestaurantController(RestaurantRepository restaurantRepository, RestaurantCrudService restaurantCrudService, ValidationService validationService) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCrudService = restaurantCrudService;
		this.validationService = validationService;
	}

	@GetMapping
	public List<Restaurant> list() {
		return restaurantRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid Restaurant restaurant) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (restaurant.getId() != null) {
			throw new GenericBusinessException("You should not send an ID when saving or updating an entity.");
		}

		try {
			restaurant = restaurantCrudService.save(restaurant);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurant);
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@GetMapping(value = {"/{id}"})
	public Restaurant get(@PathVariable Long id) {
		return restaurantCrudService.findOrElseThrow(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
									@RequestBody @Valid Restaurant restaurant) {
		Restaurant restaurantToUpdate = restaurantCrudService.findOrElseThrow(id);

		BeanUtils.copyProperties(restaurant, restaurantToUpdate,
				"id", "paymentMethods", "address", "createdDate", "updatedDate");
		try {
			// The save method will update when an existing ID is being passed.
			restaurantCrudService.save(restaurantToUpdate);
			return ResponseEntity.ok(restaurantToUpdate);
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String,
			Object> restaurantMap, HttpServletRequest request) {
		Restaurant restaurantToUpdate = restaurantCrudService.findOrElseThrow(id);

		try {
			ObjectMerger.mergeRequestBodyToGenericObject(restaurantMap, restaurantToUpdate, Restaurant.class);
		} catch (IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e);
			var servletServerHttpRequest = new ServletServerHttpRequest(request);
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
		}

		validationService.validate(restaurantToUpdate, "restaurant");
		return update(id, restaurantToUpdate);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		restaurantCrudService.delete(id);
	}


}
