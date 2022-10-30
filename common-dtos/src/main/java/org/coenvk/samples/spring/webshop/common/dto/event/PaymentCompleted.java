package org.coenvk.samples.spring.webshop.common.dto.event;

import java.util.List;
import java.util.UUID;
import org.coenvk.samples.spring.webshop.common.dto.OrderLineDto;

public record PaymentCompleted(UUID orderId, List<OrderLineDto> orderLines) {
}
