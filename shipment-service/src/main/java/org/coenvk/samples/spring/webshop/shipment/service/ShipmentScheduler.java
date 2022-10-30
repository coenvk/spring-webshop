package org.coenvk.samples.spring.webshop.shipment.service;

import java.util.Date;
import org.coenvk.samples.spring.webshop.common.dto.enums.ShipmentStatus;
import org.coenvk.samples.spring.webshop.shipment.model.Shipment;
import org.coenvk.samples.spring.webshop.shipment.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ShipmentScheduler {
    private final ShipmentRepository _repository;

    @Autowired
    public ShipmentScheduler(
            ShipmentRepository repository) {
        _repository = repository;
    }

    // start all shipments at midnight
    @Scheduled(cron = "0 59 23 * * *")
    public void transportShipments() {
        var shipments = _repository.findAllByStatus(ShipmentStatus.Submitted);

        for (var shipment : shipments) {
            shipment.setStatus(ShipmentStatus.Shipping);
            shipment.setStartDate(new Date().getTime() / 1000);
        }

        _repository.saveAll(shipments);
    }

    // complete all shipments every day at noon
    @Scheduled(cron = "0 0 12 * * *")
    public void completeShipments() {
        var shipments = _repository.findAllByStatus(ShipmentStatus.Shipping);

        for (var shipment : shipments) {
            shipment.setStatus(ShipmentStatus.Completed);
            shipment.setDeliveryDate(new Date().getTime() / 1000);
        }

        _repository.saveAll(shipments);
    }
}
