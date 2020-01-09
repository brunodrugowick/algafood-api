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

    public static final String MSG_NO_PAYMENT_METHOD = "There's no Payment Method with the id %d.";
    public static final String MSG_PAYMENT_METHOD_CONFLICT = "Operation on Payment Method %d conflicts with another entity and can not be performed.";

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodCrudService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod save(PaymentMethod paymentMethod) {
        try {
            return paymentMethodRepository.save(paymentMethod);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PAYMENT_METHOD_CONFLICT, paymentMethod.getId()));
        }
    }

    public void delete(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PAYMENT_METHOD_CONFLICT, id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format(MSG_NO_PAYMENT_METHOD, id));
        }
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public PaymentMethod findOrElseThrow(Long id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(MSG_NO_PAYMENT_METHOD, id)
                ));
    }
}
