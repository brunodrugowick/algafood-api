package dev.drugowick.algaworks.algafoodapi.infrastructure.service.report;

import dev.drugowick.algaworks.algafoodapi.domain.filter.DailySalesFilter;
import dev.drugowick.algaworks.algafoodapi.domain.model.dto.DailySales;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesQueryService;
import dev.drugowick.algaworks.algafoodapi.domain.service.SalesReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SalesReportServiceImpl implements SalesReportService {

    private final SalesQueryService salesQueryService;

    @Override
    public byte[] reportDailySales(DailySalesFilter dailySalesFilter) {

        // Get the report as a resource of the application
        InputStream reportAsStream = this.getClass().getResourceAsStream("/reports/jasper/daily-sales.jasper");

        // Set parameters for the report. Here we could also pass in the DailySalesFilter to show filters on the report.
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_LOCALE", Locale.getDefault());

        // Define a data source for the report. The data!
        List<DailySales> dailySalesList = salesQueryService.queryDailySales(dailySalesFilter);
        JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(dailySalesList);

        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reportAsStream, parameters, jrBeanCollectionDataSource);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Failed to generate daily sales report");
        }
    }
}
