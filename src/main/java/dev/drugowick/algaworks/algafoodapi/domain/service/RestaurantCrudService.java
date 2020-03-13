package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.RestaurantNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestaurantCrudService {

	public static final String MSG_RESTAURANT_CONFLICT = "Operation on Restaurant %d conflicts with another entity and can not be performed.";

	private RestaurantRepository restaurantRepository;
	private CuisineCrudService cuisineCrudService;

	public RestaurantCrudService(RestaurantRepository restaurantRepository, CuisineRepository cuisineRepository, CuisineCrudService cuisineCrudService) {
		this.restaurantRepository = restaurantRepository;
		this.cuisineCrudService = cuisineCrudService;
	}

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Cuisine cuisine = cuisineCrudService.findOrElseThrow(cuisineId);

		restaurant.setCuisine(cuisine);
		try {
			return restaurantRepository.save(restaurant);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_RESTAURANT_CONFLICT, restaurant.getId()));
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			restaurantRepository.deleteById(id);
			// Flushing here guarantees the DB exceptions below can be caught.
			restaurantRepository.flush();
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_RESTAURANT_CONFLICT, id));
		} catch (EmptyResultDataAccessException exception) {
			throw new RestaurantNotFoundException(id);
		}
	}

	/**
	 * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
	 *
	 * @param id of the entity to find.
	 * @return the entity from the repository.
	 */
	public Restaurant findOrElseThrow(Long id) {
		return restaurantRepository.findById(id)
				.orElseThrow(() -> new RestaurantNotFoundException(id));
	}
}
