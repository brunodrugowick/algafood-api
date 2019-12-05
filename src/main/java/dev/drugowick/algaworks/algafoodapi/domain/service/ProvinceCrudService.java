package dev.drugowick.algaworks.algafoodapi.domain.service;

import org.springframework.stereotype.Service;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

@Service
public class ProvinceCrudService {

	private ProvinceRepository provinceRepository;

	public ProvinceCrudService(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}
	
	public Province create(Province province) {
		return provinceRepository.save(province);
	}
	
	public Province read(Long id) {
		return provinceRepository.get(id);
	}
	
	public Province update(Long id, Province province) {
		province.setId(id);
		return provinceRepository.save(province);
	}
	
	public void delete(Long id) {
		Province provinceToDelete = read(id);
		if (provinceToDelete != null) {
			provinceRepository.remove(provinceToDelete);
		}
	}

}
