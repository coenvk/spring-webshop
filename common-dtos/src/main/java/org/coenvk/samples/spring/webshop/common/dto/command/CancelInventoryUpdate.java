package org.coenvk.samples.spring.webshop.common.dto.command;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.annotation.NotDefault;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CancelInventoryUpdate {
    @NotNull
    @NotDefault
    private UUID orderId;
}
