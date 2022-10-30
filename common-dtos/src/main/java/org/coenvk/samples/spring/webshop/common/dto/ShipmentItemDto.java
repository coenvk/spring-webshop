package org.coenvk.samples.spring.webshop.common.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ShipmentItemDto {
    @NotNull
    @NotDefault
    private UUID orderId;
    @NotNull
    @Positive
    private int totalQuantity;
    @NotNull
    @NotDefault
    private UUID shipmentId;
}
