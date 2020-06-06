package dev.drugowick.algaworks.algafoodapi.domain.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;

public interface ProductRepositoryQueries {

    ProductPhoto save(ProductPhoto photo);

    void delete(ProductPhoto photo);
}
