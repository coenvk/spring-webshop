package org.coenvk.samples.spring.webshop.inventory.repository;

import java.util.List;
import java.util.UUID;
import org.coenvk.samples.spring.webshop.inventory.model.Warehouse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {
    @Override
    @EntityGraph("warehouse:inventories")
    List<Warehouse> findAll();
}
