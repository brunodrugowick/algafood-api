package dev.drugowick.algaworks.algafoodapi.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CuisineCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.EntityNotFoundException;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {
	
	private CuisineRepository cuisineRepository;
	private CuisineCrudService cuisinesCrudService;
	
	public CuisineController(CuisineRepository cuisineRepository, CuisineCrudService cuisinesCrudService) {
		this.cuisineRepository = cuisineRepository;
		this.cuisinesCrudService = cuisinesCrudService;
	}

	@GetMapping
	public List<Cuisine> list() {
		return cuisineRepository.list();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Cuisine> save(@RequestBody Cuisine cuisine) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (cuisine.getId() != null) {
			return ResponseEntity.badRequest()
					.build();
		}
		return ResponseEntity.ok(cuisinesCrudService.create(cuisine));
	}
	
	@GetMapping(value = { "/{id}" })
	public ResponseEntity<Cuisine> get(@PathVariable Long id) {
		Cuisine cuisine = cuisineRepository.get(id);
		
		if (cuisine != null) {
			return ResponseEntity.ok(cuisine);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cuisine> update(@PathVariable Long id, @RequestBody Cuisine cuisine) {
		Cuisine cuisineToUpdate = cuisineRepository.get(id);
		
		if (cuisineToUpdate != null) {
			BeanUtils.copyProperties(cuisine, cuisineToUpdate, "id");
			cuisineToUpdate = cuisinesCrudService.update(id, cuisineToUpdate);
			return ResponseEntity.ok(cuisineToUpdate);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Cuisine> delete(@PathVariable Long id) {
		try {
			cuisinesCrudService.delete(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntityBeingUsedException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
			
	}

}