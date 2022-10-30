package org.coenvk.samples.spring.webshop.common.dto;

import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;
import org.coenvk.samples.spring.webshop.common.dto.valueobject.Address;

@Data
@NoArgsConstructor
public class WarehouseDto {
    @NotNull
    @NotDefault
    private UUID warehouseId;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    private Address address;
}
