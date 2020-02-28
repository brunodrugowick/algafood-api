package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.ApiValidationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

/**
 * A wrapper for a validation operation.
 */
@Service
public class ValidationService {

    private SmartValidator validator;

    public ValidationService(SmartValidator validator) {
        this.validator = validator;
    }

    /**
     * Validates an object via Bean Validation specification.
     *
     * @param objectToValidate the object to validate.
     * @param objectName is the name to be used on the {@link BindingResult} to reference the object that is being
     *                   validated.
     * @return a {@link BindingResult} which may include validation errors.
     */
    public BindingResult validate(Object objectToValidate, String objectName) {
        BeanPropertyBindingResult errors = new BeanPropertyBindingResult(objectToValidate, objectName);
        validator.validate(objectToValidate, errors);

        if (errors.hasErrors()) {
            throw new ApiValidationException(errors);
        }

        return errors;
    }
}
