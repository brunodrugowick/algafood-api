package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericInputDisassembler;
import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.UserModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.PasswordChangeInput;
import dev.drugowick.algaworks.algafoodapi.api.model.input.UserInput;
import dev.drugowick.algaworks.algafoodapi.api.model.input.UserNoPasswordInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.repository.UserRespository;
import dev.drugowick.algaworks.algafoodapi.domain.service.UserCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final GenericModelAssembler<User, UserModel> genericModelAssembler;
    private final GenericInputDisassembler<UserInput, User> genericInputDisassembler;
    private final GenericInputDisassembler<UserNoPasswordInput, User> genericNoPasswordInputDisassembler;

    private final UserRespository userRespository;
    private final UserCrudService userService;

    public UserController(GenericModelAssembler<User, UserModel> genericModelAssembler, GenericInputDisassembler<UserInput, User> genericInputDisassembler, GenericInputDisassembler<UserNoPasswordInput, User> genericNoPasswordInputDisassembler, UserRespository userRespository, UserCrudService userService) {
        this.genericModelAssembler = genericModelAssembler;
        this.genericInputDisassembler = genericInputDisassembler;
        this.genericNoPasswordInputDisassembler = genericNoPasswordInputDisassembler;
        this.userRespository = userRespository;
        this.userService = userService;
    }

    @GetMapping
    public List<UserModel> list() {
        return genericModelAssembler.toCollectionModel(userRespository.findAll(), UserModel.class);
    }

    @GetMapping("/{id}")
    public UserModel get(@PathVariable Long id) {
        return genericModelAssembler.toModel(userService.findOrElseThrow(id), UserModel.class);
    }

    @PostMapping
    public ResponseEntity<UserModel> save(@RequestBody @Valid UserInput userInput) {
        User user = genericInputDisassembler.toDomain(userInput, User.class);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(genericModelAssembler.toModel(userService.save(user), UserModel.class));
    }

    @PutMapping("/{id}")
    public UserModel update(@PathVariable Long id,
                            @RequestBody @Valid UserNoPasswordInput userInput) {
        User userToUpdate = userService.findOrElseThrow(id);
        genericNoPasswordInputDisassembler.copyToDomainObject(userInput, userToUpdate);
        return genericModelAssembler.toModel(userService.update(id, userToUpdate), UserModel.class);
    }

    @PutMapping("/{id}/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id,
                                        @RequestBody @Valid PasswordChangeInput passwordChangeInput) {
        userService.updatePassword(id,
                passwordChangeInput.getNewPassword(),
                passwordChangeInput.getPassword());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
