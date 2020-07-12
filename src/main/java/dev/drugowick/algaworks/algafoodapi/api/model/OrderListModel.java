package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
public class OrderListModel {

    @ApiModelProperty(example = "aff37747-f651-4463-a6c3-03af003ccde9")
    private String code;

    @ApiModelProperty(example = "CREATED")
    private String status;

    @ApiModelProperty(example = "antas@email.com")
    private String clientEmail;

    @ApiModelProperty(example = "1")
    private Long clientId;

    @ApiModelProperty(example = "112")
    private BigDecimal total;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime createdDate;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime deliveryDate;

    @ApiModelProperty(example = "Restaurante da Dona Florinda")
    private String restaurantName;

    @ApiModelProperty(example = "1")
    private Long restaurantId;
}
