package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ApiError {

    private LocalDateTime timestamp;
    private String message;

}
