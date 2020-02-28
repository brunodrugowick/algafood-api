package dev.drugowick.algaworks.algafoodapi.domain.validation;

import org.springframework.beans.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

public class IfFreeDeliverySubtotalEqualsTotalValidator implements ConstraintValidator<IfFreeDeliverySubtotalEqualsTotal, Object> {

    private String fieldDeliveryFee;
    private String fieldSubtotal;
    private String fieldTotal;

    @Override
    public void initialize(IfFreeDeliverySubtotalEqualsTotal constraint) {
        this.fieldDeliveryFee = constraint.fieldDeliveryFee();
        this.fieldSubtotal = constraint.fieldSubtotal();
        this.fieldTotal = constraint.fieldTotal();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean isValid = true;

        try {
            BigDecimal deliveryFee = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), this.fieldDeliveryFee)
                    .getReadMethod().invoke(object);
            BigDecimal subtotal = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), this.fieldSubtotal)
                    .getReadMethod().invoke(object);
            BigDecimal total = (BigDecimal) BeanUtils.getPropertyDescriptor(object.getClass(), this.fieldTotal)
                    .getReadMethod().invoke(object);

            if (deliveryFee != null
                    && BigDecimal.ZERO.compareTo(deliveryFee) == 0
                    && !subtotal.equals(total)) {
                isValid = false;
            }

        } catch (Exception e) {
            throw new ValidationException(e);
        }

        return isValid;
    }
}
