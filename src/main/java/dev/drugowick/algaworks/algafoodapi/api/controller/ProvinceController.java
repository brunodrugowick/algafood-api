package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.ProvinceModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.ProvinceInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProvinceCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/provinces")
public class ProvinceController {

	/**
	 * I don't like it but, for the sake of simplicity for now, operations that do not require a
	 * transaction are using the repository (get, list) and operations that require a transaction
	 * are using the service layer (delete, save, update).
	 */

	private ProvinceRepository provinceRepository;
	private ProvinceCrudService provinceCrudService;
	private ValidationService validationService;
	private GenericModelAssembler<Province, ProvinceModel> genericModelAssembler;
	private GenericInputDisassembler<ProvinceInput, Province> genericInputDisassembler;

	public ProvinceController(ProvinceRepository provinceRepository,
							  ProvinceCrudService provinceCrudService,
							  ValidationService validationService,
							  GenericModelAssembler<Province, ProvinceModel> genericModelAssembler,
							  GenericInputDisassembler<ProvinceInput, Province> genericInputDisassembler) {
		this.provinceRepository = provinceRepository;
		this.provinceCrudService = provinceCrudService;
		this.validationService = validationService;
		this.genericModelAssembler = genericModelAssembler;
		this.genericInputDisassembler = genericInputDisassembler;
	}

	@GetMapping
	public List<ProvinceModel> list() {
		return genericModelAssembler.toCollectionModel(provinceRepository.findAll(), ProvinceModel.class);
	}

	@GetMapping("/{id}")
	public ProvinceModel get(@PathVariable Long id) {
		return genericModelAssembler.toModel(provinceCrudService.findOrElseThrow(id), ProvinceModel.class);
	}

	@PostMapping
	public ResponseEntity<ProvinceModel> save(@RequestBody @Valid ProvinceInput provinceInput) {

		Province province = genericInputDisassembler.toDomain(provinceInput, Province.class);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(genericModelAssembler.toModel(provinceRepository.save(province), ProvinceModel.class));
	}

	@PutMapping(value = "/{id}")
	public ProvinceModel update(@PathVariable Long id, @RequestBody @Valid ProvinceInput provinceInput) {
		Province provinceToUpdate = provinceCrudService.findOrElseThrow(id);
		genericInputDisassembler.copyToDomainObject(provinceInput, provinceToUpdate);
		// The save method will update when an existing ID is being passed.
		return genericModelAssembler.toModel(provinceRepository.save(provinceToUpdate), ProvinceModel.class);
	}

	@PatchMapping("/{id}")
	public Province partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> provinceMap) {
		throw new GenericBusinessException("This method is temporarily not allowed.");
//		Province provinceToUpdate = provinceCrudService.findOrElseThrow(id);
//
//		ObjectMerger.mergeRequestBodyToGenericObject(provinceMap, provinceToUpdate, Province.class);
//		validationService.validate(provinceToUpdate, "province");
//
//		return update(id, provinceToUpdate);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		provinceCrudService.delete(id);
	}
	
}
