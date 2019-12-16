package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PaymentMethodCrudService {

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodCrudService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod save(PaymentMethod paymentMethod) {
        return paymentMethodRepository.save(paymentMethod);
    }

    public void delete(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format("Payment Method %d is being used by another entity and can not be removed.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There's no Payment Method with the id %d.", id));
        }
    }
}
