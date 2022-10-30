package org.coenvk.samples.spring.webshop.inventory.service;

import org.coenvk.samples.spring.webshop.common.dto.command.DeleteInventories;
import org.coenvk.samples.spring.webshop.common.dto.command.StockInventories;
import org.coenvk.samples.spring.webshop.common.dto.event.ProductCreated;
import org.coenvk.samples.spring.webshop.common.dto.event.ProductDeleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class CatalogEventHandler {
    private final WarehouseService _warehouseService;
    private final InventoryService _inventoryService;

    @Autowired
    public CatalogEventHandler(
            WarehouseService warehouseService,
            InventoryService inventoryService) {
        _warehouseService = warehouseService;
        _inventoryService = inventoryService;
    }

    public void on(@Payload final Object event) {
        if (event instanceof ProductDeleted productDeletedEvent) {
            on(productDeletedEvent);
        } else if (event instanceof ProductCreated productCreatedEvent) {
            on(productCreatedEvent);
        }
    }

    public void on(@Payload final ProductDeleted event) {
        _inventoryService.deleteInventories(DeleteInventories.of(event.productId()));
    }

    public void on(@Payload final ProductCreated event) {
        _warehouseService.stockProduct(StockInventories.of(event.productId()));
    }
}
