package org.coenvk.samples.spring.webshop.inventory.controller.query;

import java.util.UUID;
import org.coenvk.samples.spring.webshop.common.dto.InventoryDto;
import org.coenvk.samples.spring.webshop.inventory.hateoas.InventoryDtoAssembler;
import org.coenvk.samples.spring.webshop.inventory.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.inventory.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InventoryQueryController {
    private final InventoryRepository _repository;
    private final DomainMapper _mapper;
    private final InventoryDtoAssembler _assembler;

    @Autowired
    public InventoryQueryController(
            InventoryRepository repository,
            DomainMapper mapper,
            InventoryDtoAssembler assembler) {
        _repository = repository;
        _mapper = mapper;
        _assembler = assembler;
    }

    @GetMapping("product/{productId}/inventory")
    public ResponseEntity<CollectionModel<EntityModel<InventoryDto>>> getInventoriesForProduct(
            @PathVariable UUID productId) {
        var inventories = _repository.findAllByIdProductId(productId).stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(inventories));
    }

    @GetMapping("warehouse/{warehouseId}/inventory")
    public ResponseEntity<CollectionModel<EntityModel<InventoryDto>>> getInventoriesForWarehouse(
            @PathVariable UUID warehouseId) {
        var inventories = _repository.findAllByIdWarehouseId(warehouseId).stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(inventories));
    }
}
