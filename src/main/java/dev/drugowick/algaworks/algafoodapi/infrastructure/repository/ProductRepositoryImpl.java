package dev.drugowick.algaworks.algafoodapi.infrastructure.repository;

import dev.drugowick.algaworks.algafoodapi.domain.model.ProductPhoto;
import dev.drugowick.algaworks.algafoodapi.domain.repository.ProductRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryQueries {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public ProductPhoto save(ProductPhoto photo) {
        return entityManager.merge(photo);
    }

    @Transactional
    @Override
    public void delete(ProductPhoto photo) {
        entityManager.remove(photo);
    }
}
