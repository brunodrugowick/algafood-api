package dev.drugowick.algaworks.algafoodapi.infrastructure.repository.spec;

import dev.drugowick.algaworks.algafoodapi.domain.filter.OrderFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

public class OrderSpecs {

    public static Specification<Order> usingFilter(OrderFilter filter) {
        return (root, query, builder) -> {

            // These fetch lines solve the N+1 problem by telling JPA to fetch restaurants, users and delivery address
            // (and its nested objects) with the orders.
            // Additionally, since this is a Pageable resource, there can't be a fetch for the count query, which does
            // not return an Order, so that's what we check here.
            if (Order.class.equals(query.getResultType())) {
                root.fetch("restaurant").fetch("cuisine");
                root.fetch("client");
                root.fetch("deliveryAddress").fetch("city").fetch("province");
            }

            ArrayList<Predicate> predicates = new ArrayList<>();

            if (filter.getClientId() != null) {
                predicates.add(builder.equal(root.get("client"), filter.getClientId()));
            }

            if (filter.getRestaurantId() != null) {
                predicates.add(builder.equal(root.get("restaurant"), filter.getRestaurantId()));
            }

            if (filter.getCreateDateStart() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), filter.getCreateDateStart()));
            }

            if (filter.getCreateDateEnd() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"), filter.getCreateDateEnd()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
