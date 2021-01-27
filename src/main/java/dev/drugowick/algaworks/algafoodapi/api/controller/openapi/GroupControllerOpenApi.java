package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.GroupModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PermissionModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.GroupInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Groups")
public interface GroupControllerOpenApi {

    @ApiOperation("Retrieves the list of groups")
    List<GroupModel> list();

    @ApiOperation("Creates a new group")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Group created")
    })
    ResponseEntity<GroupModel> save(GroupInput groupInput);

    @ApiOperation("Retrieves a group by ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Not a valid request", response = ApiError.class)
    })
    GroupModel get(Long id);

    @ApiOperation("Updates a group by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Group updated"),
            @ApiResponse(code = 404, message = "Group not found", response = ApiError.class)
    })
    GroupModel update(Long id, GroupInput groupInput);

    @ApiOperation("Removes a group by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Group removed"),
            @ApiResponse(code = 404, message = "Group not found", response = ApiError.class)
    })
    void delete(Long id);

    @ApiOperation("Retrieves the list of permissions")
    List<PermissionModel> listPermissions(Long groupId);

    @ApiOperation("Binds a group with a permission")
    void bindPermission(Long groupId, Long permissionId);

    @ApiOperation("Unbinds a group and a permission")
    void unbindPermission(Long groupId, Long permissionId);
}
