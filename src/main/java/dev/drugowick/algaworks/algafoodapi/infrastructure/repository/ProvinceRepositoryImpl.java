package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Province;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProvinceRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
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
	public void remove(Long id) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		Province province = get(id);

		if (province == null) {
			throw new EmptyResultDataAccessException(1);
		}

		manager.remove(province);

	}

}
