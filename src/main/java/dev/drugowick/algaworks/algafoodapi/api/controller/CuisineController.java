package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.PageModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.controller.openapi.CuisineControllerOpenApi;
import dev.drugowick.algaworks.algafoodapi.api.model.CuisineModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PageModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CuisineInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CuisineRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CuisineCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/cuisines", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class CuisineController implements CuisineControllerOpenApi {

	/**
	 * I don't like it but, for the sake of simplicity for now, operations that do not require a
	 * transaction are using the repository (get, list) and operations that require a transaction
	 * are using the service layer (delete, save, update).
	 */

	private final CuisineRepository cuisineRepository;
	private final CuisineCrudService cuisinesCrudService;
	private final GenericModelAssembler<Cuisine, CuisineModel> genericModelAssembler;
	private final GenericInputDisassembler<CuisineInput, Cuisine> genericInputDisassembler;
	private final PageModelAssembler<Cuisine, CuisineModel> pageAssembler;

	@GetMapping
	public PageModel<CuisineModel> list(@PageableDefault(size = 5) Pageable pageable) {
		Page<Cuisine> cuisinesPage = cuisineRepository.findAll(pageable);

		return pageAssembler.toCollectionModelPage(cuisinesPage, CuisineModel.class);
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

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		cuisinesCrudService.delete(id);
	}

	@GetMapping(value = "/by-name")
	public List<CuisineModel> cuisinesByName(@RequestParam("name") String name) {
		return genericModelAssembler.toCollectionModel(cuisineRepository.byNameLike(name), CuisineModel.class);
	}

}
