package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.RestaurantModel;
import dev.drugowick.algaworks.algafoodapi.api.model.dtoPattern.RestaurantDTO;
import dev.drugowick.algaworks.algafoodapi.api.model.input.RestaurantInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

@Api(tags = "Restaurants")
public interface RestaurantControllerOpenApi {

    @ApiOperation("List of restaurants")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    public List<RestaurantDTO.Response.Default> list();

    @ApiOperation("List of restaurants (Jackson view)")
    public MappingJacksonValue listJacksonView(
            @ApiParam(value = "projection", example = "'all-fields' or 'name-only'", type = "query") String projection);

    @ApiOperation("List of restaurants (DTO pattern)")
    public List<?> listDtoPattern(String projection);

    @ApiOperation("Creates a new restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurant created")
    })
    public ResponseEntity<?> save(RestaurantInput restaurantInput);

    @ApiOperation("Creates a new restaurant")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Restaurant created")
    })
    public ResponseEntity<?> saveDtoPattern(RestaurantDTO.Request.Default restaurantInput);

    @ApiOperation("Retrieves a restaurant by ID")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Property names to filter", name = "fields", type = "string", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid restaurant ID", response = ApiError.class),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    public RestaurantModel get(Long id);

    @ApiOperation("Updates a restaurant by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Restaurant updated"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    public ResponseEntity<?> update(
            Long id,
            RestaurantInput restaurantInput);

    @ApiOperation("Removes a restaurant by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Restaurant removed"),
            @ApiResponse(code = 404, message = "Restaurant not found", response = ApiError.class)
    })
    public void delete(Long id);

    @ApiOperation("Activates a restaurant")
    public void activate(Long restaurantId);

    @ApiOperation("Activates a list of restaurants")
    public void activateList(List<Long> restaurantIds);

    @ApiOperation("Deactivates a list of restaurants")
    public void deactivateList(List<Long> restaurantIds);

    @ApiOperation("Deactivates a restaurant")
    public void deactivate(Long restaurantId);

    @ApiOperation("Openss a restaurant")
    public void open(Long restaurantId);

    @ApiOperation("Closes a restaurant")
    public void close(Long restaurantId);
}
