package org.coenvk.samples.spring.webshop.common.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.webshop.common.dto.command.UpdateProduct;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ProductDto extends UpdateProduct {
}
