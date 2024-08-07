package de.app.fivegla.integration.agvolution;

import de.app.fivegla.api.Manufacturer;
import de.app.fivegla.integration.agvolution.model.SeriesEntry;
import de.app.fivegla.monitoring.JobMonitor;
import de.app.fivegla.persistence.entity.Tenant;
import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * Scheduled data import from Agvolution API.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgvolutionMeasurementImport {

    private final AgvolutionSensorDataIntegrationService agvolutionSensorDataIntegrationService;
    private final AgvolutionFiwareIntegrationServiceWrapper agvolutionFiwareIntegrationServiceWrapper;
    private final JobMonitor jobMonitor;

    @Value("${app.scheduled.daysInThePastForInitialImport}")
    private int daysInThePastForInitialImport;

    /**
     * Run scheduled data import.
     */
    @Async
    public void run(Tenant tenant, ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
        var begin = Instant.now();
        try {
            if (null != thirdPartyApiConfiguration.getLastRun()) {
                log.info("Running scheduled data import from Agvolution API");
                var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(thirdPartyApiConfiguration, thirdPartyApiConfiguration.getLastRun().toInstant());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGVOLUTION, seriesEntries.size());
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                seriesEntries.forEach(seriesEntry -> persistDataWithinFiware(tenant, seriesEntry));
            } else {
                log.info("Running initial data import from Agvolution API, this may take a while");
                var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(thirdPartyApiConfiguration, Instant.now().minus(daysInThePastForInitialImport, ChronoUnit.DAYS));
                log.info("Found {} seriesEntries", seriesEntries.size());
                log.info("Persisting {} seriesEntries", seriesEntries.size());
                jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGVOLUTION, seriesEntries.size());
                seriesEntries.forEach(seriesEntry -> persistDataWithinFiware(tenant, seriesEntry));
            }
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agvolution API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGVOLUTION);
        } finally {
            log.info("Finished scheduled data import from Agvolution API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.AGVOLUTION, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    /**
     * Run historical data import.
     */
    @Async
    public void run(Tenant tenant, ThirdPartyApiConfiguration thirdPartyApiConfiguration, Instant start) {
        var begin = Instant.now();
        try {
            log.info("Running historical data import from Agvolution API, this may take a while");
            var seriesEntries = agvolutionSensorDataIntegrationService.fetchAll(thirdPartyApiConfiguration, start);
            log.info("Found {} seriesEntries", seriesEntries.size());
            log.info("Persisting {} seriesEntries", seriesEntries.size());
            jobMonitor.logNrOfEntitiesFetched(Manufacturer.AGVOLUTION, seriesEntries.size());
            seriesEntries.forEach(seriesEntry -> persistDataWithinFiware(tenant, seriesEntry));
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agvolution API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGVOLUTION);
        } finally {
            log.info("Finished scheduled data import from Agvolution API");
            var end = Instant.now();
            jobMonitor.logJobExecutionTime(Manufacturer.AGVOLUTION, begin.until(end, ChronoUnit.SECONDS));
        }
    }

    private void persistDataWithinFiware(Tenant tenant, SeriesEntry seriesEntry) {
        try {
            agvolutionFiwareIntegrationServiceWrapper.persist(tenant, seriesEntry);
        } catch (Exception e) {
            log.error("Error while running scheduled data import from Agvolution API", e);
            jobMonitor.logErrorDuringExecution(Manufacturer.AGVOLUTION);
        }
    }

}
