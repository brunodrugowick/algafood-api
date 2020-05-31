package dev.drugowick.algaworks.algafoodapi.domain.model;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus {
    CREATED("Created"),
    CONFIRMED("Confirmed", CREATED),
    DELIVERED("Delivered", CONFIRMED),
    CANCELLED("Cancelled", CREATED);

    private String description;
    private List<OrderStatus> previousOrderStatuses;

    OrderStatus(String description, OrderStatus... previousOrderStatuses) {
        this.description = description;
        this.previousOrderStatuses = Arrays.asList(previousOrderStatuses);
    }

    public String getDescription() {
        return description;
    }

    public boolean canNotTransitionTo(OrderStatus newStatus) {
        return !newStatus.previousOrderStatuses.contains(this);
    }
}
