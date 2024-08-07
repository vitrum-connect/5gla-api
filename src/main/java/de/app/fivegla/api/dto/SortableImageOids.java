package de.app.fivegla.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * Represents a sortable image object ID.
 */
@Schema(description = "Represents a sortable image object ID.")
public record SortableImageOids(
        @Schema(description = "The image object ID.")
        String oid,
        @Schema(description = "The timestamp of the image.")
        Date timestamp) implements Comparable<SortableImageOids> {
    @Override
    public int compareTo(SortableImageOids o) {
        return timestamp.compareTo(o.timestamp);
    }
}
