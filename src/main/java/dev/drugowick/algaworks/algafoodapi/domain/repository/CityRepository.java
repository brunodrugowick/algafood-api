package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;

import java.util.List;

public interface CityRepository {

    City save(City city);

    City get(Long id);

    List<City> list();

    void remove(Long id);

}
