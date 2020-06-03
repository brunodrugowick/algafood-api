package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderListModel {

    private String code;
    private String status;
    private String clientEmail;
    private Long clientId;
    private BigDecimal total;
    private OffsetDateTime createdDate;
    private OffsetDateTime deliveryDate;
    private String restaurantName;
    private Long restaurantId;
}
