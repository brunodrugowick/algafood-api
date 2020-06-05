package dev.drugowick.algaworks.algafoodapi.domain.service;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;

public interface SalesReportService {

    byte[] reportDailySales(DailySalesFilter dailySalesFilter);
}
