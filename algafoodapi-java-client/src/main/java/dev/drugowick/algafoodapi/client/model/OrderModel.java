package dev.drugowick.algafoodapi.client.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class OrderModel {

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
