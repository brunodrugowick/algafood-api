package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.controller.openapi.PaymentMethodControllerOpenApi;
import dev.drugowick.algaworks.algafoodapi.api.model.PaymentMethodModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.PaymentMethodInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.PaymentMethod;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PaymentMethodRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.PaymentMethodCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(path = "/payment-methods", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

    /**
     * I don't like it but, for the sake of simplicity for now, operations that do not require a
     * transaction are using the repository (get, list) and operations that require a transaction
     * are using the service layer (delete, save, update).
     */

    private PaymentMethodRepository paymentMethodRepository;
    private PaymentMethodCrudService paymentMethodCrudService;
    private ValidationService validationService;
    private GenericModelAssembler<PaymentMethod, PaymentMethodModel> genericModelAssembler;
    private GenericInputDisassembler<PaymentMethodInput, PaymentMethod> genericInputDisassembler;

    public PaymentMethodController(PaymentMethodRepository paymentMethodRepository, PaymentMethodCrudService paymentMethodCrudService, ValidationService validationService, GenericModelAssembler<PaymentMethod, PaymentMethodModel> genericModelAssembler, GenericInputDisassembler<PaymentMethodInput, PaymentMethod> genericInputDisassembler) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodCrudService = paymentMethodCrudService;
        this.validationService = validationService;
        this.genericModelAssembler = genericModelAssembler;
        this.genericInputDisassembler = genericInputDisassembler;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodModel>> list() {
        List<PaymentMethodModel> paymentMethodModels = genericModelAssembler.toCollectionModel(paymentMethodRepository.findAll(), PaymentMethodModel.class);

        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS))
                .body(paymentMethodModels);
    }

    @PostMapping
    public ResponseEntity<PaymentMethodModel> save(@RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethod = genericInputDisassembler.toDomain(paymentMethodInput, PaymentMethod.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(paymentMethodCrudService.save(paymentMethod), PaymentMethodModel.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentMethodModel> get(@PathVariable Long id, ServletWebRequest request, Principal principal) {

        // If for whatever reason you want to use this here
        System.out.println(request + "\n\n" + principal);

        PaymentMethodModel paymentMethodModel = genericModelAssembler.toModel(
                paymentMethodCrudService.findOrElseThrow(id), PaymentMethodModel.class);

        return ResponseEntity.ok()
                //.cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePrivate()) // Instructs only local caching (for sensitive data)
                .cacheControl(CacheControl.maxAge(10, TimeUnit.SECONDS).cachePublic()) // instructs shared caches that this can be shared
                //.cacheControl(CacheControl.noCache()) // Even cached, every request is validated (as if is always staled, without maxAge)
                //.cacheControl(CacheControl.noStore()) // Instructs the response to NOT be cached.
                .body(paymentMethodModel);
    }

    @PutMapping("{id}")
    public PaymentMethodModel update(@PathVariable Long id, @RequestBody @Valid PaymentMethodInput paymentMethodInput) {
        PaymentMethod paymentMethodToUpdate = paymentMethodCrudService.findOrElseThrow(id);
        genericInputDisassembler.copyToDomainObject(paymentMethodInput, paymentMethodToUpdate);
        return genericModelAssembler.toModel(paymentMethodCrudService.save(paymentMethodToUpdate), PaymentMethodModel.class);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        paymentMethodCrudService.delete(id);
    }
}
