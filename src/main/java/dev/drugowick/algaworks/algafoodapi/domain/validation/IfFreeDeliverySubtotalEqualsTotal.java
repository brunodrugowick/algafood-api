package dev.drugowick.algaworks.algafoodapi.domain.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = { IfFreeDeliverySubtotalEqualsTotalValidator.class })
public @interface IfFreeDeliverySubtotalEqualsTotal {

    String message() default "subtotal and total must be the same when delivery is free";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String fieldDeliveryFee();

    String fieldSubtotal();

    String fieldTotal();
}
