package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;

import java.util.List;

public interface SalesQueryService {

    List<DailySales> queryDailySales(DailySalesFilter dailySalesFilter);
}
