package dev.drugowick.algaworks.algafoodapi.api.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@JsonFilter("orderFieldsFilter")
@Getter
@Setter
public class OrderListModel {

    private String code;
    private String status;
    private String clientEmail;
    private BigDecimal total;
    private OffsetDateTime createdDate;
    private OffsetDateTime deliveryDate;
    private String restaurantName;
}
