package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.CuisineModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CuisineInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CuisineCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	private GenericModelAssembler<Cuisine, CuisineModel> genericModelAssembler;
	private GenericInputDisassembler<CuisineInput, Cuisine> genericInputDisassembler;

	public CuisineController(CuisineRepository cuisineRepository,
							 CuisineCrudService cuisinesCrudService,
							 ValidationService validationService,
							 GenericModelAssembler<Cuisine, CuisineModel> genericModelAssembler,
							 GenericInputDisassembler<CuisineInput, Cuisine> genericInputDisassembler) {
		this.cuisineRepository = cuisineRepository;
		this.cuisinesCrudService = cuisinesCrudService;
		this.validationService = validationService;
		this.genericModelAssembler = genericModelAssembler;
		this.genericInputDisassembler = genericInputDisassembler;
	}

	@GetMapping
	public Page<CuisineModel> list(@PageableDefault(size = 5) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisineRepository.findAll(pageable);

		List<CuisineModel> cuisineModels = genericModelAssembler.toCollectionModel(cuisinesPage.getContent(), CuisineModel.class);

		Page<CuisineModel> cuisineModelPage = new PageImpl<>(cuisineModels, pageable, cuisinesPage.getTotalElements());

		return cuisineModelPage;
	}

	@PostMapping
	public ResponseEntity<CuisineModel> save(@RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisine = genericInputDisassembler.toDomain(cuisineInput, Cuisine.class);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(genericModelAssembler.toModel(cuisinesCrudService.save(cuisine), CuisineModel.class));
	}

	@GetMapping(value = {"/{id}"})
	public CuisineModel get(@PathVariable Long id) {
		return genericModelAssembler.toModel(cuisinesCrudService.findOrElseThrow(id), CuisineModel.class);
	}

	@PutMapping(value = "/{id}")
	public CuisineModel update(@PathVariable Long id,
						  @RequestBody @Valid CuisineInput cuisineInput) {
		Cuisine cuisineToUpdate = cuisinesCrudService.findOrElseThrow(id);
		genericInputDisassembler.copyToDomainObject(cuisineInput, cuisineToUpdate);
		// The save method will update when an existing ID is being passed.
		return genericModelAssembler.toModel(cuisinesCrudService.save(cuisineToUpdate), CuisineModel.class);
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
		return genericModelAssembler.toCollectionModel(cuisineRepository.byNameLike(name), CuisineModel.class);
	}

}
