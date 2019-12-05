package dev.drugowick.algaworks.algafoodapi.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;

@Service
public class RestaurantCrudService {
	
	private RestaurantRepository restaurantRepository;

	public RestaurantCrudService(RestaurantRepository restaurantRepository) {
		this.restaurantRepository = restaurantRepository;
	}
	
	public Restaurant read(Long id) {
		return restaurantRepository.get(id);
	}
	
	public List<Restaurant> list() {
		return restaurantRepository.list();
	}
	
}
