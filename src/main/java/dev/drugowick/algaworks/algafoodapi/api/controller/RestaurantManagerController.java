package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.UserModel;
import dev.drugowick.algaworks.algafoodapi.domain.model.User;
import dev.drugowick.algaworks.algafoodapi.domain.service.RestaurantCrudService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Restaurants")
@RestController
@RequestMapping("restaurants/{restaurantId}/managers")
public class RestaurantManagerController {

    private final RestaurantCrudService restaurantCrudService;
    private final GenericModelAssembler<User, UserModel> userModelAssembler;

    public RestaurantManagerController(RestaurantCrudService restaurantCrudService, GenericModelAssembler<User, UserModel> userModelAssembler) {
        this.restaurantCrudService = restaurantCrudService;
        this.userModelAssembler = userModelAssembler;
    }

    @ApiOperation("List of managers of a restaurant")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    @GetMapping
    public List<UserModel> getManagers(
            @ApiParam(value = "Restaurant ID", example = "1", required = true)
            @PathVariable Long restaurantId) {
        return userModelAssembler.toCollectionModel(
                restaurantCrudService.findOrElseThrow(restaurantId).getManagers(),
                UserModel.class);
    }

    @ApiOperation("Removes manager (user) from a restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully removed manager (user)"),
            @ApiResponse(code = 404, message = "Restaurant or manager (user) not found",
                    response = ApiError.class)
    })
    @DeleteMapping("/{managerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteManager(
            @ApiParam(value = "Restaurant ID", example = "1", required = true)
            @PathVariable Long restaurantId,
            @ApiParam(value = "User ID", example = "1", required = true)
            @PathVariable Long managerId) {
        restaurantCrudService.unbindManager(restaurantId, managerId);
    }

    @ApiOperation("Adds manager (user) to a restaurant")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Successfully added a manager (user)"),
            @ApiResponse(code = 404, message = "Restaurant or manager (user) not found",
                    response = ApiError.class)
    })
    @PutMapping("/{managerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addManager(
            @ApiParam(value = "Restaurant ID", example = "1", required = true)
            @PathVariable Long restaurantId,
            @ApiParam(value = "User ID", example = "1", required = true)
            @PathVariable Long managerId) {
        restaurantCrudService.bindManager(restaurantId, managerId);
    }
}
