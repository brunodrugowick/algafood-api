package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findFirstByNameContainingAndProvinceAbbreviationContaining(String cityName, String provinceAbbreviation);
    // For this one I believe it's better the giant method name...

    @Query("from City where name like :cityStart%")
    List<City> byNameStarting(String cityStart);
    // In Query Methods would be: List<City> findAllByNameStartingWith(String cityStart);

    @Query("select count(c) from City c join c.province p where p.abbreviation = :provinceAbbreviation")
    int countByProvince(String provinceAbbreviation);
    // In Query Methods would be: int countDistinctByProvinceAbbreviation(String provinceAbbreviation);
}
