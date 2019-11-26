package dev.drugowick.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {
	
	private CuisineRepository cuisineRepository;
	
	public CuisineController(CuisineRepository cuisineRepository) {
		super();
		this.cuisineRepository = cuisineRepository;
	}

	@GetMapping
	public List<Cuisine> list() {
		return cuisineRepository.list();
	}
	
	@GetMapping(value = { "/{id}" })
	public Cuisine get(@PathVariable Long id) {
		return cuisineRepository.get(id);
	}

}
