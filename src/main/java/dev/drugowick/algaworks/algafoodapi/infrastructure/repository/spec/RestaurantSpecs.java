package dev.drugowick.algaworks.algafoodapi.infrastructure.repository.spec;

import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> withFreeDelivery() {
        return (root, query, builder) -> builder.equal(root.get("deliveryFee"), BigDecimal.ZERO);
    }

    public static Specification<Restaurant> withSimilarName(String name) {
        return (root, query, builder) -> builder.like(root.get("name"), "%" + name + "%");
    }
}
