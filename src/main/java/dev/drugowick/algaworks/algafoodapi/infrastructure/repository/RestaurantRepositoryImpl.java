package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;

@Component
public class RestaurantRepositoryImpl implements RestaurantRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Transactional
	@Override
	public Restaurant save(Restaurant restaurant) {
		return manager.merge(restaurant);
	}

	@Override
	public Restaurant get(Long id) {
		return manager.find(Restaurant.class, id);
	}

	@Override
	public List<Restaurant> list() {
		return manager.createQuery("from Restaurant", Restaurant.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void remove(Restaurant restaurant) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		restaurant = get(restaurant.getId());
		manager.remove(restaurant);
	}

}
