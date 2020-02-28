package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

/**
 * This is the implementation for {@link Multiple} annotation.
 *
 * The method `initialize` sets `multipleNumber` with the property set when someone is using the annotation.
 *
 * The method `isValid` implement the business rule.
 */
public class MultipleValidator implements ConstraintValidator<Multiple, Number> {

    private int multipleNumber;

    @Override
    public void initialize(Multiple constraintAnnotation) {
        this.multipleNumber = constraintAnnotation.number();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        boolean isValid = true;

        if (value != null) {
            BigDecimal incoming = BigDecimal.valueOf(value.doubleValue());
            BigDecimal multiple = BigDecimal.valueOf(this.multipleNumber);

            BigDecimal remainder = incoming.remainder(multiple);

            isValid = BigDecimal.ZERO.compareTo(remainder) == 0;
        }

        return isValid;
    }
}
