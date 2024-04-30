package de.app.fivegla.event.events;

import de.app.fivegla.persistence.entity.ThirdPartyApiConfiguration;

/**
 * Event for data import.
 *
 * @param thirdPartyApiConfiguration The third-party API configuration.
 */
public record DataImportEvent(ThirdPartyApiConfiguration thirdPartyApiConfiguration) {
}