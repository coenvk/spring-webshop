package org.coenvk.samples.spring.webshop.inventory.service;

import org.coenvk.samples.spring.webshop.common.dto.command.UpdateInventory;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdateCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaHandler {
    private final InventoryService _inventoryService;

    @Autowired
    public OrderSagaHandler(InventoryService inventoryService) {
        _inventoryService = inventoryService;
    }

    public Object on(@Payload final Object command) {
        if (command instanceof UpdateInventory updateInventoryCommand) {
            return on(updateInventoryCommand);
        }
        return null;
    }

    public Object on(@Payload final UpdateInventory command) {
        if (_inventoryService.updateInventory(command)) {
            return new InventoryUpdated(command.getOrderId(), command.getOrderLines());
        }
        return new InventoryUpdateCancelled(command.getOrderId());
    }
}
