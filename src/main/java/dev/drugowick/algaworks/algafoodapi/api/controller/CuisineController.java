package dev.drugowick.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cuisine save(@RequestBody Cuisine cuisine) {
		return cuisineRepository.save(cuisine);
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<Cuisine> get(@PathVariable Long id) {
		Cuisine cuisine = cuisineRepository.get(id);
		
		if (cuisine != null) {
			return ResponseEntity.ok(cuisine);
		}
		
		return ResponseEntity.notFound().build();
	}

}
