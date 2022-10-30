package org.coenvk.samples.spring.webshop.inventory.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.coenvk.samples.spring.toolkit.model.AuditedUuidable;
import org.coenvk.samples.spring.webshop.common.dto.valueobject.Address;

@NamedEntityGraph(
        name = "warehouse:inventories",
        attributeNodes = {
                @NamedAttributeNode("inventories")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "warehouse")
public final class Warehouse extends AuditedUuidable<UUID> {
    @NotNull
    @NotBlank
    private String name;
    @Embedded
    private Address address;
    @OneToMany(targetEntity = Inventory.class, mappedBy = "warehouse", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<Inventory> inventories = new HashSet<>();
}
