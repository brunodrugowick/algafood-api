package dev.drugowick.algaworks.algafoodapi.infrastructure.service;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.List;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {

    private EntityManager manager;

    public SalesQueryServiceImpl(EntityManager manager) {
        this.manager = manager;
    }

    @Override
    public List<DailySales> queryDailySales(DailySalesFilter dailySalesFilter) {

        // Preparing CriteriaBuilder through EntityManager
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<DailySales> query = builder.createQuery(DailySales.class);
        // from clause
        Root<Order> root = query.from(Order.class);
        // creating a function to be executed on the database (to be used on the select clause
        Expression<Date> functionDateOfCreation = builder.function("date", Date.class, root.get("createdDate"));
        // Creating the select clause. Instructs that the return type is a DailySales
        //The order of the selection fields matches the order of the DailySales constructor
        CompoundSelection<DailySales> selection = builder.construct(DailySales.class,
                functionDateOfCreation, // LocalDate day
                builder.count(root.get("id")), // count id to get salesQuantity on that day
                builder.sum(root.get("total"))); // sum the column total to get salesAmount on that day

        // "Forming" the query
        query.select(selection);
        query.groupBy(functionDateOfCreation);
        // Runs using the EntityManager
        TypedQuery<DailySales> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }
}
