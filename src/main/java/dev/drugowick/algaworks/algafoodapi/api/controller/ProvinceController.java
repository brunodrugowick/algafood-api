package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProvinceCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	public ProvinceController(ProvinceRepository provinceRepository, ProvinceCrudService provinceCrudService) {
		this.provinceRepository = provinceRepository;
		this.provinceCrudService = provinceCrudService;
	}

	@GetMapping
	public List<Province> list() {
		return provinceRepository.findAll();
	}

	@GetMapping("/{id}")
	public Province get(@PathVariable Long id) {
		return provinceCrudService.findOrElseThrow(id);
	}

	@PostMapping
	public ResponseEntity<Province> save(@RequestBody Province province) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (province.getId() != null) {
            throw new GenericBusinessException("You should not send an ID when saving or updating an entity.");
        }

		province = provinceCrudService.save(province);

		return ResponseEntity.status(HttpStatus.CREATED).body(province);
	}

	@PutMapping(value = "/{id}")
	public Province update(@PathVariable Long id, @RequestBody Province province) {
		Province provinceToUpdate = provinceCrudService.findOrElseThrow(id);

		BeanUtils.copyProperties(province, provinceToUpdate, "id");

		// The save method will update when an existing ID is being passed.
		return provinceRepository.save(provinceToUpdate);
	}

	@PatchMapping("/{id}")
	public Province partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> provinceMap) {
		Province provinceToUpdate = provinceCrudService.findOrElseThrow(id);

		ObjectMerger.mergeRequestBodyToGenericObject(provinceMap, provinceToUpdate, Province.class);

		return update(id, provinceToUpdate);
	}

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		provinceCrudService.delete(id);
	}
	
}
