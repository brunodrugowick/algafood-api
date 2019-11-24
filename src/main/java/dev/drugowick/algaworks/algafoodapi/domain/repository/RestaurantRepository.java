package dev.drugowick.algaworks.algafoodapi.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;

public interface RestaurantRepository {

	Restaurant save(Restaurant restaurant);
	Restaurant get(Long id);
	List<Restaurant> list();
	void remove(Restaurant restaurant);
	
}
