package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;

import java.util.List;

public interface ProvinceRepository {

	Province save(Province province);

	Province get(Long id);

	List<Province> list();

	void remove(Long id);

}
