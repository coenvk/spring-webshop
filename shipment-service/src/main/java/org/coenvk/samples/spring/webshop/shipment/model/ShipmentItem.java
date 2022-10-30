package org.coenvk.samples.spring.webshop.shipment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.coenvk.samples.spring.toolkit.model.Idable;
import org.springframework.data.domain.Persistable;

@NamedEntityGraph(
        name = "shipmentItem:shipment",
        attributeNodes = {
                @NamedAttributeNode("shipment")
        }
)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "shipment_item")
public final class ShipmentItem implements Idable<ShipmentItem.ShipmentItemId>, Persistable<ShipmentItem.ShipmentItemId> {
    @NotNull
    @Positive
    private Integer totalQuantity;
    @ManyToOne(targetEntity = Shipment.class, fetch = FetchType.LAZY)
    @MapsId("shipmentId")
    @JsonBackReference
    private Shipment shipment;
    @Transient
    @Setter(AccessLevel.NONE)
    private boolean isNew = true;
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private ShipmentItemId id;

    public ShipmentItem(Shipment shipment, UUID orderId, int totalQuantity) {
        this.id = new ShipmentItemId(shipment.getId(), orderId);
        this.shipment = shipment;
        this.totalQuantity = totalQuantity;
    }

    @PostPersist
    @PostLoad
    void markNotNew() {
        isNew = false;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @Embeddable
    @Data
    @Setter(AccessLevel.NONE)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShipmentItemId implements Serializable {
        @Column(name = "shipment_id")
        private UUID shipmentId;

        @Column(name = "order_id")
        private UUID orderId;
    }
}
