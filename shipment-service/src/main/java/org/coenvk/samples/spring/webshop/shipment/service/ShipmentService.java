package org.coenvk.samples.spring.webshop.shipment.service;

import java.util.Date;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.common.dto.ShipmentDto;
import org.coenvk.samples.spring.webshop.common.dto.command.AddToShipment;
import org.coenvk.samples.spring.webshop.common.dto.command.CompleteShipment;
import org.coenvk.samples.spring.webshop.common.dto.command.TransportShipment;
import org.coenvk.samples.spring.webshop.common.dto.enums.ShipmentStatus;
import org.coenvk.samples.spring.webshop.shipment.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.shipment.model.Shipment;
import org.coenvk.samples.spring.webshop.shipment.model.ShipmentItem;
import org.coenvk.samples.spring.webshop.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {
    private final ShipmentRepository _repository;
    private final DomainMapper _mapper;

    @Autowired
    public ShipmentService(
            ShipmentRepository _repository,
            DomainMapper mapper
    ) {
        this._repository = _repository;
        _mapper = mapper;
    }

    public ShipmentDto createShipment(AddToShipment addToShipment) {
        var optionalShipment = _repository.findByStatus(ShipmentStatus.Submitted);

        Shipment shipment;
        if (optionalShipment.isPresent()) {
            shipment = optionalShipment.get();
        } else {
            shipment = new Shipment();
            shipment.setStatus(ShipmentStatus.Submitted);
        }

        var savedShipment = _repository.save(shipment);

        var shipmentItem = new ShipmentItem(savedShipment, addToShipment.getOrderId(),
                addToShipment.getTotalQuantity());
        savedShipment.getShipmentItems().add(shipmentItem);
        savedShipment = _repository.save(savedShipment);
        return _mapper.map(savedShipment);
    }

    public ShipmentDto transportShipment(TransportShipment transportShipment) {
        var shipment = _repository.findById(transportShipment.getShipmentId())
                .orElseThrow(() -> new ModelNotFound(Shipment.class, transportShipment.getShipmentId()));
        shipment.setStatus(ShipmentStatus.Shipping);
        shipment.setStartDate(new Date().getTime() / 1000);
        var savedShipment = _repository.save(shipment);
        return _mapper.map(savedShipment);
    }

    public ShipmentDto completeShipment(CompleteShipment completeShipment) {
        var shipment = _repository.findById(completeShipment.getShipmentId())
                .orElseThrow(() -> new ModelNotFound(Shipment.class, completeShipment.getShipmentId()));
        shipment.setStatus(ShipmentStatus.Completed);
        shipment.setDeliveryDate(new Date().getTime() / 1000);
        var savedShipment = _repository.save(shipment);
        return _mapper.map(savedShipment);
    }
}
