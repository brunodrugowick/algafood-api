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

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProvinceCrudService;

@RestController
@RequestMapping(value = "/provinces")
public class ProvinceController {

	private ProvinceRepository provinceRepository;
	private ProvinceCrudService provinceCrudService;

	public ProvinceController(ProvinceRepository provinceRepository, ProvinceCrudService provinceCrudService) {
		this.provinceRepository = provinceRepository;
		this.provinceCrudService = provinceCrudService;
	}

	@GetMapping
	public List<Province> list() {
		return provinceRepository.list();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Province> save(@RequestBody Province province) {
		// Temporary. Client should not send an ID when posting. See #2.
		if (province.getId() != null) {
			return ResponseEntity.badRequest()
					.build();
		}
		return ResponseEntity.ok(provinceCrudService.create(province));
	}
	
	@GetMapping(value = {"/{id}"})
	public ResponseEntity<Province> get(@PathVariable Long id) {
		Province province = provinceRepository.get(id);
		
		if (province != null) {
			return ResponseEntity.ok(province);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Province> update(@PathVariable Long id, @RequestBody Province province) {
		Province provinceToUpdate = provinceRepository.get(id);
		
		if (provinceToUpdate != null) {
			BeanUtils.copyProperties(province, provinceToUpdate, "id");
			provinceToUpdate = provinceRepository.save(provinceToUpdate);
			return ResponseEntity.ok(provinceToUpdate);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Province> delete(@PathVariable Long id) {
		try {
			Province province = provinceRepository.get(id);
			if (province != null) {
				provinceRepository.remove(province);
				return ResponseEntity.noContent().build();
			}
			
			return ResponseEntity.notFound().build();
			
		} catch (DataIntegrityViolationException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
