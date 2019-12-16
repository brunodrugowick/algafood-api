package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

    // Although this works I'm not sure aout performance. Anyways...
    @Query("select count(p) > 0 from Permission p where name = :name")
    boolean existsByName(String name);
    // In Query Method would be: boolean existsByName(String name);

}
