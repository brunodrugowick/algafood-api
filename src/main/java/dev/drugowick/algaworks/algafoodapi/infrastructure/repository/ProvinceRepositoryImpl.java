package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;

@Component
public class ProvinceRepositoryImpl implements ProvinceRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public Province save(Province province) {
		return manager.merge(province);
	}

	@Override
	public Province get(Long id) {
		return manager.find(Province.class, id);
	}

	@Override
	public List<Province> list() {
		return manager.createQuery("from Province", Province.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void remove(Province province) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		province = get(province.getId());
		manager.remove(province);

	}

}
