package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.PaymentMethodCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    public PaymentMethod get(@PathVariable Long id) {
        return paymentMethodCrudService.findOrElseThrow(id);
    }

    @PutMapping("{id}")
    public PaymentMethod update(@PathVariable Long id, @RequestBody PaymentMethod paymentMethod) {
        PaymentMethod paymentMethodToUpdate = paymentMethodCrudService.findOrElseThrow(id);

        BeanUtils.copyProperties(paymentMethod, paymentMethodToUpdate, "id");

        return paymentMethodCrudService.save(paymentMethodToUpdate);
    }

    @PatchMapping("{id}")
    public PaymentMethod partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> paymentMethod) {
        PaymentMethod paymentMethodToUpdate = paymentMethodCrudService.findOrElseThrow(id);

        ObjectMerger.mergeRequestBodyToGenericObject(paymentMethod, paymentMethodToUpdate, PaymentMethod.class);

        return update(id, paymentMethodToUpdate);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        paymentMethodCrudService.delete(id);
    }
}
