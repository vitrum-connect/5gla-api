package de.app.fivegla.controller.dto.response;

import de.app.fivegla.api.Response;
import de.app.fivegla.controller.dto.response.inner.Group;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Schema(description = "Response wrapper.")
public class SensorAddedToGroupResponse extends Response {

    @Schema(description = "The group to which the sensor was added.")
    private Group group;
}
