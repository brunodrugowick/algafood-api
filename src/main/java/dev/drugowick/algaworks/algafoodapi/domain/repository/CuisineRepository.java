package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;

import java.util.List;

public interface CuisineRepository {

	Cuisine save(Cuisine cuisine);

	Cuisine get(Long id);

	List<Cuisine> list();

	List<Cuisine> listByName(String name);

	void remove(Long id);
}
