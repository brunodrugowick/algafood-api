package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An example of an annotation for DeliveryFee. This is a dummy example (since it's the same as using @PositiveOrZero
 * plus @NotNull) just to show how to create your validation annotation.
 *
 * Well, it does work to have a cleaner code (one annotation instead of two).
 *
 * Have this in mind, though: https://github.com/spring-projects/spring-framework/issues/20519. What happens here is
 * that if I have a key `PositiveOrZero` or `NotNull` on my `message.properties` file Spring uses their messages in case
 * of a `@DeliveryFee` violation.
 *
 * This happens because of the issue above. This won't happen in my case because I only have the
 * `deliveryFee.notNullAndPositiveOrZero` key on my `message.properties` since I decided not to use custom messsages for
 * existing validations. Wise decision, I believe.
 *
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { })
@NotNull
@PositiveOrZero
public @interface DeliveryFee {

    @OverridesAttribute(constraint = PositiveOrZero.class, name = "message")
    @OverridesAttribute(constraint = NotNull.class, name = "message")
    String message() default "{deliveryFee.notNullAndPositiveOrZero}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
