package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class PermissionCrudService {

    private PermissionRepository permissionRepository;

    public PermissionCrudService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public Permission save(Permission permission) {
        return permissionRepository.save(permission);
    }

    public void delete(Long id) {
        try {
            permissionRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format("Permission %d is being used by another entity and can not be removed.", id));
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(
                    String.format("There's no Permission with the id %d.", id));
        }
    }
}
