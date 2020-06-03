package dev.drugowick.algaworks.algafoodapi.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DailySales {

    private Date day;
    private Long salesQuantity;
    private BigDecimal salesAmount;
}
