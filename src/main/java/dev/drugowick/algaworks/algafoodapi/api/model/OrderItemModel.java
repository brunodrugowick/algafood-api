package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderItemModel {

    @ApiModelProperty(example = "1", required = true)
    private Long id;

    @ApiModelProperty(example = "Chilli Tapas")
    private String productName;

    @ApiModelProperty(example = "The name says it all")
    private String productDescription;

    @ApiModelProperty(example = "2", required = true)
    private Integer amount;

    @ApiModelProperty(example = "12")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "24")
    private BigDecimal totalPrice;

    @ApiModelProperty(example = "No salt, with  pepper")
    private String notes;
}
