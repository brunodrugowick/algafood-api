package dev.drugowick.algafoodapi.client.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class RestaurantSummaryModel {

    private Long id;
    private String name;
    private BigDecimal delivery;
    private CuisineSummaryModel cuisine;
    private Boolean active;
    private Boolean opened;

}
