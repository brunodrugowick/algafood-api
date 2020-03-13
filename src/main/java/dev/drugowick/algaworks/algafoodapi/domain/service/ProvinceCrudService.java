package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.ProvinceNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinceCrudService {

	public static final String MSG_PROVINCE_CONFLICT = "Operation on Province %d conflicts with another entity and can not be performed.";

	private ProvinceRepository provinceRepository;

	public ProvinceCrudService(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}

	@Transactional
	public Province save(Province province) {
		try {
			return provinceRepository.save(province);
		} catch (DataIntegrityViolationException exception) {
			throw new EntityBeingUsedException(
					String.format(MSG_PROVINCE_CONFLICT, province.getId()));
		}
	}

	@Transactional
	public void delete(Long id) {
		try {
			provinceRepository.deleteById(id);
			// Flushing here guarantees the DB exceptions below can be caught.
			provinceRepository.flush();
		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(
					String.format(MSG_PROVINCE_CONFLICT, id));
		} catch (EmptyResultDataAccessException e) {
			throw new ProvinceNotFoundException(id);
		}
	}

	/**
	 * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
	 *
	 * @param id of the entity to find.
	 * @return the entity from the repository.
	 */
	public Province findOrElseThrow(Long id) {
		return provinceRepository.findById(id)
				.orElseThrow(() -> new ProvinceNotFoundException(id));
	}

}
