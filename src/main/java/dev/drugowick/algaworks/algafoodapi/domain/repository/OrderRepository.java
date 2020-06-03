package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("from order_ o join fetch o.client join fetch o.restaurant r join fetch r.cuisine join fetch r.address a " +
            "join fetch a.city c join fetch c.province")
    List<Order> findAll();

    Optional<Order> findByCode(String orderCode);
}
