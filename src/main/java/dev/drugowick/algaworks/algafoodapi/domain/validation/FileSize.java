package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This is a custom annotation implemented by {@link FileSizeValidator}.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileSizeValidator.class })
public @interface FileSize {

    String message() default "exceeds max file size of {max}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();
}
