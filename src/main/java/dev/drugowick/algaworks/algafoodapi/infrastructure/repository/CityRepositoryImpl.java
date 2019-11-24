package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;

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
	public void remove(City city) {
		// Finds the entity so Hibernate has it Managed (not Detached).
				city = get(city.getId());
				manager.remove(city);
	}

}
