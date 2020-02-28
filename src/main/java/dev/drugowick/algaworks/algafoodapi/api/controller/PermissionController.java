package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.PermissionCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ValidationService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    /**
     * I don't like it but, for the sake of simplicity for now, operations that do not require a
     * transaction are using the repository (get, list) and operations that require a transaction
     * are using the service layer (delete, save, update).
     */

    private PermissionRepository permissionRepository;
    private PermissionCrudService permissionCrudService;
    private ValidationService validationService;

    public PermissionController(PermissionRepository permissionRepository, PermissionCrudService permissionCrudService, ValidationService validationService) {
        this.permissionRepository = permissionRepository;
        this.permissionCrudService = permissionCrudService;
        this.validationService = validationService;
    }

    @GetMapping
    public List<Permission> list() {
        return permissionRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Permission> save(@RequestBody @Valid Permission permission) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (permission.getId() != null) {
            throw new GenericBusinessException("You should not send an ID when saving or updating an entity.");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionCrudService.save(permission));
    }

    @GetMapping("/{id}")
    public Permission get(@PathVariable Long id) {
        return permissionCrudService.findOrElseThrow(id);
    }

    @PutMapping("{id}")
    public Permission update(@PathVariable Long id, @RequestBody @Valid Permission permission) {
        Permission permissionToUpdate = permissionCrudService.findOrElseThrow(id);

        BeanUtils.copyProperties(permission, permissionToUpdate, "id");

        return permissionCrudService.save(permissionToUpdate);
    }

    @PatchMapping("{id}")
    public Permission partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> permission) {
        Permission permissionToUpdate = permissionCrudService.findOrElseThrow(id);

        ObjectMerger.mergeRequestBodyToGenericObject(permission, permissionToUpdate, Permission.class);
        validationService.validate(permissionToUpdate, "permission");

        return update(id, permissionToUpdate);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        permissionCrudService.delete(id);
    }
}
