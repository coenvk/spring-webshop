package org.coenvk.samples.spring.webshop.common.dto;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;
import org.coenvk.samples.spring.webshop.common.dto.enums.OrderStatus;
import org.coenvk.samples.spring.webshop.common.dto.valueobject.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderDto {
    @NotNull
    @NotDefault
    private UUID orderId;
    @NotNull
    private Address address;
    private OrderStatus status;
    @NotEmpty
    private List<OrderLineDto> orderLines;
}
