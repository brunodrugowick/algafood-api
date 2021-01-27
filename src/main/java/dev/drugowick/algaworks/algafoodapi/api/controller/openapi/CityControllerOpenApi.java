package dev.drugowick.algaworks.algafoodapi.api.controller.openapi;

import dev.drugowick.algaworks.algafoodapi.api.exceptionhandler.ApiError;
import dev.drugowick.algaworks.algafoodapi.api.model.CityModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.CityInput;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cities")
public interface CityControllerOpenApi {

    @ApiOperation("Retrieves the list of cities")
    public List<CityModel> list();

    @ApiOperation("Retrieves a city by ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Not a valid request", response = ApiError.class)
    })
    public CityModel get(@ApiParam(value = "City ID", example = "1") Long id);

    @ApiOperation("Creates a new city")
    @ApiResponses({
            @ApiResponse(code = 201, message = "City created")
    })
    public ResponseEntity<CityModel> save(@ApiParam(value = "A representation of a new city") CityInput cityInput);

    @ApiOperation("Updates a city by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "City updated"),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    public ResponseEntity<CityModel> update(@ApiParam(value = "City ID", example = "1") Long id,
                                            @ApiParam(value = "A representation with new data of a city") CityInput cityInput);

    @ApiOperation("Removes a city by ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "City removed"),
            @ApiResponse(code = 404, message = "City not found", response = ApiError.class)
    })
    public void delete(@ApiParam(value = "City ID", example = "1") Long id);
}
