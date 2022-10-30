package org.coenvk.samples.spring.webshop.shipment.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.coenvk.samples.spring.webshop.common.dto.enums.ShipmentStatus;
import org.coenvk.samples.spring.webshop.shipment.model.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ShipmentRepository extends JpaRepository<Shipment, UUID> {
    @Transactional(readOnly = true)
    @EntityGraph("shipment:shipmentItems")
    List<Shipment> findAllByStatus(ShipmentStatus status);

    @Transactional(readOnly = true)
    @EntityGraph("shipment:shipmentItems")
    Optional<Shipment> findByStatus(ShipmentStatus status);

    @Override
    @EntityGraph("shipment:shipmentItems")
    List<Shipment> findAll();

    @Override
    @EntityGraph("shipment:shipmentItems")
    Page<Shipment> findAll(Pageable pageable);

    @Override
    @EntityGraph("shipment:shipmentItems")
    Optional<Shipment> findById(UUID uuid);
}
