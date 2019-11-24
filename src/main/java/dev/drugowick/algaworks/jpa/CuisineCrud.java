package dev.drugowick.algaworks.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.domain.model.Cuisine;

@Component
public class CuisineCrud {
	
	@PersistenceContext
	private EntityManager manager;
	
	public List<Cuisine> listAll() {
		return manager.createQuery("from Cuisine", Cuisine.class)
				.getResultList();
	}
	
	public Cuisine findById(Long id) {
		return manager.find(Cuisine.class, id);
	}
	
	@Transactional
	public Cuisine save(Cuisine cuisine) {
		return manager.merge(cuisine);
	}
	
	@Transactional
	public void remove(Cuisine cuisine) {
		cuisine = findById(cuisine.getId());
		manager.remove(cuisine);
	}

}
