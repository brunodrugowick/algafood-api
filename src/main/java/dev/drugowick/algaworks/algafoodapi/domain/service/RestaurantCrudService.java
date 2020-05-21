package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.RestaurantNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.*;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RestaurantCrudService {

	public static final String MSG_RESTAURANT_CONFLICT = "Operation on Restaurant %d conflicts with another entity and can not be performed.";

	private RestaurantRepository restaurantRepository;
	private CuisineCrudService cuisineCrudService;
	private CityCrudService cityCrudService;
	private PaymentMethodCrudService paymentMethodCrudService;
	private final UserCrudService userCrudService;

	public RestaurantCrudService(RestaurantRepository restaurantRepository, CuisineRepository cuisineRepository, CuisineCrudService cuisineCrudService, CityCrudService cityCrudService, PaymentMethodCrudService paymentMethodCrudService, UserCrudService userCrudService) {
		this.restaurantRepository = restaurantRepository;
		this.cuisineCrudService = cuisineCrudService;
		this.cityCrudService = cityCrudService;
		this.paymentMethodCrudService = paymentMethodCrudService;
		this.userCrudService = userCrudService;
	}

	@Transactional
	public Restaurant save(Restaurant restaurant) {
		Long cuisineId = restaurant.getCuisine().getId();
		Long cityId = restaurant.getAddress().getCity().getId();

		Cuisine cuisine = cuisineCrudService.findOrElseThrow(cuisineId);
		City city = cityCrudService.findOrElseThrow(cityId);

		restaurant.setCuisine(cuisine);
		restaurant.getAddress().setCity(city);

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

	@Transactional
	public void activate(Long restaurantId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		restaurant.activate();
	}

	@Transactional
	public void activate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::activate);
	}

	@Transactional
	public void deactivate(Long restaurantId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		restaurant.deactivate();
	}

	@Transactional
	public void deactivate(List<Long> restaurantIds) {
		restaurantIds.forEach(this::deactivate);
	}

	@Transactional
	public void open(Long restaurantId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		restaurant.open();
	}

	@Transactional
	public void close(Long restaurantId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		restaurant.close();
	}

	@Transactional
	public void unbindPaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		PaymentMethod paymentMethod = paymentMethodCrudService.findOrElseThrow(paymentMethodId);

		restaurant.removePaymentMethod(paymentMethod);
	}

	@Transactional
	public void bindPaymentMethod(Long restaurantId, Long paymentMethodId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		PaymentMethod paymentMethod = paymentMethodCrudService.findOrElseThrow(paymentMethodId);

		restaurant.addPaymentMethod(paymentMethod);
	}

	@Transactional
	public void unbindManager(Long restaurantId, Long managerId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		User manager = userCrudService.findOrElseThrow(managerId);

		restaurant.removeManager(manager);
	}

	@Transactional
	public void bindManager(Long restaurantId, Long managerId) {
		Restaurant restaurant = findOrElseThrow(restaurantId);
		User manager = userCrudService.findOrElseThrow(managerId);

		restaurant.addManager(manager);
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
