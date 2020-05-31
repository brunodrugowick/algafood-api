package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class OrderModel {

    private String code;
    private String status;
    private BigDecimal subtotal;
    private BigDecimal deliveryFee;
    private BigDecimal total;
    private OffsetDateTime createdDate;
    private OffsetDateTime confirmationDate;
    private OffsetDateTime cancellationDate;
    private OffsetDateTime deliveryDate;
    private PaymentMethodModel paymentMethod;
    private RestaurantSummaryModel restaurant;
    // Keep in mind that you can traverse objects and call specific properties on these models when using ModelMapper.
    // One could, for example, use an String clientName and the UserModel name property would be used.
    private UserModel client;
    private AddressModel deliveryAddress;
    private List<OrderItemModel> orderItems;
}
