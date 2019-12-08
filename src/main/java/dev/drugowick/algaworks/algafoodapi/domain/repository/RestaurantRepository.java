package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;

import java.util.List;

public interface RestaurantRepository {

	Restaurant save(Restaurant restaurant);

	Restaurant get(Long id);

	List<Restaurant> list();

	void remove(Long id);

}
