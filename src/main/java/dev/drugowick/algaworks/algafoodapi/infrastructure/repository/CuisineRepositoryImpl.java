package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class CuisineRepositoryImpl implements CuisineRepository {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Cuisine> list() {
		return manager.createQuery("from Cuisine", Cuisine.class)
				.getResultList();
	}
	
	@Override
	public Cuisine get(Long id) {
		return manager.find(Cuisine.class, id);
	}

	@Transactional
	@Override
	public Cuisine save(Cuisine cuisine) {
		return manager.merge(cuisine);
	}
	
	@Transactional
	@Override
	public void remove(Long id) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		Cuisine cuisine = get(id);
		
		if (cuisine == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		manager.remove(cuisine);
	}

}
