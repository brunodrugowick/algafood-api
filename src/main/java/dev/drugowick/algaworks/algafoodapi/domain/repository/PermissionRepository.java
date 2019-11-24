package dev.drugowick.algaworks.algafoodapi.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;

public interface PermissionRepository {

	Permission save(Permission permission);
	Permission get(Long id);
	List<Permission> list();
	void remove(Permission permission);
	
}
