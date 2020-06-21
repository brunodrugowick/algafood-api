package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.RestaurantModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.RestaurantModel;
import dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern.RestaurantDTO;
import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
import dev.drugowick.algaworks.algafoodapi.api.model.view.RestaurantView;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.RestaurantNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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
	private final GenericModelAssembler<Restaurant, RestaurantDTO.Response.Default> modelAssembler;
	private final GenericModelAssembler<Restaurant, RestaurantDTO.Response.Summary> modelAssemblerSummary;
	private RestaurantDisassembler restaurantDisassembler;

	public RestaurantController(
			RestaurantRepository restaurantRepository,
			RestaurantCrudService restaurantCrudService,
			ValidationService validationService,
			RestaurantModelAssembler restaurantModelAssembler,
			GenericModelAssembler<Restaurant, RestaurantDTO.Response.Default> modelAssembler,
			GenericModelAssembler<Restaurant, RestaurantDTO.Response.Summary> modelAssemblerSummary,
			RestaurantDisassembler restaurantDisassembler) {
		this.restaurantRepository = restaurantRepository;
		this.restaurantCrudService = restaurantCrudService;
		this.validationService = validationService;
        this.restaurantModelAssembler = restaurantModelAssembler;
		this.modelAssembler = modelAssembler;
		this.modelAssemblerSummary = modelAssemblerSummary;
		this.restaurantDisassembler = restaurantDisassembler;
    }

    @GetMapping
	public List<RestaurantDTO.Response.Default> list() {
	    return modelAssembler.toCollectionModel(restaurantRepository.findAll(), RestaurantDTO.Response.Default.class);
	}

	@GetMapping(value = "/jackson-view")
	public MappingJacksonValue listJacksonView(@RequestParam(required = false) String projection) {
		List<Restaurant> restaurantList = restaurantRepository.findAll();
		List<RestaurantModel> restaurantsModelList = restaurantModelAssembler.toCollectionModel(restaurantList);

		MappingJacksonValue restaurantsModelWrapper = new MappingJacksonValue(restaurantsModelList);

		restaurantsModelWrapper.setSerializationView(RestaurantView.Summary.class);
		if ("name-only".equals(projection)) {
			restaurantsModelWrapper.setSerializationView(RestaurantView.NameOnly.class);
		} else if ("all-fields".equals(projection)) {
			restaurantsModelWrapper.setSerializationView(null);
		}

		return restaurantsModelWrapper;
	}

	@GetMapping(value = "/dto-pattern")
	public List<?> listDtoPattern(@RequestParam(required = false) String projection) {
		List<Restaurant> restaurantList = restaurantRepository.findAll();

		if ("all-fields".equals(projection)) {
			return modelAssembler.toCollectionModel(restaurantList, RestaurantDTO.Response.Default.class);
		}

		return modelAssemblerSummary.toCollectionModel(restaurantList, RestaurantDTO.Response.Summary.class);
	}

//	@JsonView(RestaurantView.Summary.class)
//	@GetMapping(value = "/jackson-view", params = "projection=summary")
//	public List<RestaurantModel> listSummaryJsonView() {
//	    return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
//	}
//
//	@JsonView(RestaurantView.NameOnly.class)
//	@GetMapping(value = "/jackson-view", params = "projection=name-only")
//	public List<RestaurantModel> listNameOnlyJsonView() {
//	    return restaurantModelAssembler.toCollectionModel(restaurantRepository.findAll());
//	}

	@PostMapping
	public ResponseEntity<?> save(@RequestBody @Valid RestaurantInput restaurantInput) {
		try {
			Restaurant restaurant = restaurantDisassembler.inputToDomain(restaurantInput);

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurantModelAssembler.toModel(restaurantCrudService.save(restaurant)));
		} catch (EntityNotFoundException e) {
			throw new GenericBusinessException(e.getMessage(), e);
		}
	}

	@PostMapping(value = "/dto-pattern")
	public ResponseEntity<?> saveDtoPattern(@RequestBody @Valid RestaurantDTO.Request.Default restaurantInput) {
		try {
			Restaurant restaurant = restaurantDisassembler.dtoToDomain(restaurantInput);

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
			restaurantDisassembler.copyToDomainObject(restaurantInput, restaurantToUpdate);
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
