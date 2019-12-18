package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepository;
import dev.drugowick.algaworks.algafoodapi.domain.repository.RestaurantRepositoryQueries;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static dev.drugowick.algaworks.algafoodapi.infrastructure.repository.spec.RestaurantSpecs.withFreeDelivery;
import static dev.drugowick.algaworks.algafoodapi.infrastructure.repository.spec.RestaurantSpecs.withSimilarName;

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

    /**
     * The {@link RestaurantRepository} is injected here but as @Lazy otherwise we'd get a circular
     * reference error.
     * <p>
     * This is related to the confusing use of JpaSpecificationExecutor on the repository and the
     * idea to still have the Repository in control of the queries (not the Controller, for example).
     */
    private RestaurantRepository restaurantRepository;

    @Lazy
    public RestaurantRepositoryImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurant> findByAll(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine) {

        // A custom method that handles querying any Restaurant field.
        var jpql = new StringBuilder();
        jpql.append("from Restaurant where 1=1 ");

        var parameters = new HashMap<String, Object>();

        if (StringUtils.hasText(name)) {
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

    /**
     * Although this is an ok implementation and good for the majority of use cases,
     * this is cooler: https://gist.github.com/ulisseslima/7ff9d4ecc49470ccf5c5979b8d806eef
     * (from https://gist.github.com/ulisseslima).
     *
     * @param name
     * @param startFee
     * @param endingFee
     * @param cuisine
     * @return
     */
    @Override
    public List<Restaurant> findByAllCriteriaApi(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine) {

        // A custom method that handles querying any Restaurant field.

        var builder = entityManager.getCriteriaBuilder();
        // CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        var criteriaQuery = builder.createQuery(Restaurant.class);
        // CriteriaQuery<Restaurant> criteriaQuery = builder.createQuery(Restaurant.class);

        var root = criteriaQuery.from(Restaurant.class);
        // Root<Restaurant> root = criteriaQuery.from(Restaurant.class);

        var predicates = new ArrayList<Predicate>();
        if (StringUtils.hasText(name)) predicates.add(builder.like(root.get("name"), "%" + name + "%"));
        if (startFee != null) predicates.add(builder.greaterThanOrEqualTo(root.get("deliveryFee"), startFee));
        if (endingFee != null) predicates.add(builder.lessThanOrEqualTo(root.get("deliveryFee"), endingFee));
        if (StringUtils.hasLength(cuisine))
            predicates.add(builder.like(root.get("cuisine").get("name"), "%" + cuisine + "%"));

        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Override
    public List<Restaurant> findByAllQueryDsl(String name, BigDecimal startFee, BigDecimal endingFee, String cuisine) {

        // A custom method that handles querying any Restaurant field.

        // TODO implement using Querydsl. No idea what it is...

        return findByAllCriteriaApi(name, startFee, endingFee, cuisine);
    }

    @Override
    public List<Restaurant> findFreeDelivery(String name) {
        return restaurantRepository.findAll(withFreeDelivery().and(withSimilarName(name)));
    }
}
