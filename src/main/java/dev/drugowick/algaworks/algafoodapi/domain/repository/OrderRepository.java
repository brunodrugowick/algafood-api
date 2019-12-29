package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CustomJpaRepository<Order, Long> {

}
