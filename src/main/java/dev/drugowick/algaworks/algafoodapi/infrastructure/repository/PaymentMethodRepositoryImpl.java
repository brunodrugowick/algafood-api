package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;

@Component
public class PaymentMethodRepositoryImpl implements PaymentMethodRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Transactional
	@Override
	public PaymentMethod save(PaymentMethod paymentMethod) {
		return entityManager.merge(paymentMethod);
	}

	@Override
	public PaymentMethod get(Long id) {
		return entityManager.find(PaymentMethod.class, id);
	}

	@Override
	public List<PaymentMethod> list() {
		return entityManager.createQuery("from PaymentMethod", PaymentMethod.class)
				.getResultList();
	}

	@Transactional
	@Override
	public void remove(PaymentMethod paymentMethod) {
		// Finds the entity so Hibernate has it Managed (not Detached).
		paymentMethod = get(paymentMethod.getId());
		entityManager.remove(paymentMethod);

	}

}
