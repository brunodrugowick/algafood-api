package dev.drugowick.algafoodapi.client.model;

import lombok.Data;

import java.util.List;

@Data
public class OrderResponse {
    
    private List<OrderModel> content;
    private String totalElements;
    private String totalPages;
    private String page;
    private String size; 
}
