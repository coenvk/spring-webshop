package org.coenvk.samples.spring.webshop.common.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;
import org.coenvk.samples.spring.webshop.common.dto.enums.ShipmentStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ShipmentDto {
    @NotNull
    @NotDefault
    private UUID shipmentId;
    private Date startDate;
    private Date deliveryDate;
    private ShipmentStatus status;
    @NotEmpty
    private List<ShipmentItemDto> shipmentItems;
}
