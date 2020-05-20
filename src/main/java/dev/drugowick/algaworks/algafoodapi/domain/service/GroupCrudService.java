package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityBeingUsedException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GroupNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Group;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.GroupRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupCrudService {

    public static final String MSG_GROUP_CONFLICT = "Operation on Group %d conflicts with another entity and can not be performed.";

    private GroupRepository groupRepository;
    private PermissionCrudService permissionCrudService;

    public GroupCrudService(GroupRepository groupRepository, PermissionCrudService permissionCrudService) {
        this.groupRepository = groupRepository;
        this.permissionCrudService = permissionCrudService;
    }

    @Transactional
    public Group save(Group group) {
        try {
            return groupRepository.save(group);
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_GROUP_CONFLICT, group.getId()));
        }
    }

    @Transactional
    public Group update(Long id, Group group) {
        group.setId(id);
        return groupRepository.save(group);
    }

    @Transactional
    public void delete(Long id) {
        try {
            groupRepository.deleteById(id);
            // Flushing here guarantees the DB exceptions below can be caught.
            groupRepository.flush();
        } catch (DataIntegrityViolationException exception) {
            throw new EntityBeingUsedException(
                    String.format(MSG_GROUP_CONFLICT, id));
        } catch (EmptyResultDataAccessException exception) {
            throw new GroupNotFoundException(id);
        }
    }

    @Transactional
    public void bindPermission(Long groupId, Long permissionId) {
        Group group = findOrElseThrow(groupId);
        Permission permission = permissionCrudService.findOrElseThrow(permissionId);

        group.addPermission(permission);
    }

    @Transactional
    public void unbindPermission(Long groupId, Long permissionId) {
        Group group = findOrElseThrow(groupId);
        Permission permission = permissionCrudService.findOrElseThrow(permissionId);

        group.removePermission(permission);
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param id of the entity to find.
     * @return the entity from the repository.
     */
    public Group findOrElseThrow(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new GroupNotFoundException(id));
    }
}
