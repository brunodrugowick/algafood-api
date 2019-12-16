package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    boolean existsByName(String name);

}
