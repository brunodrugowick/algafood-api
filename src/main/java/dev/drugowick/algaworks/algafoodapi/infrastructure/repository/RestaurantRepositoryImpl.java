package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * This is a special class that Spring Data JPA (SDJ) detects and uses. This detection is based on the class name,
 * which is the name of the repository interface plus the string "Impl".
 * <p>
 * This is all that's necessary for SDJ to detect and use the methods implemented here, but, for the sake
 * of code readability, we make this implementation implement an interface and we extend the interface on the default
 * SDJ repository.
 */
@Repository
public class RestaurantRepositoryImpl implements RestaurantRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Restaurant> findByAll(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine) {

        // A custom method that handles querying any Restaurant field.
        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 1=1 ");

        var parameters = new HashMap<String, Object>();

        if (StringUtils.hasLength(name)) {
            jpql.append("and name like :name ");
            parameters.put("name", "%" + name + "%");
        }

        if (startFee != null) {
            jpql.append("and deliveryFee >= :startFee ");
            parameters.put("startFee", startFee);
        }

        if (endingFee != null) {
            jpql.append("and deliveryFee <= :endingFee ");
            parameters.put("endingFee", endingFee);
        }

        if (StringUtils.hasLength(cuisine)) {
            jpql.append("and cuisine.name like :cuisine");
            parameters.put("cuisine", "%" + cuisine + "%");
        }

        TypedQuery<Restaurant> query = entityManager.createQuery(jpql.toString(), Restaurant.class);

        parameters.forEach((key, value) -> query.setParameter(key, value));

        return query.getResultList();
    }
}
