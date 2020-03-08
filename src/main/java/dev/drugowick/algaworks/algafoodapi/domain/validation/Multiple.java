package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This is a custom annotation implemented by {@link MultipleValidator}.
 *
 * TODO: a way to return the number provided on the annotation on the default message without using messages.properties.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { MultipleValidator.class })
public @interface Multiple {

    String message() default "must be multiple of {number}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    int number();
}
