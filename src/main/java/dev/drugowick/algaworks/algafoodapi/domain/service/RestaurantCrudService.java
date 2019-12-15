package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantCrudService {

	private RestaurantRepository restaurantRepository;
	private CuisineRepository cuisineRepository;

	public RestaurantCrudService(RestaurantRepository restaurantRepository, CuisineRepository cuisineRepository) {
		this.restaurantRepository = restaurantRepository;
		this.cuisineRepository = cuisineRepository;
	}

	public Restaurant create(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Cuisine cuisine = cuisineRepository.findById(cuisineId)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format("No cuisine with ID %d", cuisineId)));

		restaurant.setCuisine(cuisine);
		return restaurantRepository.save(restaurant);
	}

	public Restaurant read(Long id) {
		return restaurantRepository.get(id);
	}

	public List<Restaurant> list() {
		return restaurantRepository.list();
	}

	public Restaurant update(Long id, Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Optional<Cuisine> cuisine = cuisineRepository.findById(cuisineId);

		if (cuisine.isEmpty()) {
			throw new EntityNotFoundException(
					String.format("No cuisine with ID %d", cuisineId));
		}

		Restaurant restaurantToSave = restaurantRepository.get(id);
		restaurantToSave.setCuisine(cuisine.get());

		if (restaurantToSave == null) {
			throw new EntityNotFoundException(
					String.format("No restaurant with id %d", id));
		}

		BeanUtils.copyProperties(restaurant, restaurantToSave, "id");
		return restaurantRepository.save(restaurantToSave);
	}

	public void delete(Long id) {
		try {
			restaurantRepository.remove(id);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format("Restaurant %d is being used by another entity and can not be removed.", id));
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException(
					String.format("There's no Restaurant with the id %d.", id));
		}
	}
}
