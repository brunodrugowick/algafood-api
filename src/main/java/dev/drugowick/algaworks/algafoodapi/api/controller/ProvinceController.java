package dev.drugowick.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

@RestController
@RequestMapping(value = "/provinces")
public class ProvinceController {

	private ProvinceRepository provinceRepository;
	
	public ProvinceController(ProvinceRepository provinceRepository) {
		this.provinceRepository = provinceRepository;
	}

	@GetMapping
	public List<Province> list() {
		return provinceRepository.list();
	}
	
	@GetMapping(value = {"/{id}"})
	public Province get(@PathVariable Long id) {
		return provinceRepository.get(id);
	}
	
}
