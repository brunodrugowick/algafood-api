package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.CuisineNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CuisineCrudService {

	public static final String MSG_CUISINE_CONFLICT = "Operation on Cuisine %d conflicts with another entity and can not be performed.";

	private CuisineRepository cuisineRepository;

	public CuisineCrudService(CuisineRepository cuisineRepository) {
		this.cuisineRepository = cuisineRepository;
	}

	@Transactional
	public Cuisine save(Cuisine cuisine) {
		try {
			return cuisineRepository.save(cuisine);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_CUISINE_CONFLICT, cuisine.getId()));
		}
	}

	@Transactional
	public Cuisine update(Long id, Cuisine cuisine) {
		cuisine.setId(id);
		return cuisineRepository.save(cuisine);
	}

	@Transactional
	public void delete(Long id) {
		try {
			cuisineRepository.deleteById(id);
			// Flushing here guarantees the DB exceptions below can be caught.
			cuisineRepository.flush();
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_CUISINE_CONFLICT, id));
		} catch (EmptyResultDataAccessException exception) {
			throw new CuisineNotFoundException(id);
		}
	}

	/**
	 * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
	 *
	 * @param id of the entity to find.
	 * @return the entity from the repository.
	 */
	public Cuisine findOrElseThrow(Long id) {
		return cuisineRepository.findById(id)
				.orElseThrow(() -> new CuisineNotFoundException(id));
	}

}
