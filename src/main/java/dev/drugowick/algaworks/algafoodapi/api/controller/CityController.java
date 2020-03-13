package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.CityInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.CityModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CityInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CityCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

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

    @GetMapping
    public List<CityModel> list() {
        return genericModelAssembler.toCollectionModel(cityRepository.findAll(), CityModel.class);
    }

    @GetMapping("/{id}")
    public CityModel get(@PathVariable Long id) {
        return genericModelAssembler.toModel(cityCrudService.findOrElseThrow(id), CityModel.class);
    }

    @PostMapping
    public ResponseEntity<CityModel> save(@RequestBody @Valid CityInput cityInput) {
        City city = cityInputDisassembler.toDomain(cityInput);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(cityCrudService.save(city), CityModel.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityModel> update(@PathVariable @Valid Long id, @RequestBody CityInput cityInput) {
        City cityToUpdate = cityCrudService.findOrElseThrow(id);
        cityInputDisassembler.copyToDomainObject(cityInput, cityToUpdate);
        // The save method will update when an existing ID is being passed.
        return ResponseEntity.ok(genericModelAssembler.toModel(cityCrudService.save(cityToUpdate), CityModel.class));
    }

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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityCrudService.delete(id);
    }

}
