package org.coenvk.samples.spring.webshop.common.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class OrderLineDto {
    @NotNull
    private UUID productId;
    @Positive
    @NotNull
    private int quantity;
}
