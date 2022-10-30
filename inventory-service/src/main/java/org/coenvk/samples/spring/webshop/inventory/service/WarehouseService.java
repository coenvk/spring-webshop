package org.coenvk.samples.spring.webshop.inventory.service;

import org.coenvk.samples.spring.webshop.common.dto.command.StockInventories;
import org.coenvk.samples.spring.webshop.inventory.model.Inventory;
import org.coenvk.samples.spring.webshop.inventory.model.Warehouse;
import org.coenvk.samples.spring.webshop.inventory.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WarehouseService {
    private final WarehouseRepository _repository;

    @Value("${warehouse.inventory.initial-quantity:20}")
    private long _initialQuantity;

    @Autowired
    public WarehouseService(
            WarehouseRepository repository) {
        _repository = repository;
    }

    public void stockProduct(final StockInventories stockInventories) {
        var warehouses = _repository.findAll();
        var productId = stockInventories.getProductId();

        for (Warehouse warehouse : warehouses) {
            var optionalInventory = warehouse.getInventories().stream()
                    .filter(inventory -> inventory.getId() != null
                            && inventory.getId().getProductId().equals(productId))
                    .findAny();

            optionalInventory.ifPresentOrElse(inventory -> {
                inventory.restock(_initialQuantity);
            }, () -> {
                var inventory = new Inventory(
                        warehouse,
                        productId,
                        _initialQuantity);
                warehouse.getInventories().add(inventory);
            });
        }

        _repository.saveAll(warehouses);
    }
}
