package dev.drugowick.algaworks.algafoodapi.api.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderModel {

    @ApiModelProperty(example = "aff37747-f651-4463-a6c3-03af003ccde9")
    private String code;

    @ApiModelProperty(example = "CREATED")
    private String status;

    @ApiModelProperty(example = "100")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "12")
    private BigDecimal deliveryFee;

    @ApiModelProperty(example = "112")
    private BigDecimal total;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime createdDate;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime confirmationDate;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime cancellationDate;

    @ApiModelProperty(example = "2020-07-12T18:34:59.691766Z")
    private OffsetDateTime deliveryDate;

    private PaymentMethodModel paymentMethod;
    private RestaurantSummaryModel restaurant;
    // Keep in mind that you can traverse objects and call specific properties on these models when using ModelMapper.
    // One could, for example, use an String clientName and the UserModel name property would be used.
    private UserModel client;
    private AddressModel deliveryAddress;
    private List<OrderItemModel> orderItems;
}
