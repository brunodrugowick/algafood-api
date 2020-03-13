package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.PermissionNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.PermissionRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PermissionCrudService {

    public static final String MSG_PERMISSION_CONFLICT = "Operation on Permission %d conflicts with another entity and can not be performed.";

    private PermissionRepository permissionRepository;

    public PermissionCrudService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Transactional
    public Permission save(Permission permission) {
        try {
            return permissionRepository.save(permission);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PERMISSION_CONFLICT, permission.getId()));
        }
    }

    @Transactional
    public void delete(Long id) {
        try {
            permissionRepository.deleteById(id);
            // Flushing here guarantees the DB exceptions below can be caught.
            permissionRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityBeingUsedException(
                    String.format(MSG_PERMISSION_CONFLICT, id));
        } catch (EmptyResultDataAccessException e) {
            throw new PermissionNotFoundException(id);
        }
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public Permission findOrElseThrow(Long id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new PermissionNotFoundException(id));
    }
}
