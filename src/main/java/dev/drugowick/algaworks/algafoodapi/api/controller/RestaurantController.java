package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.RestaurantModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	private RestaurantRepository restaurantRepository;
	private RestaurantCrudService restaurantCrudService;
	private ValidationService validationService;
	private RestaurantModelAssembler restaurantModelAssembler;
	private RestaurantInputDisassembler restaurantInputDisassembler;

	public RestaurantController(RestaurantRepository restaurantRepository, RestaurantCrudService restaurantCrudService, ValidationService validationService, RestaurantModelAssembler restaurantModelAssembler, RestaurantInputDisassembler restaurantInputDisassembler) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCrudService = restaurantCrudService;
		this.validationService = validationService;
        this.restaurantModelAssembler = restaurantModelAssembler;
        this.restaurantInputDisassembler = restaurantInputDisassembler;
    }

	@GetMapping
	public List<RestaurantModel> list() {
	    return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantInputDisassembler.toDomain(restaurantInput);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurantModelAssembler.toModel(restaurantCrudService.save(restaurant)));
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@GetMapping(value = {"/{id}"})
	public RestaurantModel get(@PathVariable Long id) {
		return restaurantModelAssembler.toModel(restaurantCrudService.findOrElseThrow(id));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,
									@RequestBody @Valid RestaurantInput restaurantInput) {

		Restaurant restaurant = restaurantInputDisassembler.toDomain(restaurantInput);
		Restaurant restaurantToUpdate = restaurantCrudService.findOrElseThrow(id);

		BeanUtils.copyProperties(restaurant, restaurantToUpdate,
				"id", "paymentMethods", "address", "createdDate", "updatedDate");
		try {
			// The save method will update when an existing ID is being passed.
			restaurantCrudService.save(restaurantToUpdate);
			return ResponseEntity.ok(restaurantModelAssembler.toModel(restaurantToUpdate));
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

//



	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		restaurantCrudService.delete(id);
	}


}
