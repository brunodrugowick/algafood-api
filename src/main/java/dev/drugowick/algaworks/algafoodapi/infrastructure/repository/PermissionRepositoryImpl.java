package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;

@Component
public class PermissionRepositoryImpl implements PermissionRepository {

	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	@Override
	public Permission save(Permission permission) {
		return manager.merge(permission);
	}

	@Override
	public Permission get(Long id) {
		return manager.find(Permission.class, id);
	}

	@Override
	public List<Permission> list() {
		return manager.createQuery("from Permission", Permission.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void remove(Permission permission) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		permission = get(permission.getId());
		manager.remove(permission);

	}

}
