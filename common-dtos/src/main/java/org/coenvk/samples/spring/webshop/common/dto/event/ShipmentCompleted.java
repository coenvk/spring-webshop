package org.coenvk.samples.spring.webshop.common.dto.event;

import java.util.UUID;

public record ShipmentCompleted(UUID shipmentId) {
}
