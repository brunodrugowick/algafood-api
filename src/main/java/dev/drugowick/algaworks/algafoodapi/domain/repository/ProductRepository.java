package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CustomJpaRepository<Product, Long>, ProductRepositoryQueries {

    @Query("from Product where restaurant.id = :restaurantId and id = :productId")
    Optional<Product> findById(@Param("restaurantId") Long restaurantId,@Param("productId") Long productId);

    @Query("from Product where active = true and restaurant.id = :restaurantId")
    List<Product> findActiveByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<Product> findByRestaurantId(Long restaurantId);

    //@Query("select pf from ProductPhoto pf join Product p where p.restaurant.id = :restaurantId and pf.product.id = :productId")
    @Query("from ProductPhoto where product.restaurant.id = :restaurantId and product.id = :productId")
    Optional<ProductPhoto> findPhotoById(@Param("restaurantId") Long restaurantId,@Param("productId") Long productId);

}
