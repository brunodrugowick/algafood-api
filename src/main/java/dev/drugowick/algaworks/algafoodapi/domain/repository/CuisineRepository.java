package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

	@Query("from Cuisine where name like %:name%")
	List<Cuisine> byNameLike(String name);
	// In Query Methods would be: List<Cuisine> findAllByNameContaining(String name);

	List<Cuisine> findTop1ByNameContaining(String name);
}
