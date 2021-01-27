package dev.drugowick.algaworks.algafoodapi.domain.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Setter
@Getter
public class OrderFilter {

    @ApiModelProperty(example = "1", value = "Client ID for order filtering")
    private Long clientId;

    @ApiModelProperty(example = "1", value = "Restaurant ID for order filtering")
    private Long restaurantId;

    @ApiModelProperty(example = "2021-10-30T00:00:00Z", value = "Start of creation date for order filtering")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createDateStart;

    @ApiModelProperty(example = "2021-10-30T00:00:00Z", value = "End of creation date for order filtering")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime createDateEnd;
}
