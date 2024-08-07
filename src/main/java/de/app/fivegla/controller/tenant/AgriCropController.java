package de.app.fivegla.controller.tenant;

import de.app.fivegla.api.Response;
import de.app.fivegla.business.AgriCropService;
import de.app.fivegla.business.GroupService;
import de.app.fivegla.business.TenantService;
import de.app.fivegla.config.security.marker.TenantCredentialApiAccess;
import de.app.fivegla.controller.api.BaseMappings;
import de.app.fivegla.controller.dto.request.ImportAgriCropFromCsvRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(BaseMappings.AGRI_CROP)
public class AgriCropController implements TenantCredentialApiAccess {

    private final AgriCropService agriCropService;
    private final TenantService tenantService;
    private final GroupService groupService;

    @Operation(
            operationId = "agri-crop.import-csv",
            description = "Imports the CSV containing the agri-crop data.",
            tags = BaseMappings.AGRI_CROP
    )
    @ApiResponse(
            responseCode = "201",
            description = "The CSV was imported successfully.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "The request is invalid.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = Response.class)
            )
    )
    @PostMapping(value = "/csv/{cropId}/{zone}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<? extends Response> importCsv(@RequestBody @Valid @Parameter(description = "The request body, containing the CSV, the crop ID and the optional group") ImportAgriCropFromCsvRequest request,
                                                        Principal principal) {

        var tenant = validateTenant(tenantService, principal);
        var group = groupService.getOrDefault(tenant, request.getGroupId());
        agriCropService.createFromCsv(tenant, group, request.getCropId(), request.getCsvData());
        return ResponseEntity.status(HttpStatus.CREATED).body(new Response());
    }
}
