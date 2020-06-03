package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final SalesQueryService salesQueryService;

    @GetMapping("/daily-sales")
    public List<DailySales> dailySales(@Valid DailySalesFilter filter) {
        return salesQueryService.queryDailySales(filter);
    }
}
