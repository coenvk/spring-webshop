package org.coenvk.samples.spring.webshop.inventory.model;

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
import javax.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.coenvk.samples.spring.toolkit.model.Idable;
import org.springframework.data.domain.Persistable;

@NamedEntityGraph(
        name = "inventory:warehouse",
        attributeNodes = {
                @NamedAttributeNode("warehouse")
        }
)
@Data
@Entity
@NoArgsConstructor
@Table(name = "inventory")
public final class Inventory implements Idable<Inventory.InventoryId>, Persistable<Inventory.InventoryId> {
    @NotNull
    @PositiveOrZero
    private Long quantity;
    @ManyToOne(targetEntity = Warehouse.class, fetch = FetchType.LAZY)
    @MapsId("warehouseId")
    @JsonBackReference
    private Warehouse warehouse;
    @Transient
    @Setter(AccessLevel.NONE)
    private boolean isNew = true;
    @EmbeddedId
    @Setter(AccessLevel.NONE)
    private InventoryId id;

    public Inventory(Warehouse warehouse, UUID productId, long quantity) {
        this.id = new InventoryId(warehouse.getId(), productId);
        this.warehouse = warehouse;
        this.quantity = quantity;
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

    public void restock(long quantity) {
        setQuantity(getQuantity() + quantity);
    }

    @Embeddable
    @Data
    @Setter(AccessLevel.NONE)
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryId implements Serializable {
        @Column(name = "warehouse_id")
        private UUID warehouseId;

        @Column(name = "product_id")
        private UUID productId;
    }
}
