package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
public class ApiError {

    @ApiModelProperty(example = "404")
    private Integer status;

    @ApiModelProperty(example = "https://drugo.dev/algafoodapi/resource-not-found")
    private String type;

    @ApiModelProperty(example = "Resource Not Found")
    private String title;

    @ApiModelProperty(example = "There's no City with the id -1.")
    private String detail;

    @ApiModelProperty(example = "There's no City with the id -1.")
    private String userMessage;

    @ApiModelProperty(example = "2020-07-11T03:44:18.949262151Z")
    private OffsetDateTime timestamp;

    @ApiModelProperty("Fields or objects that caused the validation error.")
    private List<Object> errorObjects;

    @ApiModel("ProblemObject")
    @Getter
    @Builder
    public static class Object {

        @ApiModelProperty(example = "price")
        private String name;

        @ApiModelProperty(example = "is mandatory")
        private String userMessage;
    }

}
