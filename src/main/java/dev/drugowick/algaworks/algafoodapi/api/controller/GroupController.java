package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.controller.openapi.GroupControllerOpenApi;
import dev.drugowick.algaworks.algafoodapi.api.model.GroupModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PermissionModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.GroupInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Group;
import dev.drugowick.algaworks.algafoodapi.domain.model.Permission;
import dev.drugowick.algaworks.algafoodapi.domain.repository.GroupRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.GroupCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.PermissionCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController implements GroupControllerOpenApi {

    private GroupRepository groupRepository;
    private GroupCrudService groupService;
    private PermissionCrudService permissionCrudService;
    private GenericModelAssembler<Group, GroupModel> genericModelAssembler;
    private GenericModelAssembler<Permission, PermissionModel> permissionModelAssembler;
    private GenericInputDisassembler<GroupInput, Group> genericInputDisassembler;

    public GroupController(GroupRepository groupRepository,
                           GroupCrudService groupService,
                           PermissionCrudService permissionCrudService, GenericModelAssembler<Group, GroupModel> genericModelAssembler,
                           GenericModelAssembler<Permission, PermissionModel> permissionModelAssembler,
                           GenericInputDisassembler<GroupInput, Group> genericInputDisassembler) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.permissionCrudService = permissionCrudService;
        this.genericModelAssembler = genericModelAssembler;
        this.permissionModelAssembler = permissionModelAssembler;
        this.genericInputDisassembler = genericInputDisassembler;
    }

    @Override
    @GetMapping
    public List<GroupModel> list() {
        return genericModelAssembler.toCollectionModel(groupRepository.findAll(), GroupModel.class);
    }

    @Override
    @PostMapping
    public ResponseEntity<GroupModel> save(@RequestBody @Valid GroupInput groupInput) {
        Group group = genericInputDisassembler.toDomain(groupInput, Group.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(groupService.save(group), GroupModel.class));
    }

    @Override
    @GetMapping("/{id}")
    public GroupModel get(@PathVariable Long id) {
        return genericModelAssembler.toModel(groupService.findOrElseThrow(id), GroupModel.class);
    }

    @Override
    @PutMapping("/{id}")
    public GroupModel update(@PathVariable Long id,
                             @RequestBody @Valid GroupInput groupInput) {
        Group groupToUpdate = groupService.findOrElseThrow(id);
        genericInputDisassembler.copyToDomainObject(groupInput, groupToUpdate);
        return genericModelAssembler.toModel(groupService.update(id, groupToUpdate), GroupModel.class);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

    @Override
    @GetMapping("/{groupId}/permissions")
    public List<PermissionModel> listPermissions(@PathVariable Long groupId) {
        Group group = groupService.findOrElseThrow(groupId);

        return permissionModelAssembler.toCollectionModel(group.getPermissions(), PermissionModel.class);
    }

    @Override
    @PutMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void bindPermission(@PathVariable Long groupId,
                               @PathVariable Long permissionId) {
        groupService.bindPermission(groupId, permissionId);
    }

    @Override
    @DeleteMapping("/{groupId}/permissions/{permissionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unbindPermission(@PathVariable Long groupId,
                                 @PathVariable Long permissionId) {
        groupService.unbindPermission(groupId, permissionId);
    }
}
