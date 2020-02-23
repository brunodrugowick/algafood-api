package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CityCrudService;
import org.springframework.beans.BeanUtils;
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

    public CityController(CityRepository cityRepository, CityCrudService cityCrudService) {
        this.cityRepository = cityRepository;
        this.cityCrudService = cityCrudService;
    }

    @GetMapping
    public List<City> list() {
        return cityRepository.findAll();
    }

    @GetMapping("/{id}")
    public City get(@PathVariable Long id) {
        return cityCrudService.findOrElseThrow(id);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid City city) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (city.getId() != null || city.getProvince() == null) {
            throw new GenericBusinessException("You should not send an ID when saving or updating an entity.");
        }

        try {
            city = cityCrudService.save(city);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(city);
        } catch (EntityNotFoundException e) {
            throw new GenericBusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable @Valid Long id, @RequestBody City city) {
        City cityToUpdate = cityCrudService.findOrElseThrow(id);

        BeanUtils.copyProperties(city, cityToUpdate, "id");

        try {
            // The save method will update when an existing ID is being passed.
            cityToUpdate = cityCrudService.save(cityToUpdate);
            return ResponseEntity.ok(cityToUpdate);
        } catch (EntityNotFoundException e) {
            throw new GenericBusinessException(e.getMessage(), e);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> cityMap) {
        City cityToUpdate = cityCrudService.findOrElseThrow(id);

        ObjectMerger.mergeRequestBodyToGenericObject(cityMap, cityToUpdate, City.class);

        return update(id, cityToUpdate);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        cityCrudService.delete(id);
    }

}
