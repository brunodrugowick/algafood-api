package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {

    List<Restaurant> findByAll(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine);

    List<Restaurant> findByAllCriteriaApi(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine);

    List<Restaurant> findByAllQueryDsl(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine);

    List<Restaurant> findFreeDelivery(String name);
}
