package de.app.fivegla.integration.soilscout.job;

import de.app.fivegla.integration.fiware.FiwareIntegrationServiceWrapper;
import de.app.fivegla.integration.soilscout.SoilScoutSensorIntegrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Scheduled data import from Soil Scout API.
 */
@Slf4j
@Service
public class SoilScoutScheduledSensorImport {

    private final SoilScoutSensorIntegrationService soilScoutSensorIntegrationService;
    private final FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper;

    public SoilScoutScheduledSensorImport(SoilScoutSensorIntegrationService soilScoutSensorIntegrationService,
                                          FiwareIntegrationServiceWrapper fiwareIntegrationServiceWrapper) {
        this.soilScoutSensorIntegrationService = soilScoutSensorIntegrationService;
        this.fiwareIntegrationServiceWrapper = fiwareIntegrationServiceWrapper;
    }

    /**
     * Run scheduled data import.
     */
    @Scheduled(cron = "${app.scheduled.sensor-import.cron}}")
    public void run() {
        log.info("Running scheduled sensor import from Soil Scout API");
        var sensors = soilScoutSensorIntegrationService.findAll();
        log.info("Found {} sensors", sensors.size());
        sensors.forEach(fiwareIntegrationServiceWrapper::persist);
    }

}