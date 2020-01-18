package dev.drugowick.algaworks.algafoodapi.api.exceptionhandler;

import lombok.Getter;

/**
 * This ENUM helps to determine the title and type properties when returning an {@link ApiError}.
 * Those values are predetermined here for the ENUM values.
 * <p>
 * This class also defines the URI prefix for the type property of {@link ApiError}.
 */
@Getter
public enum ApiErrorType {

    ENTITY_NOT_FOUND("/entity-not-found", "Entity Not Found"),
    ENTITY_BEING_USED("/entity-being-used", "Entity Being Used"),
    BUSINESS_EXCEPTION("/business-exception", "Business Exception");

    private String title;
    private String uri;

    ApiErrorType(String path, String title) {
        this.uri = "https://drugo.dev/algafoodapi" + path;
        this.title = title;
    }
}
