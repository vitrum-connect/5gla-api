package de.app.fivegla.controller.global;

import de.app.fivegla.api.Response;
import de.app.fivegla.config.security.marker.ApiKeyApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.response.FiwareStatusResponse;
import de.app.fivegla.integration.fiware.StatusIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for information purpose.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.INFO)
public class InfoController implements ApiKeyApiAccess {

    private final StatusIntegrationService statusIntegrationService;

    /**
     * Returns the status of the fiware connection.
     *
     * @return the status of the fiware connection
     */
    @Operation(
            operationId = "info.fiware",
            description = "Fetch the status of the fiware connection.",
            tags = BaseMappings.INFO
    )
    @ApiResponse(
            responseCode = "200",
            description = "The status of the fiware connection.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = FiwareStatusResponse.class)
            )
    )
    @GetMapping(value = "/fiware", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> getFiwareStatus() {
        var version = statusIntegrationService.getVersion();
        return ResponseEntity.ok(FiwareStatusResponse.builder()
                .fiwareStatus(HttpStatus.OK)
                .fiwareVersion(version)
                .build());
    }

}
