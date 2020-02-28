package dev.drugowick.algaworks.algafoodapi.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ApiValidationException extends RuntimeException {

    private BindingResult bindingResult;
}
