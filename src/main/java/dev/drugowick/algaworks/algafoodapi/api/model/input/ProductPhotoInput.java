package dev.drugowick.algaworks.algafoodapi.api.model.input;

import dev.drugowick.algaworks.algafoodapi.domain.validation.FileContentType;
import dev.drugowick.algaworks.algafoodapi.domain.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class  ProductPhotoInput {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private MultipartFile file;

    @NotBlank
    private String description;
}
