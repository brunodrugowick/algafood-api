package dev.drugowick.algaworks.algafoodapi.api.controller;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesQueryService;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final SalesReportService salesReportService;

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DailySales> dailySales(@Valid DailySalesFilter filter) {
        return salesQueryService.queryDailySales(filter);
    }

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> dailySalesPdf(@Valid DailySalesFilter filter) {
        byte[] bytesPdf = salesReportService.reportDailySales(filter);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(
                HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=daily-sales.pdf");

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(httpHeaders)
                .body(bytesPdf);
    }
}
