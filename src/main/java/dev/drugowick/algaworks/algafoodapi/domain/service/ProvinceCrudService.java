package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ProvinceCrudService {

	private ProvinceRepository provinceRepository;

	public ProvinceCrudService(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}

	public Province save(Province province) {
		return provinceRepository.save(province);
	}

	public void delete(Long id) {
		try {
			provinceRepository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntityBeingUsedException(
					String.format("Province %d is being used by another entity and can not be removed.", id));
		} catch (EmptyResultDataAccessException e) {
			throw new EntityNotFoundException(
					String.format("There's no Province with the id %d.", id));
		}
	}

}
