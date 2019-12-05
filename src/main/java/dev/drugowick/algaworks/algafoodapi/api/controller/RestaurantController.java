package dev.drugowick.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
	
	private RestaurantCrudService restaurantCrudService;

	public RestaurantController(RestaurantCrudService restaurantCrudService) {
		this.restaurantCrudService = restaurantCrudService;
	}

	@GetMapping
	public List<Restaurant> list() {
		return restaurantCrudService.list();
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<Restaurant> get(@PathVariable Long id) {
		Restaurant restaurant = restaurantCrudService.read(id);
		
		if (restaurant != null) {
			return ResponseEntity.ok(restaurant);
		}
		
		return ResponseEntity.notFound().build();
	}
	
}
