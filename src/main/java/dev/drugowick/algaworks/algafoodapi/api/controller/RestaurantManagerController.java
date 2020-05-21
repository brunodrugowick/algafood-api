package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.UserModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("restaurants/{restaurantId}/managers")
public class RestaurantManagerController {

    private final RestaurantCrudService restaurantCrudService;
    private final GenericModelAssembler<User, UserModel> userModelAssembler;

    public RestaurantManagerController(RestaurantCrudService restaurantCrudService, GenericModelAssembler<User, UserModel> userModelAssembler) {
        this.restaurantCrudService = restaurantCrudService;
        this.userModelAssembler = userModelAssembler;
    }

    @GetMapping
    public List<UserModel> getManagers(@PathVariable Long restaurantId) {
        return userModelAssembler.toCollectionModel(
                restaurantCrudService.findOrElseThrow(restaurantId).getManagers(),
                UserModel.class);
    }

    @DeleteMapping("/{managerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteManager(@PathVariable Long restaurantId, @PathVariable Long managerId) {
        restaurantCrudService.unbindManager(restaurantId, managerId);
    }

    @PutMapping("/{managerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addManager(@PathVariable Long restaurantId, @PathVariable Long managerId) {
        restaurantCrudService.bindManager(restaurantId, managerId);
    }
}
