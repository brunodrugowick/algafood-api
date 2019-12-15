package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CuisineCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cuisines")
public class CuisineController {

	/**
	 * I don't like it but, for the sake of simplicity for now, operations that do not require a
	 * transaction are using the repository (get, list) and operations that require a transaction
	 * are using the service layer (delete, save, update).
	 */

	private CuisineRepository cuisineRepository;
	private CuisineCrudService cuisinesCrudService;

	public CuisineController(CuisineRepository cuisineRepository, CuisineCrudService cuisinesCrudService) {
		this.cuisineRepository = cuisineRepository;
		this.cuisinesCrudService = cuisinesCrudService;
	}

	@GetMapping
	public List<Cuisine> list() {
		return cuisineRepository.findAll();
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
		Optional<Cuisine> cuisine = cuisineRepository.findById(id);

		if (cuisine.isPresent()) {
			return ResponseEntity.ok(cuisine.get());
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Cuisine> update(@PathVariable Long id, @RequestBody Cuisine cuisine) {
		Optional<Cuisine> cuisineToUpdate = cuisineRepository.findById(id);

		if (cuisineToUpdate.isPresent()) {
			BeanUtils.copyProperties(cuisine, cuisineToUpdate.get(), "id");
			Cuisine cuisineUpdated = cuisinesCrudService.update(id, cuisineToUpdate.get());
			return ResponseEntity.ok(cuisineUpdated);
		}

		return ResponseEntity.notFound().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> cuisineMap) {
		Optional<Cuisine> cuisineToUpdate = cuisineRepository.findById(id);

		if (cuisineToUpdate.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		ObjectMerger.mergeRequestBodyToGenericObject(cuisineMap, cuisineToUpdate.get(), Cuisine.class);

		return update(id, cuisineToUpdate.get());
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
/**
	@GetMapping(value = "/by-name")
	public List<Cuisine> cuisinesByName(@RequestParam("name") String name) {
		return cuisineRepository.listByName(name);
	}
 */
}
