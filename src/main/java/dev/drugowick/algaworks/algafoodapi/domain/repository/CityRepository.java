package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findFirstByNameContainingAndProvinceAbbreviationContaining(String cityName, String provinceAbbreviation);

    List<City> findAllByNameStartingWith(String cityStart);

    int countDistinctByProvinceAbbreviation(String provinceAbbreviation);
}
