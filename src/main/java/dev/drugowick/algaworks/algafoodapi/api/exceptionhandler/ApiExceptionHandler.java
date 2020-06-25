package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.ApiValidationException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Log4j2
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String DEFAULT_USER_MESSAGE = "Internal error. Please try again or contact the system administrator.";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handler(Exception exception, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiErrorType apiErrorType = ApiErrorType.INTERNAL_SERVER_ERROR;
        String detail = exception.getMessage();

        exception.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(exception, exception.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(ApiValidationException.class)
    public ResponseEntity<Object> handleApiValidationException(ApiValidationException exception, WebRequest request) {
        return handleValidationInternal(exception, exception.getBindingResult(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handler(EntityNotFoundException exception, WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorType apiErrorType = ApiErrorType.RESOURCE_NOT_FOUND;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityBeingUsedException.class)
    public ResponseEntity<?> handler(EntityBeingUsedException exception, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        ApiErrorType apiErrorType = ApiErrorType.ENTITY_BEING_USED;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> handler(GenericBusinessException exception, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ApiErrorType apiErrorType = ApiErrorType.BUSINESS_EXCEPTION;
        String detail = exception.getMessage();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ApiErrorType problemType = ApiErrorType.INVALID_PARAMETER;

        String detail = String.format("The URL param '%s' with value '%s', "
                        + "is invalid. The value must be compatible with the type %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        ApiError apiError = createApiErrorBuilder(status, problemType, detail)
                .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        } else if (rootCause instanceof DateTimeParseException) {
            return handleDateTimeParseException((DateTimeParseException) rootCause, headers, status, request);
        }

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = "Invalid request body. Check the syntax and properties of your request body" +
                ".";

        ApiError problem = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' does not exist. Remove or fix it and try again",
                path);

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
        String path = joinPath(ex.getPath());

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        String detail = String.format("The property '%s' with value '%s', "
                        + "is invalid. The value must be compatible with the type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    public ResponseEntity<Object> handleDateTimeParseException(DateTimeParseException exception,
                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {

        ApiErrorType apiErrorType = ApiErrorType.MESSAGE_NOT_READABLE;
        // TODO customize message... maybe.
        String detail = exception.getMessage();

        exception.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .build();

        return handleExceptionInternal(exception, apiError, new HttpHeaders(), status, request);
    }

    /**
     * Customizes Spring's handleExceptionInternal to create a default body for all possible exceptions already
     * handled by ResponseEntityExceptionHandler class that we extend here.
     *
     * @param ex      The exception, that won't be touched here..
     * @param body    The HTTP response body, that will be defined here as being or ApiError class.
     * @param headers The HTTP response headers, that won't be touched here.
     * @param status  The HTTP status, that won't be touched here.
     * @param request The HTTP request, that won't be touched here.
     * @return call Spring's handleExceptionInternal.
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        if (body == null) {
            body = ApiError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(DEFAULT_USER_MESSAGE)
                    .build();
        } else if (body instanceof String) {
            body = ApiError.builder()
                    .timestamp(OffsetDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(DEFAULT_USER_MESSAGE)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    /**
     * Since we throw a HttpMediaTypeNotAcceptable exception on one of our controllers, it's  necessary to overwrite
     * and properly handle the Exception. If not, the default method would call the handleExceptionInternal, which we
     * do override and then  it tries to send a body for the client...  which won't  be possible because for now this
     * exception occurs when the client  is requesting a, for instance, png but we can only provide a jpeg.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                      HttpHeaders headers,
                                                                      HttpStatus status,
                                                                      WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    private ResponseEntity<Object> handleValidationInternal(Exception exception, BindingResult bindingResult,
                                                            HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorType apiErrorType = ApiErrorType.INVALID_DATA;
        String detail = "One or more fields are invalid or missing. Please, make sure you're sending the data " +
                "according the API standards and try again.";

        List<ApiError.Object> errorsList = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    String message = messageSource.getMessage(objectError, LocaleContextHolder.getLocale());

                    String name = objectError.getObjectName();
                    if (objectError instanceof FieldError) name = ((FieldError) objectError).getField();

                    return ApiError.Object.builder()
                            .name(name)
                            .userMessage(message)
                            .build();
                })
                .collect(Collectors.toList());

        exception.printStackTrace();

        ApiError apiError = createApiErrorBuilder(status, apiErrorType, detail)
                .userMessage(detail)
                .errorObjects(errorsList)
                .build();

        return handleExceptionInternal(exception, apiError, headers, status, request);
    }

    /**
     * A helper method that returns an ApiErrorBuilder with the default values set primary via an ENUM ApiErrorType.
     * <p>
     * This allows for any additional customization/field to used together wit the builder.
     *
     * @param status       the HTTP Status.
     * @param apiErrorType the ENUM with the error type information (defines type and title).
     * @param detail       the detailed message for the error.
     * @return an ApiError.ApiErrorBuilder to be further customized with other property values.
     */
    private ApiError.ApiErrorBuilder createApiErrorBuilder(HttpStatus status, ApiErrorType apiErrorType, String detail) {
        return ApiError.builder()
                .status(status.value())
                .type(apiErrorType.getUri())
                .title(apiErrorType.getTitle())
                .detail(detail)
                .userMessage(DEFAULT_USER_MESSAGE)
                .timestamp(OffsetDateTime.now());
    }

    /**
     * A helper method to join property names with a dot.
     *
     * @param references
     * @return
     */
    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
