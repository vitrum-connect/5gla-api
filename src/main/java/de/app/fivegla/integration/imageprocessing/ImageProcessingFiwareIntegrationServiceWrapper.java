package de.app.fivegla.integration.imageprocessing;


import de.app.fivegla.api.enums.EntityType;
import de.app.fivegla.integration.fiware.FiwareEntityIntegrationService;
import de.app.fivegla.integration.fiware.model.DeviceMeasurement;
import de.app.fivegla.integration.fiware.model.internal.DateAttribute;
import de.app.fivegla.integration.fiware.model.internal.EmptyAttribute;
import de.app.fivegla.integration.fiware.model.internal.TextAttribute;
import de.app.fivegla.persistence.entity.Group;
import de.app.fivegla.persistence.entity.Image;
import de.app.fivegla.persistence.entity.Tenant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service for integration with FIWARE.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImageProcessingFiwareIntegrationServiceWrapper {
    private final FiwareEntityIntegrationService fiwareEntityIntegrationService;

    @Value("${app.imagePathBaseUrl}")
    private String imagePathBaseUrl;

    /**
     * Create a new drone device measurement in FIWARE.
     *
     * @param image the image to create the measurement for
     */
    public void createDroneDeviceMeasurement(Tenant tenant, Group group, String droneId, Image image) {
        var deviceMeasurement = new DeviceMeasurement(
                tenant.getFiwarePrefix() + droneId,
                EntityType.MICASENSE_IMAGE.getKey(),
                new TextAttribute(group.getOid()),
                new TextAttribute("image"),
                new EmptyAttribute(),
                new DateAttribute(image.getMeasuredAt()),
                new TextAttribute(imagePathBaseUrl + image.getOid()),
                image.getLatitude(),
                image.getLongitude());
        fiwareEntityIntegrationService.persist(tenant, group, deviceMeasurement);
    }

}
