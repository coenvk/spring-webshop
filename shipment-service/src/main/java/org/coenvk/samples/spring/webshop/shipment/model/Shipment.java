package org.coenvk.samples.spring.webshop.shipment.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.coenvk.samples.spring.toolkit.model.AbstractUuidable;
import org.coenvk.samples.spring.webshop.common.dto.enums.ShipmentStatus;

@NamedEntityGraph(
        name = "shipment:shipmentItems",
        attributeNodes = {
                @NamedAttributeNode("shipmentItems")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "shipment")
public final class Shipment extends AbstractUuidable<UUID> {
    private Long startDate;
    private Long deliveryDate;
    @Column(nullable = false)
    private ShipmentStatus status;
    @OneToMany(targetEntity = ShipmentItem.class, mappedBy = "shipment", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private Set<ShipmentItem> shipmentItems = new HashSet<>();
}
