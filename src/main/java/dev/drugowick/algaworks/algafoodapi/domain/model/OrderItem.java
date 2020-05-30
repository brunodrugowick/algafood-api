package dev.drugowick.algaworks.algafoodapi.domain.model;

import dev.drugowick.algaworks.algafoodapi.domain.validation.ValidationGroups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "order_item")
public class OrderItem {

    @NotNull(groups = ValidationGroups.OrderItemId.class)
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

    @NotNull
    @PositiveOrZero
    @Column(name = "amount", nullable = false)
    private Integer amount;

    @NotNull
    @PositiveOrZero
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @NotNull
    @PositiveOrZero
    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "notes")
    private String notes;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public void calculateTotal() {
        BigDecimal unitPrice = this.getUnitPrice();
        Integer amount = this.getAmount();

        if (unitPrice == null) unitPrice = BigDecimal.ZERO;
        if (amount == null) amount = 0;

        this.setTotalPrice(unitPrice.multiply(new BigDecimal(amount)));
    }
}
