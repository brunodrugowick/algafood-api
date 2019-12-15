package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
	public void remove(Long id) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		Restaurant restaurant = get(id);

		if (restaurant == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(restaurant);
	}

}
