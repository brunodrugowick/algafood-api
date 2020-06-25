package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.ProductPhotoNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPhotoCatalogService {

    private final ProductRepository productRepository;
    private final PhotoStorageService photoStorageService;

    @Transactional
    public ProductPhoto save(ProductPhoto photo, InputStream inputStream) {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();
        photo.setFileName(getFileName(photo));
        Optional<ProductPhoto> existingPhotoOptional = productRepository.findPhotoById(restaurantId, productId);
        String existingPhotoFilename = null;

        if (existingPhotoOptional.isPresent()) {
            existingPhotoFilename = existingPhotoOptional.get().getFileName();
            productRepository.delete(existingPhotoOptional.get());
        }

        // Making sure that the repository saves the photo to the database (flush guarantees that)
        // AGAIN, so I remember this in the future: FLUSH commits the transaction! So if something fails,
        // it fails before storing the file.
        photo = productRepository.save(photo);
        productRepository.flush();

        // Now that the photo is saved to the database (repository is flushed), we can actually save the photo file
        // to whatever place the storage service is going to save.
        PhotoStorageService.NewPhoto newPhoto = PhotoStorageService.NewPhoto.builder()
                .fileName(photo.getFileName())
                .contentType(photo.getContentType())
                .inputStream(inputStream)
                .build();

        photoStorageService.replace(existingPhotoFilename, newPhoto);

        return photo;
    }

    /**
     * This sets the filename to a string plus the restaurant and product ids. This is to make sure a product photo has
     * always the same filename.
     *
     * @param photo
     */
    private String getFileName(ProductPhoto photo) {
        return photoStorageService.getFileName("rest" + photo.getRestaurantId() + "_prod" + photo.getProductId());
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param restaurantId id of the Restaurant to find.
     * @param productId id of the Product to find.
     * @return the entity from the repository.
     */
    public ProductPhoto findOrElseThrow(Long restaurantId, Long productId) {
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new ProductPhotoNotFoundException(restaurantId, productId));
    }
}
