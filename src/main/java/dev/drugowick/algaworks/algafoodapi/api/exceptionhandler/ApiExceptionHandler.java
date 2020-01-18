package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handler(EntityNotFoundException exception) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(EntityBeingUsedException.class)
    public ResponseEntity<ApiError> handler(EntityBeingUsedException exception) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<ApiError> handler(GenericBusinessException exception) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

    /**
     * Example of handling exceptions of Spring (not your own code), in order to provide your custom model for other exceptions.
     *
     * @param exception HttpMediaTypeNotSupportedException
     * @return The ApiError with Unsupported Media Type (415) HTTP status code.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<?> handler(HttpMediaTypeNotSupportedException exception) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(apiError);
    }
}
