package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findAllByCuisineNameContaining(String cuisine);

    List<Restaurant> findAllByDeliveryFeeBetween(BigDecimal startingFee, BigDecimal endingFee);

}
