package org.coenvk.samples.spring.webshop.order.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
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
import org.coenvk.samples.spring.toolkit.model.AuditedUuidable;
import org.coenvk.samples.spring.webshop.common.dto.enums.OrderStatus;
import org.coenvk.samples.spring.webshop.common.dto.valueobject.Address;

@NamedEntityGraph(
        name = "order:orderLines",
        attributeNodes = {
                @NamedAttributeNode("orderLines")
        }
)
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@Table(name = "order")
public final class Order extends AuditedUuidable<UUID> {
    @Embedded
    private Address address;
    private OrderStatus status;
    @OneToMany(targetEntity = OrderLine.class, mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonManagedReference
    private List<OrderLine> orderLines = new ArrayList<>();
}
