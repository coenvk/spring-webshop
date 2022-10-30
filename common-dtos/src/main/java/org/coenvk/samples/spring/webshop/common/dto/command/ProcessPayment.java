package org.coenvk.samples.spring.webshop.common.dto.command;

import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;
import org.coenvk.samples.spring.webshop.common.dto.OrderLineDto;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ProcessPayment {
    @NotNull
    @NotDefault
    private UUID orderId;
    @NotEmpty
    private List<OrderLineDto> orderLines;
}
