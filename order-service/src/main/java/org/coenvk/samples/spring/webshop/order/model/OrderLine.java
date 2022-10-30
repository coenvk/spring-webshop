package org.coenvk.samples.spring.webshop.order.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.coenvk.samples.spring.toolkit.model.AbstractUuidable;

@NamedEntityGraph(
        name = "orderLine:order",
        attributeNodes = {
                @NamedAttributeNode("order")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "order_line")
public final class OrderLine extends AbstractUuidable<UUID> {
    @NotNull
    private UUID productId;
    @NotNull
    @Positive
    private int quantity;
    @ManyToOne(targetEntity = Order.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Order order;
}
