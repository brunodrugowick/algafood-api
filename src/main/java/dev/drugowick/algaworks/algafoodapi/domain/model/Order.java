package dev.drugowick.algaworks.algafoodapi.domain.model;

import dev.drugowick.algaworks.algafoodapi.domain.event.OrderConfirmedEvent;
import dev.drugowick.algaworks.algafoodapi.domain.event.listener.OrderCancelledEvent;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.validation.IfFreeDeliverySubtotalEqualsTotal;
import dev.drugowick.algaworks.algafoodapi.domain.validation.ValidationGroups;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

// A custom annotation that validates if subtotal equals total when deliveryFee equals 0.
@IfFreeDeliverySubtotalEqualsTotal(fieldDeliveryFee = "deliveryFee", fieldSubtotal = "subtotal", fieldTotal = "total")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity(name = "order_")
public class Order extends AbstractAggregateRoot<Order> {

    @NotNull(groups = ValidationGroups.OrderId.class)
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    /**
     * @Column is included to exemplify its use, but it's not
     * recommended here since the database will be generated
     * via script and this is not a validation for the object,
     * only a constraint on the database.
     * <p>
     * It's used, though, if you want to re-generate the ddl from JPA entities.
     */

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal subtotal;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal deliveryFee;

    @NotNull
    @PositiveOrZero
    @Column(nullable = false)
    private BigDecimal total;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime", updatable = false)
    private OffsetDateTime createdDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private OffsetDateTime confirmationDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private OffsetDateTime cancellationDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private OffsetDateTime deliveryDate;

    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.PaymentMethodId.class)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.RestaurantId.class)
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @NotNull
    @Valid
    @ConvertGroup(from = Default.class, to = ValidationGroups.UserId.class)
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.CREATED;

    @Embedded
    private Address deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @PrePersist
    private void generateCode() {
        this.code = UUID.randomUUID().toString();
    }

    public void calculateTotal() {
        getItems().forEach(OrderItem::calculateTotal);

        this.subtotal = getItems().stream()
                .map(orderItem -> orderItem.getTotalPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        this.total = this.subtotal.add(this.deliveryFee);
    }

    public void confirm() {
        setStatus(OrderStatus.CONFIRMED);
        setConfirmationDate(OffsetDateTime.now(ZoneOffset.UTC));

        registerEvent(new OrderConfirmedEvent(this));
    }

    public void deliver() {
        setStatus(OrderStatus.DELIVERED);
        setDeliveryDate(OffsetDateTime.now(ZoneOffset.UTC));
    }

    public void cancel() {
        setStatus(OrderStatus.CANCELLED);
        setCancellationDate(OffsetDateTime.now(ZoneOffset.UTC));

        registerEvent(new OrderCancelledEvent(this));
    }

    /**
     * This overrides the Lombok implementation.
     *
     * It's private to limit status modifications to be called from within the object, via the named methods confirm,
     * delivery and cancel.
     */
    private void setStatus (OrderStatus newStatus) {
        if (getStatus().canNotTransitionTo(newStatus)) {
            throw new GenericBusinessException(
                    String.format("Impossible to transition order %s from %s to %s.",
                            getCode(), getStatus().getDescription(), newStatus.getDescription()));
        }

        this.status = newStatus;
    }

}
