package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.CuisineModel;
import dev.drugowick.algaworks.algafoodapi.api.model.PageModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CuisineInput;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cuisines")
public interface CuisineControllerOpenApi {

    @ApiOperation("Paginated list of cuisines")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    public PageModel<CuisineModel> list(Pageable pageable);

    @ApiOperation("Creates a new cuisine")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cuisine created")
    })
    public ResponseEntity<CuisineModel> save(
            @ApiParam(name = "body", value = "Representation of a cuisine to be saved") CuisineInput cuisineInput);

    @ApiOperation("Retrieves a cuisine by ID")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid cuisine ID", response = ApiError.class),
            @ApiResponse(code = 404, message = "Cuisine not found", response = ApiError.class)
    })
    public CuisineModel get(
            @ApiParam(value = "Cuisine ID", example = "1") Long id);

    @ApiOperation("Updates a cuisine by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cuisine updated"),
            @ApiResponse(code = 404, message = "Cuisine not found", response = ApiError.class)
    })
    public CuisineModel update(
            @ApiParam(value = "Cuisine ID", example = "1") Long id,
            @ApiParam(name = "body", value = "Representation of a cuisine to be updated") CuisineInput cuisineInput);

    @ApiOperation("Removes a cuisine by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cuisine removed"),
            @ApiResponse(code = 404, message = "Cuisine not found", response = ApiError.class)
    })
    public void delete(
            @ApiParam(value = "Cuisine ID", example = "1") Long id);

    @ApiOperation("List of cuisines with a name like the parameter passed")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    public List<CuisineModel> cuisinesByName(
            @ApiParam(value = "Cuisine name or part of it", example = "Brazil") String name);
}
