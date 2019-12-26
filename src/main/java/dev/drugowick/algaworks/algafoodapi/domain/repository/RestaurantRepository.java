package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepository extends CustomJpaRepository<Restaurant, Long>,
        RestaurantRepositoryQueries, JpaSpecificationExecutor<Restaurant> {

    @Query("from Restaurant r join fetch r.cuisine")
    List<Restaurant> findAll();

    List<Restaurant> byCuisineLike(@Param("name") String cuisine);
    // In Query Method would be: List<Restaurant> findAllByCuisineNameContaining(String cuisine);
    // With annotation would be: //@Query("from Restaurant r join r.cuisine c where c.name like %:name%")


    List<Restaurant> byDeliveryFee(@Param("initial") BigDecimal startingFee,
                                   @Param("final") BigDecimal endingFee);
    // In QueryMethod would be: List<Restaurant> findAllByDeliveryFeeBetween(BigDecimal startingFee, BigDecimal endingFee);
    // With annotation would be: //@Query("from Restaurant where deliveryFee >= :initial and deliveryFee <= :final")
}
