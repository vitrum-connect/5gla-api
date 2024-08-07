package de.app.fivegla.integration.agranimo;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.integration.agranimo.model.SoilMoisture;
import de.app.fivegla.integration.agranimo.model.Zone;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.InstantAttribute;
import de.app.fivegla.integration.fiware.model.internal.NumberAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgranimoFiwareIntegrationServiceWrapper {

    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;
    private final GroupService groupService;

    /**
     * Persists the soil moisture measurement for a given group.
     *
     * @param zone         the group associated with the soil moisture measurement
     * @param soilMoisture the soil moisture measurement to persist
     */
    public void persist(Tenant tenant, Zone zone, SoilMoisture soilMoisture) {
        var group = groupService.findGroupByTenantAndSensorId(tenant, soilMoisture.getDeviceId());
        if (group.isDefaultGroupForTenant()) {
            log.warn("Looks like the group for the sensor with id {} is not set. We are using the default group for the tenant.", soilMoisture.getDeviceId());
        }
        var smo1 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                EntityType.AGRANIMO_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("smo1"),
                new NumberAttribute(soilMoisture.getSmo1()),
                new InstantAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        fiwareEntityIntegrationService.persist(tenant, group, smo1);

        var smo2 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                EntityType.AGRANIMO_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("smo2"),
                new NumberAttribute(soilMoisture.getSmo2()),
                new InstantAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        fiwareEntityIntegrationService.persist(tenant, group, smo2);

        var smo3 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                EntityType.AGRANIMO_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("smo3"),
                new NumberAttribute(soilMoisture.getSmo3()),
                new InstantAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        fiwareEntityIntegrationService.persist(tenant, group, smo3);

        var smo4 = new DeviceMeasurement(
                tenant.getFiwarePrefix() + soilMoisture.getDeviceId(),
                EntityType.AGRANIMO_SENSOR.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("smo4"),
                new NumberAttribute(soilMoisture.getSmo4()),
                new InstantAttribute(soilMoisture.getTms()),
                new EmptyAttribute(),
                zone.getData().getPoint().getCoordinates()[0],
                zone.getData().getPoint().getCoordinates()[1]);
        fiwareEntityIntegrationService.persist(tenant, group, smo4);
    }

}
