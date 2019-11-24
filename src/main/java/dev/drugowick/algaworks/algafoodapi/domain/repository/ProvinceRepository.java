package dev.drugowick.algaworks.algafoodapi.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;

public interface ProvinceRepository {
	
	Province save(Province province);
	Province get(Long id);
	List<Province> list();
	void remove(Province province);

}
