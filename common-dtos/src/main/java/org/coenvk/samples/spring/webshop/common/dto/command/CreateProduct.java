package org.coenvk.samples.spring.webshop.common.dto.command;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateProduct {
    @NotBlank
    @NotNull
    private String name;
    private String description;
    @NotNull
    @PositiveOrZero
    private long unitPrice;
}
