package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CuisineCrudService {
	
	private CuisineRepository cuisineRepository;

	public CuisineCrudService(CuisineRepository cuisineRepository) {
		this.cuisineRepository = cuisineRepository;
	}
	
	public Cuisine create(Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}
	
	public Cuisine update(Long id, Cuisine cuisine) {
		cuisine.setId(id);
		return cuisineRepository.save(cuisine);
	}
	
	public void delete(Long id) {
		try {
			cuisineRepository.deleteById(id);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format("Cuisine %d is being used by another entity and can not be removed.", id));
		} catch (EmptyResultDataAccessException exception) {
			throw new EntityNotFoundException(
					String.format("There's no Cuisine with the id %d.", id));
		}
	}

}
