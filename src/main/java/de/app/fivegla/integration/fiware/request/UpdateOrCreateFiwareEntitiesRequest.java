package de.app.fivegla.integration.fiware.request;

import de.app.fivegla.integration.fiware.model.api.FiwareEntity;
import de.app.fivegla.integration.fiware.request.enums.ActionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Request.
 */
@Getter
@Setter
@Builder
public class UpdateOrCreateFiwareEntitiesRequest {

    /**
     * The action type.
     */
    private final String actionType = ActionType.APPEND.getKey();

    /**
     * The entities.
     */
    private List<FiwareEntity> entities;

    public String asJson() {
        return "{\"actionType\":\"" + actionType + "\",\"entities\":[" + entitiesAsJson() + "]}";
    }

    private String entitiesAsJson() {
        return entities.stream().map(FiwareEntity::asJson).collect(Collectors.joining(","));
    }
}
