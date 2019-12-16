package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.PaymentMethodCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/payment-methods")
public class PaymentMethodController {

    /**
     * I don't like it but, for the sake of simplicity for now, operations that do not require a
     * transaction are using the repository (get, list) and operations that require a transaction
     * are using the service layer (delete, save, update).
     */

    private PaymentMethodRepository paymentMethodRepository;
    private PaymentMethodCrudService paymentMethodCrudService;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository, PaymentMethodCrudService paymentMethodCrudService) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodCrudService = paymentMethodCrudService;
    }

    @GetMapping
    public List<PaymentMethod> list() {
        return paymentMethodRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<PaymentMethod> save(@RequestBody PaymentMethod paymentMethod) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (paymentMethod.getId() != null) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodCrudService.save(paymentMethod));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethod> get(@PathVariable Long id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);

        if (paymentMethodOptional.isPresent()) {
            return ResponseEntity.ok(paymentMethodOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<PaymentMethod> update(@PathVariable Long id, @RequestBody PaymentMethod paymentMethod) {
        Optional<PaymentMethod> paymentMethodToUpdate = paymentMethodRepository.findById(id);

        if (paymentMethodToUpdate.isPresent()) {
            BeanUtils.copyProperties(paymentMethod, paymentMethodToUpdate.get(), "id");
            PaymentMethod paymentMethodUpdated = paymentMethodCrudService.save(paymentMethodToUpdate.get());
            return ResponseEntity.ok(paymentMethodUpdated);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<PaymentMethod> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> paymentMethod) {
        Optional<PaymentMethod> paymentMethodToUpdate = paymentMethodRepository.findById(id);

        if (paymentMethodToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ObjectMerger.mergeRequestBodyToGenericObject(paymentMethod, paymentMethodToUpdate.get(), PaymentMethod.class);

        return update(id, paymentMethodToUpdate.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            paymentMethodCrudService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityBeingUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
