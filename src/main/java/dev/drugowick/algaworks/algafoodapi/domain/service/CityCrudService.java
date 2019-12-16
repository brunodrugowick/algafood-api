package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.CityRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CityCrudService {

    private CityRepository cityRepository;
    private ProvinceRepository provinceRepository;

    public CityCrudService(CityRepository cityRepository, ProvinceRepository provinceRepository) {
        this.cityRepository = cityRepository;
        this.provinceRepository = provinceRepository;
    }

    public City save(City city) {
        Long provinceId = city.getProvince().getId();
        Optional<Province> province = provinceRepository.findById(provinceId);

        if (province.isEmpty()) {
            throw new EntityNotFoundException(
                    String.format("No province with ID %d", provinceId));
        }

        city.setProvince(province.get());
        return cityRepository.save(city);
    }

    public void delete(Long id) {
        try {
            cityRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format("City %d is being used by another entity and can not be removed.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There's no City with the id %d.", id));
        }
    }
}
