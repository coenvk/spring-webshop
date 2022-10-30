package org.coenvk.samples.spring.webshop.shipment.controller.query;

import java.util.UUID;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.common.dto.ShipmentDto;
import org.coenvk.samples.spring.webshop.shipment.hateoas.ShipmentDtoAssembler;
import org.coenvk.samples.spring.webshop.shipment.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.shipment.model.Shipment;
import org.coenvk.samples.spring.webshop.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ShipmentQueryController {
    private final ShipmentRepository _repository;
    private final DomainMapper _mapper;
    private final ShipmentDtoAssembler _assembler;

    @Autowired
    public ShipmentQueryController(
            ShipmentRepository repository,
            DomainMapper mapper,
            ShipmentDtoAssembler assembler) {
        _repository = repository;
        _mapper = mapper;
        _assembler = assembler;
    }

    @GetMapping("shipment")
    public ResponseEntity<CollectionModel<EntityModel<ShipmentDto>>> getShipments(Pageable pageable) {
        var shipments = _repository.findAll(pageable)
                .stream()
                .map(_mapper::map)
                .toList();
        return ResponseEntity.ok(_assembler.toCollectionModel(shipments));
    }

    @GetMapping("shipment/{shipmentId}")
    public ResponseEntity<EntityModel<ShipmentDto>> getShipmentById(@PathVariable UUID shipmentId) {
        var shipment = _repository.findById(shipmentId)
                .orElseThrow(() -> new ModelNotFound(Shipment.class, shipmentId));
        var shipmentDto = _mapper.map(shipment);
        return ResponseEntity.ok(_assembler.toModel(shipmentDto));
    }
}
