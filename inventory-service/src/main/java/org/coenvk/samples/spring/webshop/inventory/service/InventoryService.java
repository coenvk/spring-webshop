package org.coenvk.samples.spring.webshop.inventory.service;

import java.util.ArrayList;
import java.util.stream.Collectors;
import org.coenvk.samples.spring.toolkit.error.exceptions.NotSupposedToHappenException;
import org.coenvk.samples.spring.webshop.common.dto.OrderLineDto;
import org.coenvk.samples.spring.webshop.common.dto.command.DeleteInventories;
import org.coenvk.samples.spring.webshop.common.dto.command.UpdateInventory;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdateCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdated;
import org.coenvk.samples.spring.webshop.inventory.model.Inventory;
import org.coenvk.samples.spring.webshop.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class InventoryService {
    private final InventoryRepository _repository;
    private final Sinks.Many<Object> _inventorySink;

    @Value("${warehouse.inventory.restock-quantity:5}")
    private long _restockQuantity;
    @Value("${warehouse.inventory.initial-quantity:20}")
    private long _initialQuantity;

    @Autowired
    public InventoryService(
            InventoryRepository repository,
            Sinks.Many<Object> inventorySink) {
        _repository = repository;
        _inventorySink = inventorySink;
    }

    public void deleteInventories(final DeleteInventories deleteInventories) {
        var inventories = _repository.findAllByIdProductId(deleteInventories.getProductId());
        _repository.deleteAllInBatch(inventories);
    }

    @Scheduled(cron = "0 0 1 * * 0")
    public void restock() {
        var inventories = _repository.findByQuantityLessThanEqual(_restockQuantity);

        for (Inventory inventory : inventories) {
            inventory.setQuantity(_initialQuantity);
        }

        _repository.saveAll(inventories);
    }

    public boolean updateInventory(final UpdateInventory command) {
        var inventories = _repository.findAllByIdProductIdIn(command.getOrderLines().stream()
                .map(OrderLineDto::getProductId).toList()).stream().collect(
                Collectors.groupingBy(inventory -> inventory.getId().getProductId()));

        var inventoriesToUpdate = new ArrayList<Inventory>(inventories.size());
        for (var entry : inventories.entrySet()) {
            var productId = entry.getKey();
            for (var inventory : entry.getValue()) {
                var requestedQuantity = command.getOrderLines().stream().filter(
                        ol -> ol.getProductId().equals(productId)).findAny().orElseThrow(
                        NotSupposedToHappenException::new).getQuantity();
                if (requestedQuantity <= inventory.getQuantity()) {
                    inventory.setQuantity(inventory.getQuantity() - requestedQuantity);
                    inventoriesToUpdate.add(inventory);
                    break;
                }
            }
        }

        if (inventoriesToUpdate.size() != command.getOrderLines().size()) {
            _inventorySink.tryEmitNext(new InventoryUpdateCancelled(command.getOrderId()));
            return false;
        }

        _repository.saveAll(inventoriesToUpdate);
        _inventorySink.tryEmitNext(new InventoryUpdated(command.getOrderId(), command.getOrderLines()));
        return true;
    }
}
