package dev.drugowick.algaworks.algafoodapi.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;

public interface CityRepository {
	
	City save(City city);
	City get(Long id);
	List<City> list();
	void remove(City city);

}
