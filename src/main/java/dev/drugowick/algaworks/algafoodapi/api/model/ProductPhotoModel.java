package dev.drugowick.algaworks.algafoodapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductPhotoModel {

    private String fileName;
    private String description;
    private String contentType;
    private Long size;
}
