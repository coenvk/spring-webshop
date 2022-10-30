package org.coenvk.samples.spring.webshop.catalog.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.model.AuditedIdable;
import org.coenvk.samples.spring.toolkit.model.AuditedUuidable;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "product")
public final class Product extends AuditedUuidable<UUID> {
    @NotBlank
    @NotNull
    private String name;
    private String description;
    @NotNull
    @PositiveOrZero
    private Long unitPrice;
}
