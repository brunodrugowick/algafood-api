package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.CityCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return cityRepository.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> get(@PathVariable Long id) {
        City city = cityRepository.get(id);

        if (city != null) {
            return ResponseEntity.ok(city);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody City city) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (city.getId() != null || city.getProvince() == null) {
            return ResponseEntity.badRequest()
                    .body("Invalid request body.");
        }

        try {
            city = cityCrudService.save(city);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(city);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody City city) {
        City cityToUpdate = cityRepository.get(id);

        /**
         * Not found because the URI is not a valid resource on the application.
         */
        if (cityToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            BeanUtils.copyProperties(city, cityToUpdate, "id");
            // The save method will update when an existing ID is being passed.
            cityToUpdate = cityCrudService.save(cityToUpdate);
            return ResponseEntity.ok(cityToUpdate);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> cityMap) {
        City cityToUpdate = cityRepository.get(id);

        if (cityToUpdate == null) {
            return ResponseEntity.notFound().build();
        }

        ObjectMerger.mergeRequestBodyToGenericObject(cityMap, cityToUpdate, City.class);

        return update(id, cityToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            cityCrudService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityBeingUsedException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }
}
