package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.CuisineInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.CuisineModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.CuisineModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CuisineInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CuisineCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
	private ValidationService validationService;
	private CuisineModelAssembler cuisineModelAssembler;
	private CuisineInputDisassembler cuisineInputDisassembler;

	public CuisineController(CuisineRepository cuisineRepository, CuisineCrudService cuisinesCrudService, ValidationService validationService, CuisineModelAssembler cuisineModelAssembler, CuisineInputDisassembler cuisineInputDisassembler) {
		this.cuisineRepository = cuisineRepository;
		this.cuisinesCrudService = cuisinesCrudService;
		this.validationService = validationService;
		this.cuisineModelAssembler = cuisineModelAssembler;
		this.cuisineInputDisassembler = cuisineInputDisassembler;
	}

	@GetMapping
	public List<CuisineModel> list() {
		return cuisineModelAssembler.toCollectionModel(cuisineRepository.findAll());
	}

	@PostMapping
	public ResponseEntity<CuisineModel> save(@RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisine = cuisineInputDisassembler.toDomain(cuisineInput);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(cuisineModelAssembler.toModel(cuisinesCrudService.save(cuisine)));
	}

	@GetMapping(value = {"/{id}"})
	public CuisineModel get(@PathVariable Long id) {
		return cuisineModelAssembler.toModel(cuisinesCrudService.findOrElseThrow(id));
	}

	@PutMapping(value = "/{id}")
	public CuisineModel update(@PathVariable Long id,
						  @RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisineToUpdate = cuisinesCrudService.findOrElseThrow(id);
		cuisineInputDisassembler.copyToDomainObject(cuisineInput, cuisineToUpdate);
		// The save method will update when an existing ID is being passed.
		return cuisineModelAssembler.toModel(cuisinesCrudService.save(cuisineToUpdate));
	}

	@PatchMapping("/{id}")
	public Cuisine partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> cuisineMap) {
		throw new GenericBusinessException("This method is temporarily not allowed.");
//		Cuisine cuisineToUpdate = cuisinesCrudService.findOrElseThrow(id);
//
//		ObjectMerger.mergeRequestBodyToGenericObject(cuisineMap, cuisineToUpdate, Cuisine.class);
//		validationService.validate(cuisineToUpdate, "cuisine");
//
//		return update(id, cuisineToUpdate);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		cuisinesCrudService.delete(id);
	}

	@GetMapping(value = "/by-name")
	public List<CuisineModel> cuisinesByName(@RequestParam("name") String name) {
		return cuisineModelAssembler.toCollectionModel(cuisineRepository.byNameLike(name));
	}

}
