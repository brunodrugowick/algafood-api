package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.RestaurantModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.RestaurantNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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
		try {
			Restaurant restaurantToUpdate = restaurantCrudService.findOrElseThrow(id);
			restaurantInputDisassembler.copyToDomainObject(restaurantInput, restaurantToUpdate);
			// The save method will update when an existing ID is being passed.
			return ResponseEntity.ok(restaurantModelAssembler.toModel(restaurantCrudService.save(restaurantToUpdate)));
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String,
				Object> restaurantMap, HttpServletRequest request) {
		throw new GenericBusinessException("This method is temporarily not allowed.");
//		Restaurant restaurantToUpdate = restaurantCrudService.findOrElseThrow(id);
//
//		try {
//			ObjectMerger.mergeRequestBodyToGenericObject(restaurantMap, restaurantToUpdate, Restaurant.class);
//		} catch (IllegalArgumentException e) {
//			Throwable rootCause = ExceptionUtils.getRootCause(e);
//			var servletServerHttpRequest = new ServletServerHttpRequest(request);
//			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, servletServerHttpRequest);
//		}
//
//		validationService.validate(restaurantToUpdate, "restaurant");
//		return update(id, restaurantToUpdate);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		restaurantCrudService.delete(id);
	}

	@PutMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activate(@PathVariable Long restaurantId) {
		restaurantCrudService.activate(restaurantId);
	}

	@PutMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void activateList(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantCrudService.activate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/activation")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivateList(@RequestBody List<Long> restaurantIds) {
		try {
			restaurantCrudService.deactivate(restaurantIds);
		} catch (RestaurantNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/{restaurantId}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deactivate(@PathVariable Long restaurantId) {
		restaurantCrudService.deactivate(restaurantId);
	}

	@PutMapping("/{restaurantId}/opening")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void open(@PathVariable Long restaurantId) {
		restaurantCrudService.open(restaurantId);
	}

	@PutMapping("/{restaurantId}/closure" +
			"")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void close(@PathVariable Long restaurantId) {
		restaurantCrudService.close(restaurantId);
	}
}
