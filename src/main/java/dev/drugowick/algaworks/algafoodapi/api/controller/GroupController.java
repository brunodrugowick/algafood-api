package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.GroupModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.GroupInput;
import dev.drugowick.algaworks.algafoodapi.domain.exception.GenericBusinessException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Group;
import dev.drugowick.algaworks.algafoodapi.domain.repository.GroupRepository;
import dev.drugowick.algaworks.algafoodapi.domain.service.GroupCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/groups")
public class GroupController {

    private GroupRepository groupRepository;
    private GroupCrudService groupService;
    private GenericModelAssembler<Group, GroupModel> genericModelAssembler;
    private GenericInputDisassembler<GroupInput, Group> genericInputDisassembler;

    public GroupController(GroupRepository groupRepository, GroupCrudService groupService, GenericModelAssembler<Group, GroupModel> genericModelAssembler, GenericInputDisassembler<GroupInput, Group> genericInputDisassembler) {
        this.groupRepository = groupRepository;
        this.groupService = groupService;
        this.genericModelAssembler = genericModelAssembler;
        this.genericInputDisassembler = genericInputDisassembler;
    }

    @GetMapping
    public List<GroupModel> list() {
        return genericModelAssembler.toCollectionModel(groupRepository.findAll(), GroupModel.class);
    }

    @PostMapping
    public ResponseEntity<GroupModel> save(@RequestBody @Valid GroupInput groupInput) {
        Group group = genericInputDisassembler.toDomain(groupInput, Group.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(groupService.save(group), GroupModel.class));
    }

    @GetMapping("/{id}")
    public GroupModel get(@PathVariable Long id) {
        return genericModelAssembler.toModel(groupService.findOrElseThrow(id), GroupModel.class);
    }

    @PutMapping("/{id}")
    public GroupModel update(@PathVariable Long id,
                             @RequestBody @Valid GroupInput groupInput) {
        Group groupToUpdate = groupService.findOrElseThrow(id);
        genericInputDisassembler.copyToDomainObject(groupInput, groupToUpdate);
        return genericModelAssembler.toModel(groupService.update(id, groupToUpdate), GroupModel.class);
    }

    @PatchMapping("/{id}")
    public Group partialUpdate(@PathVariable Long id, @RequestBody Map<String, Object> groupMap) {
        throw new GenericBusinessException("This method is temporarily not allowed.");
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        groupService.delete(id);
    }

}
