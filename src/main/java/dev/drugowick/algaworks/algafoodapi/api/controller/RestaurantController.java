package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private RestaurantCrudService restaurantCrudService;
	private ObjectMerger<Restaurant> objectMerger;

	public RestaurantController(RestaurantCrudService restaurantCrudService, ObjectMerger<Restaurant> objectMerger) {
		this.restaurantCrudService = restaurantCrudService;
		this.objectMerger = objectMerger;
	}

	@GetMapping
	public List<Restaurant> list() {
		return restaurantCrudService.list();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Restaurant restaurant) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (restaurant.getId() != null) {
			return ResponseEntity.badRequest()
					.build();
		}

		try {
			restaurant = restaurantCrudService.create(restaurant);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurant);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(value = {"/{id}"})
	public ResponseEntity<Restaurant> get(@PathVariable Long id) {
		Restaurant restaurant = restaurantCrudService.read(id);

		if (restaurant != null) {
			return ResponseEntity.ok(restaurant);
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
		Restaurant restaurantToUpdate = restaurantCrudService.read(id);

		if (restaurantToUpdate == null) {
			return ResponseEntity.notFound().build();
		}

		try {
			BeanUtils.copyProperties(restaurant, restaurantToUpdate, "id");
			restaurantToUpdate = restaurantCrudService.update(id, restaurantToUpdate);
			return ResponseEntity.ok(restaurantToUpdate);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> restaurantMap) {
		Restaurant restaurantToUpdate = restaurantCrudService.read(id);

		if (restaurantToUpdate == null) {
			return ResponseEntity.notFound().build();
		}

		restaurantToUpdate = objectMerger.mergeRequestBodyToGenericObject(restaurantMap, restaurantToUpdate);

		return update(id, restaurantToUpdate);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			restaurantCrudService.delete(id);
			return ResponseEntity.noContent().build();

		} catch (EntityBeingUsedException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
		} catch (EntityNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}


}
