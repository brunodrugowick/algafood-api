package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.controller.utils.ObjectMerger;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.PermissionCrudService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    public PermissionController(PermissionRepository permissionRepository, PermissionCrudService permissionCrudService) {
        this.permissionRepository = permissionRepository;
        this.permissionCrudService = permissionCrudService;
    }

    @GetMapping
    public List<Permission> list() {
        return permissionRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Permission> save(@RequestBody Permission permission) {
        // Temporary. Client should not send an ID when posting. See #2.
        if (permission.getId() != null) {
            return ResponseEntity.badRequest()
                    .build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionCrudService.save(permission));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> get(@PathVariable Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);

        if (permissionOptional.isPresent()) {
            return ResponseEntity.ok(permissionOptional.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Permission> update(@PathVariable Long id, @RequestBody Permission permission) {
        Optional<Permission> permissionToUpdate = permissionRepository.findById(id);

        if (permissionToUpdate.isPresent()) {
            BeanUtils.copyProperties(permission, permissionToUpdate.get(), "id");
            Permission permissionUpdated = permissionCrudService.save(permissionToUpdate.get());
            return ResponseEntity.ok(permissionUpdated);
        }

        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Permission> partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> permission) {
        Optional<Permission> permissionToUpdate = permissionRepository.findById(id);

        if (permissionToUpdate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ObjectMerger.mergeRequestBodyToGenericObject(permission, permissionToUpdate.get(), Permission.class);

        return update(id, permissionToUpdate.get());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            permissionCrudService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityBeingUsedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
