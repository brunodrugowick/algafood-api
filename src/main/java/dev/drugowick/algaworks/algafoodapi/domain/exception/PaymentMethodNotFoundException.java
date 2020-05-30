package dev.drugowick.algaworks.algafoodapi.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PaymentMethodNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public PaymentMethodNotFoundException(String message) {
		super(message);
	}

	public PaymentMethodNotFoundException(Long id) {
		this(String.format("There's no Payment Method with the id %d", id));
	}

	public PaymentMethodNotFoundException(Long restaurantId, Long paymentMethodId) {
		this(String.format("There's no Payment Method with the id %d for Restaurant %d", paymentMethodId, restaurantId));
	}
}
