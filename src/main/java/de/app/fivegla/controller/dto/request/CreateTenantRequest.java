package de.app.fivegla.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a request for tenant creation.
 */
@Getter
@Setter
@Schema(description = "Represents a request for tenant creation.")
public class CreateTenantRequest {

    /**
     * The name of the tenant.
     */
    @NotBlank
    @Schema(description = "The name of the tenant.")
    private String name;

    /**
     * The tenant id.
     * This field represents the unique identifier for a tenant.
     * <p>
     * Constraints:
     * - The tenant id can only contain alphanumeric characters.
     * - The maximum length of the tenant id is 50 characters.
     * - The tenant id is required and cannot be blank.
     */
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{1,50}$")
    @Schema(description = "The tenant id, only alphanumeric characters and the '-' are allowed, maximum length is 50 characters.")
    private String tenantId;

    /**
     * The description of the tenant, the description optional.
     */
    @Schema(description = "The description of the tenant, the description optional.")
    private String description;

}
