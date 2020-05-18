package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.exception.EntityNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.exception.ProductNotFoundException;
import dev.drugowick.algaworks.algafoodapi.domain.model.Product;
import dev.drugowick.algaworks.algafoodapi.domain.model.Restaurant;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCrudService {

    public static final String MSG_PRODUCT_CONFLICT = "Operation on Product %d conflicts with another entity and can not be performed.";

    private final ProductRepository productRepository;
    private final RestaurantCrudService restaurantCrudService;

    public ProductCrudService(ProductRepository productRepository, RestaurantCrudService restaurantCrudService) {
        this.productRepository = productRepository;
        this.restaurantCrudService = restaurantCrudService;
    }

    @Transactional
    public Product save(Long restaurantId, Product product) {
        Restaurant restaurant = restaurantCrudService.findOrElseThrow(restaurantId);

        restaurant.addProduct(product);
        product.setRestaurant(restaurant);

        return productRepository.save(product);
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
}
