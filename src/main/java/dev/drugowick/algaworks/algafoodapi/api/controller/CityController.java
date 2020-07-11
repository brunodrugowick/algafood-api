package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.CityInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.CityModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CityInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CityCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Api(tags = "Cities")
@RestController
@RequestMapping("cities")
public class CityController {

    /**
     * I don't like it but, for the sake of simplicity for now, operations that do not require a
     * transaction are using the repository (get, list) and operations that require a transaction
     * are using the service layer (delete, save, update).
     */

    private CityRepository cityRepository;
    private CityCrudService cityCrudService;
    private ValidationService validationService;
    private GenericModelAssembler<City, CityModel> genericModelAssembler;
    private CityInputDisassembler cityInputDisassembler;

    public CityController(CityRepository cityRepository,
                          CityCrudService cityCrudService,
                          ValidationService validationService,
                          GenericModelAssembler<City, CityModel> genericModelAssembler,
                          CityInputDisassembler cityInputDisassembler) {
        this.cityRepository = cityRepository;
        this.cityCrudService = cityCrudService;
        this.validationService = validationService;
        this.genericModelAssembler = genericModelAssembler;
        this.cityInputDisassembler = cityInputDisassembler;
    }

    @ApiOperation("Retrieves the list of cities")
    @GetMapping
    public List<CityModel> list() {
        return genericModelAssembler.toCollectionModel(cityRepository.findAll(), CityModel.class);
    }

    @ApiOperation("Retrieves a city by ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Not a valid request", response = ApiError.class)
    })
    @GetMapping("/{id}")
    public CityModel get(@ApiParam(value = "City ID", example = "1") @PathVariable Long id) {
        return genericModelAssembler.toModel(cityCrudService.findOrElseThrow(id), CityModel.class);
    }

    @ApiOperation("Creates a new city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityModel> save(@ApiParam(value = "A representation of a new city") @RequestBody @Valid CityInput cityInput) {
        City city = cityInputDisassembler.toDomain(cityInput);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(cityCrudService.save(city), CityModel.class));
    }

    @ApiOperation("Updates a city by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@ApiParam(value = "City ID", example = "1")
                                            @PathVariable @Valid Long id,

                                            @ApiParam(value = "A representation with new data of a city")
                                            @RequestBody CityInput cityInput) {
        City cityToUpdate = cityCrudService.findOrElseThrow(id);
        cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);
        // The save method will update when an existing ID is being passed.
        return ResponseEntity.ok(genericModelAssembler.toModel(cityCrudService.save(cityToUpdate), CityModel.class));
    }

    @ApiOperation("Updates a city by ID")
    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> cityMap) {
        throw new GenericBusinessException("This method is temporarily not allowed.");
//        City cityToUpdate = cityCrudService.findOrElseThrow(id);
//
//        ObjectMerger.mergeRequestBodyToGenericObject(cityMap, cityToUpdate, City.class);
//        validationService.validate(cityToUpdate, "city");
//
//        return update(id, cityToUpdate);
    }

    @ApiOperation("Removes a city by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City removed"),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "City ID", example = "1") @PathVariable Long id) {
        cityCrudService.delete(id);
    }

}
