package de.app.fivegla.integration.fiware.model;

import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.model.api.Validatable;
import de.app.fivegla.integration.fiware.model.internal.Attribute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents a MicaSense image.
 */
@Slf4j
public record CameraImage(
        String id,
        String type,
        Attribute group,
        Attribute oid,
        Attribute cameraId,
        Attribute transactionId,
        Attribute imageChannel,
        Attribute imagePath,
        Attribute dateCreated,
        double latitude,
        double longitude
) implements FiwareEntity, Validatable {

    @Override
    public String asJson() {
        validate();
        var json = "{" +
                "  \"id\":\"" + id.trim() + "\"," +
                "  \"type\":\"" + type.trim() + "\"," +
                "  \"customGroup\":" + group.asJson().trim() + "," +
                "  \"oid\":" + oid.asJson().trim() + "," +
                "  \"cameraId\":" + cameraId.asJson().trim() + "," +
                "  \"transactionId\":" + transactionId.asJson().trim() + "," +
                "  \"imageChannel\":" + imageChannel.asJson().trim() + "," +
                "  \"imagePath\":" + imagePath.asJson().trim() + "," +
                "  \"dateCreated\":" + dateCreated.asJson().trim() + "," +
                "  \"location\":" + locationAsJson(latitude, longitude).trim() +
                "}";
        log.debug("{} as JSON: {}", this.getClass().getSimpleName(), json);
        return json;
    }

    @Override
    public String asSmartModelJson() {
        throw new NotImplementedException("Smart model JSON is not supported for CameraImage.");
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the camera image must not be blank.");
        }
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The type of the camera image must not be blank.");
        }
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean shouldCreateSmartModelEntity() {
        return false;
    }
}
