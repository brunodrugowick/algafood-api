package dev.drugowick.algaworks.algafoodapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "order_")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column is included to exemplify its use, but it's not
     * recommended here since the database will be generated
     * via script and this is not a validation for the object,
     * only a constraint on the database.
     * <p>
     * It's used, though, if you want to re-generate the ddl from JPA entities.
     */

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal deliveryFee;

    @Column(nullable = false)
    private BigDecimal total;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime", updatable = false)
    private LocalDateTime createdDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private LocalDateTime confirmationDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private LocalDateTime cancellationDate;

    @Column(nullable = true, columnDefinition = "datetime")
    private LocalDateTime deliveryDate;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @JsonIgnore
    @Embedded
    private Address deliveryAddress;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

}