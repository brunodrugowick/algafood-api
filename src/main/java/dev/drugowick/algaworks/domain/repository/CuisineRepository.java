package dev.drugowick.algaworks.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.domain.model.Cuisine;

public interface CuisineRepository {

	Cuisine save(Cuisine cuisine);
	Cuisine get(Long id);
	List<Cuisine> list();
	void remove(Cuisine cuisine);
}
