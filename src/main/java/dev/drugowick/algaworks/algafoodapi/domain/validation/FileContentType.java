package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * This is a custom annotation implemented by {@link FileContentTypeValidator}.
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileContentTypeValidator.class })
public @interface FileContentType {

    String message() default "content type must be one of the following: {allowed}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] allowed();
}
