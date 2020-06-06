package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.ProductPhotoModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.ProductPhotoInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProductCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProductPhotoCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class ProductPhotoController {

    private final ProductPhotoCatalogService productPhotoCatalogService;
    private final ProductCrudService productCrudService;
    private final GenericModelAssembler<ProductPhoto, ProductPhotoModel> modelAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductPhotoModel updatePhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                         @Valid ProductPhotoInput productPhotoInput) throws IOException {
        Product product = productCrudService.findOrElseThrow(restaurantId, productId);

        // There's no reason to use a disassembler here since this is not a typical input to model transformation.
        ProductPhoto productPhoto = new ProductPhoto();
        MultipartFile file = productPhotoInput.getFile();
        productPhoto.setContentType(file.getContentType());
        productPhoto.setDescription(productPhotoInput.getDescription());
        productPhoto.setFileName(file.getOriginalFilename());
        productPhoto.setProduct(product);
        productPhoto.setSize(file.getSize());

        ProductPhotoModel photoModel =
                modelAssembler.toModel(productPhotoCatalogService.save(productPhoto), ProductPhotoModel.class);

        return photoModel;
    }
}
