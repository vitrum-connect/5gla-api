package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Request to add a device position.")
public class AddDevicePositionRequest {

    @Schema(description = "The latitude of the device.")
    private double latitude;

    @Schema(description = "The longitude of the device.")
    private double longitude;

    @Schema(description = "A custom group ID, which can be used to group devices / measurements. This is optional, if not set, the default group will be used.")
    protected String groupId;
}
