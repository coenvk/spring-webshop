package org.coenvk.samples.spring.webshop.common.dto.event;

import java.util.UUID;

public record ShipmentItemCancelled(UUID orderId, UUID shipmentId) {
}
