package org.coenvk.samples.spring.webshop.common.dto.command;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.webshop.common.dto.OrderLineDto;
import org.coenvk.samples.spring.webshop.common.dto.valueobject.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateOrder {
    @NotNull
    @Valid
    private Address address;
    @NotEmpty
    private List<OrderLineDto> orderLines;
}
