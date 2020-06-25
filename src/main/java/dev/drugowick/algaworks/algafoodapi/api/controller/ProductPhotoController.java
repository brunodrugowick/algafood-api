package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.api.assembler.GenericModelAssembler;
import dev.drugowick.algaworks.algafoodapi.api.model.ProductPhotoModel;
import dev.drugowick.algaworks.algafoodapi.api.model.input.ProductPhotoInput;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.service.PhotoStorageService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProductCrudService;
import dev.drugowick.algaworks.algafoodapi.domain.service.ProductPhotoCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurants/{restaurantId}/products/{productId}/photo")
public class ProductPhotoController {

    private final ProductPhotoCatalogService productPhotoCatalogService;
    private final ProductCrudService productCrudService;
    private final GenericModelAssembler<ProductPhoto, ProductPhotoModel> modelAssembler;
    private final PhotoStorageService photoStorageService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductPhotoModel getPhotoJson(@PathVariable Long restaurantId, @PathVariable Long productId) {
        ProductPhoto productPhoto = productPhotoCatalogService.findOrElseThrow(restaurantId, productId);

        return modelAssembler.toModel(productPhoto, ProductPhotoModel.class);
    }

    @GetMapping
    public ResponseEntity<?> getPhoto(@PathVariable Long restaurantId, @PathVariable Long productId,
                                                        @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        ProductPhoto productPhoto = productPhotoCatalogService.findOrElseThrow(restaurantId, productId);

        MediaType photoMediaType = MediaType.parseMediaType(productPhoto.getContentType());
        List<MediaType> clientAcceptedMediaTypes = MediaType.parseMediaTypes(acceptHeader);

        checkMediaType(photoMediaType, clientAcceptedMediaTypes);

        PhotoStorageService.PhotoWrapper photoWrapper = photoStorageService.get(productPhoto.getFileName());
        if (photoWrapper.hasUrl()) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, photoWrapper.getUrl())
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(photoMediaType)
                    .body(new InputStreamResource(photoWrapper.getInputStream()));
        }

    }

    /**
     * This method makes sure that the photo being returned if of the type that the client accepts.
     *
     * @param photoMediaType
     * @param clientAcceptedMediaTypes
     * @throws HttpMediaTypeNotAcceptableException
     */
    private void checkMediaType(MediaType photoMediaType, List<MediaType> clientAcceptedMediaTypes)
            throws HttpMediaTypeNotAcceptableException {
        boolean compatible = clientAcceptedMediaTypes.stream()
                .anyMatch(mediaType -> mediaType.isCompatibleWith(photoMediaType));
        if (!compatible) {
            throw new HttpMediaTypeNotAcceptableException(clientAcceptedMediaTypes);
        }
    }

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
        productPhoto.setProductId(product.getId());
        productPhoto.setProduct(product);
        productPhoto.setSize(file.getSize());

        ProductPhotoModel photoModel =
                modelAssembler.toModel(productPhotoCatalogService.save(productPhoto, file.getInputStream()), ProductPhotoModel.class);

        return photoModel;
    }

    @DeleteMapping
    public void deletePhoto(@PathVariable Long restaurantId, @PathVariable Long productId) {
        productCrudService.delete(restaurantId, productId);
    }
}
