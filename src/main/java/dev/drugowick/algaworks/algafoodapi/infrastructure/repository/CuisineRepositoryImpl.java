package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;

@Component
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
	public void remove(Cuisine cuisine) {
		cuisine = get(cuisine.getId());
		manager.remove(cuisine);
	}

}
