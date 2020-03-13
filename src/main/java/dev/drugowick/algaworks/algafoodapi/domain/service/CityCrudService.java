package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.CityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityCrudService {

    public static final String MSG_CITY_CONFLICT = "Operation on City %d conflicts with another entity and can not be performed.";

    private CityRepository cityRepository;
    private ProvinceCrudService provinceCrudService;

    public CityCrudService(CityRepository cityRepository, ProvinceRepository provinceRepository, ProvinceCrudService provinceCrudService) {
        this.cityRepository = cityRepository;
        this.provinceCrudService = provinceCrudService;
    }

    @Transactional
    public City save(City city) {
        Long provinceId = city.getProvince().getId();
        Province province = provinceCrudService.findOrElseThrow(provinceId);

        city.setProvince(province);
        try {
            return cityRepository.save(city);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_CITY_CONFLICT, city.getId()));
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
            // Flushing here guarantees the DB exceptions below can be caught.
            cityRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_CITY_CONFLICT, id));
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        }
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public City findOrElseThrow(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new CityNotFoundException(id));
    }
}
