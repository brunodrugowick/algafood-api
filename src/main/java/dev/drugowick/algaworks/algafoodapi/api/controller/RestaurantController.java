package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private RestaurantCrudService restaurantCrudService;

	public RestaurantController(RestaurantRepository restaurantRepository, RestaurantCrudService restaurantCrudService) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCrudService = restaurantCrudService;
	}

	@GetMapping
	public List<Restaurant> list() {
		return restaurantRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody Restaurant restaurant) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (restaurant.getId() != null) {
			return ResponseEntity.badRequest()
					.body("Invalid request body.");
		}

		try {
			restaurant = restaurantCrudService.save(restaurant);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurant);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping(value = {"/{id}"})
	public ResponseEntity<Restaurant> get(@PathVariable Long id) {
		Optional<Restaurant> restaurant = restaurantRepository.findById(id);

		if (restaurant.isPresent()) {
			return ResponseEntity.ok(restaurant.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
		Optional<Restaurant> restaurantToUpdate = restaurantRepository.findById(id);

		if (restaurantToUpdate.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		try {
			BeanUtils.copyProperties(restaurant, restaurantToUpdate.get(), "id", "paymentMethods", "address");
			// The save method will update when an existing ID is being passed.
			Restaurant restaurantUpdated = restaurantCrudService.save(restaurantToUpdate.get());
			return ResponseEntity.ok(restaurantUpdated);

		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> restaurantMap) {
		Optional<Restaurant> restaurantToUpdate = restaurantRepository.findById(id);

		if (restaurantToUpdate.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		ObjectMerger.mergeRequestBodyToGenericObject(restaurantMap, restaurantToUpdate.get(), Restaurant.class);

		return update(id, restaurantToUpdate.get());
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
