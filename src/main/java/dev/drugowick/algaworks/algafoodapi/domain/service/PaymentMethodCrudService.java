package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.PaymentMethodNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentMethodCrudService {

    public static final String MSG_PAYMENT_METHOD_CONFLICT = "Operation on Payment Method %d conflicts with another entity and can not be performed.";

    private PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodCrudService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Transactional
    public PaymentMethod save(PaymentMethod paymentMethod) {
        try {
            return paymentMethodRepository.save(paymentMethod);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PAYMENT_METHOD_CONFLICT, paymentMethod.getId()));
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
            // Flushing here guarantees the DB exceptions below can be caught.
            paymentMethodRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PAYMENT_METHOD_CONFLICT, id));
        } catch (EmptyResultDataAccessException e) {
            throw new PaymentMethodNotFoundException(id);
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
                .orElseThrow(() -> new PaymentMethodNotFoundException(id));
    }
}
