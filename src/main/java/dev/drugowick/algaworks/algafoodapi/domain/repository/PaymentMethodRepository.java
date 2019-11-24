package dev.drugowick.algaworks.algafoodapi.domain.repository;

import java.util.List;

import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;

public interface PaymentMethodRepository {
	
	PaymentMethod save(PaymentMethod paymentMethod);
	PaymentMethod get(Long id);
	List<PaymentMethod> list();
	void remove(PaymentMethod paymentMethod);

}
