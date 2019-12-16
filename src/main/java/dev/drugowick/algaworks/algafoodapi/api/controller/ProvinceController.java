package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProvinceCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public ResponseEntity<Province> get(@PathVariable Long id) {
		Optional<Province> province = provinceRepository.findById(id);

		if (province.isPresent()) {
			return ResponseEntity.ok(province.get());
		}

		return ResponseEntity.notFound().build();
	}

	@PostMapping
	public ResponseEntity<Province> save(@RequestBody Province province) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (province.getId() != null) {
			return ResponseEntity.badRequest()
					.build();
		}

		province = provinceCrudService.save(province);

		return ResponseEntity.status(HttpStatus.CREATED).body(province);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Province> update(@PathVariable Long id, @RequestBody Province province) {
		Optional<Province> provinceToUpdate = provinceRepository.findById(id);

		/**
		 * Not found because the URI is not a valid resource on the application.
		 */
		if (provinceToUpdate.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		BeanUtils.copyProperties(province, provinceToUpdate.get(), "id");
		// The save method will update when an existing ID is being passed.
		Province provinceUpdated = provinceRepository.save(provinceToUpdate.get());
		return ResponseEntity.ok(provinceUpdated);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> provinceMap) {
		Optional<Province> provinceToUpdate = provinceRepository.findById(id);

		if (provinceToUpdate.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		ObjectMerger.mergeRequestBodyToGenericObject(provinceMap, provinceToUpdate.get(), Province.class);

		return update(id, provinceToUpdate.get());
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		try {
			provinceCrudService.delete(id);
			return ResponseEntity.noContent().build();
		} catch (EntityBeingUsedException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
		} catch (EntityNotFoundException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
	}
	
}
