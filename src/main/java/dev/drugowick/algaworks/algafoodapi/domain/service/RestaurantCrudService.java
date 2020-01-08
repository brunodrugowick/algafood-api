package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantCrudService {

	public static final String MSG_NO_RESTAURANT = "There's no Restaurant with the id %d.";
	public static final String MSG_RESTAURANT_IN_USE = "Restaurant %d is being used by another entity and can not be removed.";

	private RestaurantRepository restaurantRepository;
	private CuisineRepository cuisineRepository;

	public RestaurantCrudService(RestaurantRepository restaurantRepository, CuisineRepository cuisineRepository) {
		this.restaurantRepository = restaurantRepository;
		this.cuisineRepository = cuisineRepository;
	}

	public Restaurant save(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Optional<Cuisine> cuisine = cuisineRepository.findById(cuisineId);

		if (cuisine.isEmpty()) {
			throw new EntityNotFoundException(
					String.format("No cuisine with ID %d", cuisineId));
		}

		restaurant.setCuisine(cuisine.get());
		return restaurantRepository.save(restaurant);
	}

	public void delete(Long id) {
		try {
			restaurantRepository.deleteById(id);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_RESTAURANT_IN_USE, id));
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException(
					String.format(MSG_NO_RESTAURANT, id));
		}
	}

	/**
	 * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
	 *
	 * @param id of the Restaurant to find.
	 * @return the Restaurant from the repository.
	 */
	public Restaurant findOrElseThrow(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(
						String.format(MSG_NO_RESTAURANT, id)
				));
	}
}
