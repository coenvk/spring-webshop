package org.coenvk.samples.spring.webshop.inventory.repository;

import java.util.List;
import java.util.UUID;
import org.coenvk.samples.spring.webshop.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, UUID> {
    @Transactional(readOnly = true)
    @Query("select i from Inventory i where i.id.productId = :id")
    List<Inventory> findAllByIdProductId(@Param("id") UUID productId);

    @Transactional(readOnly = true)
    @Query("select i from Inventory i where i.id.productId in :ids")
    List<Inventory> findAllByIdProductIdIn(@Param("ids") List<UUID> productIds);

    @Transactional(readOnly = true)
    @Query("select i from Inventory i where i.id.warehouseId = :id")
    List<Inventory> findAllByIdWarehouseId(@Param("id") UUID warehouseId);

    @Transactional(readOnly = true)
    @Query("select i from Inventory i where i.id.warehouseId in :ids")
    List<Inventory> findAllByIdWarehouseIdIn(@Param("ids") List<UUID> warehouseIds);

    @Transactional(readOnly = true)
    List<Inventory> findByQuantityLessThanEqual(long quantity);
}
