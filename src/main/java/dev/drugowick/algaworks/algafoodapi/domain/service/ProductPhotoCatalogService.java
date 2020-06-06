package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductPhotoCatalogService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductPhoto save(ProductPhoto photo) {
        Long restaurantId = photo.getRestaurantId();
        Long productId = photo.getProduct().getId();
        Optional<ProductPhoto> existingPhoto = productRepository.findPhotoById(restaurantId, productId);

        existingPhoto.ifPresent(productRepository::delete);

        return productRepository.save(photo);
    }
}
