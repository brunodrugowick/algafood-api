package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.ProductNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductCrudService {

    public static final String MSG_PRODUCT_CONFLICT = "Operation on Product %d conflicts with another entity and can not be performed.";

    private final ProductRepository productRepository;
    private final RestaurantCrudService restaurantCrudService;
    private final PhotoStorageService photoStorageService;

    @Transactional
    public Product save(Long restaurantId, Product product) {
        Restaurant restaurant = restaurantCrudService.findOrElseThrow(restaurantId);

        restaurant.addProduct(product);
        product.setRestaurant(restaurant);

        return productRepository.save(product);
    }

    @Transactional
    public void delete(Long restaurantId, Long productId) {
        ProductPhoto productPhoto = findPhotoOrElseThrow(restaurantId, productId);

        // Flush makes sure whatever is pending on the Repository is going to be committed before continuing
        productRepository.delete(productPhoto);
        productRepository.flush();

        photoStorageService.remove(productPhoto.getFileName());
    }

    /**
     * Tries to find by ID and throws the business exception @{@link EntityNotFoundException} if not found.
     *
     * @param restaurantId id of the Restaurant to find.
     * @param productId id of the Product to find.
     * @return the entity from the repository.
     */
    public Product findOrElseThrow(Long restaurantId, Long productId) {
        return productRepository.findById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }

    public ProductPhoto findPhotoOrElseThrow(Long restaurantId, Long productId) {
        return productRepository.findPhotoById(restaurantId, productId)
                .orElseThrow(() -> new ProductNotFoundException(restaurantId, productId));
    }
}
