package dev.drugowick.algaworks.algafoodapi.infrastructure.service.query;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.Order;
import dev.drugowick.algaworks.algafoodapi.domain.model.OrderStatus;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesQueryService;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class SalesQueryServiceImpl implements SalesQueryService {

    private final EntityManager manager;

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
        // creating a function to be executed on the database (to be used on the select clause)
        Expression<Date> functionConvertTimezone = builder.function("convert_tz", Date.class,
                root.get("createdDate"),
                builder.literal("+00:00"),
                builder.literal(getOffsetString(dailySalesFilter)));
        Expression<Date> functionDateOfCreation = builder.function("date", Date.class, functionConvertTimezone);
        // Creating the select clause. Instructs that the return type is a DailySales
        //The order of the selection fields matches the order of the DailySales constructor
        CompoundSelection<DailySales> selection = builder.construct(DailySales.class,
                functionDateOfCreation, // LocalDate day
                builder.count(root.get("id")), // count id to get salesQuantity on that day
                builder.sum(root.get("total"))); // sum the column total to get salesAmount on that day

        // "Forming" the query
        query.select(selection);
        query.groupBy(functionDateOfCreation);

        List<Predicate> predicates = new ArrayList<Predicate>();
        if (dailySalesFilter.getRestaurantId() != null) {
            predicates.add(builder.equal(root.get("restaurant"), dailySalesFilter.getRestaurantId()));
        }

        if (dailySalesFilter.getCreateDateStart() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"),
                    dailySalesFilter.getCreateDateStart()));
        }

        if (dailySalesFilter.getCreateDateEnd() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("createdDate"),
                    dailySalesFilter.getCreateDateEnd()));
        }

        predicates.add(root.get("status").in(
                OrderStatus.CONFIRMED, OrderStatus.DELIVERED));
        query.where(predicates.toArray(new Predicate[0]));
        // Runs using the EntityManager);
        TypedQuery<DailySales> typedQuery = manager.createQuery(query);

        return typedQuery.getResultList();
    }

    private String getOffsetString(DailySalesFilter dailySalesFilter) {
        ZoneOffset zoneOffset = dailySalesFilter.getCreateDateStart().getOffset();
        return zoneOffset.toString().equals("Z") ? "-00:00" : zoneOffset.toString();
    }
}
