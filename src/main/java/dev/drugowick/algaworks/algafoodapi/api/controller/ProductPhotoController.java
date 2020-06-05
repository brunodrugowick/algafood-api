package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.model.input.ProductPhotoInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class ProductPhotoController {

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                            ProductPhotoInput productPhotoInput) throws IOException {

        String filename = UUID.randomUUID() + "_" + restaurantId + "_" + productId;

        Path path = Path.of("/home/drugo/Pictures/algafood-files/", filename);

        System.out.println(path);
        System.out.println(productPhotoInput.getFile().getContentType());
        System.out.println(productPhotoInput.getDescription());

        productPhotoInput.getFile().transferTo(path);
    }
}
