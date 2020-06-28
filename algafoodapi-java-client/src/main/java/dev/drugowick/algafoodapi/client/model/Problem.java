package dev.drugowick.algafoodapi.client.model;

import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
public class Problem {
    
    private Integer status;
    private OffsetDateTime timestamp;
    private String userMessage;
    private String error;
    private List<Object> errorObjects;
}
