package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemModel {

    private Long id;
    private String productName;
    private String productDescription;
    private Integer amount;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private String notes;
}
