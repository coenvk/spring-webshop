package org.coenvk.samples.spring.webshop.inventory.controller.query;

import java.util.UUID;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.common.dto.WarehouseDto;
import org.coenvk.samples.spring.webshop.inventory.hateoas.WarehouseDtoAssembler;
import org.coenvk.samples.spring.webshop.inventory.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.inventory.repository.WarehouseRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WarehouseQueryController {
    private final WarehouseRepository _repository;
    private final DomainMapper _mapper;
    private final WarehouseDtoAssembler _assembler;

    public WarehouseQueryController(
            WarehouseRepository repository,
            DomainMapper mapper,
            WarehouseDtoAssembler assembler) {
        _repository = repository;
        _mapper = mapper;
        _assembler = assembler;
    }

    @GetMapping("warehouse")
    public ResponseEntity<CollectionModel<EntityModel<WarehouseDto>>> getWarehouses(Pageable pageable) {
        var warehouses = _repository.findAll(pageable)
                .stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(warehouses));
    }

    @GetMapping("warehouse/{warehouseId}")
    public ResponseEntity<EntityModel<WarehouseDto>> getWarehouseById(@PathVariable UUID warehouseId) {
        var warehouse = _repository.findById(warehouseId)
                .orElseThrow(ModelNotFound::new);
        var warehouseDto = _mapper.map(warehouse);
        return ResponseEntity.ok(_assembler.toModel(warehouseDto));
    }
}
