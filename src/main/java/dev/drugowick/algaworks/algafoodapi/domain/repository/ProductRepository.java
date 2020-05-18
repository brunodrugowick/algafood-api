package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CustomJpaRepository<Product, Long> {

    @Query("from Product where restaurant.id = :restaurantId and id = :productId")
    Optional<Product> findById(@Param("restaurantId") Long restaurantId,@Param("productId") Long productId);

    List<Product> findByRestaurantId(Long restaurantId);

}
