package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderListModel {

    private Long id;
    private String status;
    private String clientEmail;
    private BigDecimal total;
    private OffsetDateTime createdDate;
    private OffsetDateTime deliveryDate;
    private String restaurantName;
}
