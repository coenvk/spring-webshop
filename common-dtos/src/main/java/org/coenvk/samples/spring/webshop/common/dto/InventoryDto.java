package org.coenvk.samples.spring.webshop.common.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;

@Data
@NoArgsConstructor
public class InventoryDto {
    @NotNull
    @PositiveOrZero
    private long quantity;
    @NotNull
    @NotDefault
    private UUID productId;
    @NotNull
    @NotDefault
    private UUID warehouseId;
}
