package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CityRepositoryImpl implements CityRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public City save(City city) {
		return manager.merge(city);
	}

	@Override
	public City get(Long id) {
		return manager.find(City.class, id);
	}

	@Override
	public List<City> list() {
		return manager.createQuery("from City", City.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void remove(Long id) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		City city = get(id);

		if (city == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(city);
	}

}
