package org.coenvk.samples.spring.webshop.shipment.service;

import org.coenvk.samples.spring.webshop.common.dto.command.AddToShipment;
import org.coenvk.samples.spring.webshop.common.dto.command.CompleteOrder;
import org.coenvk.samples.spring.webshop.common.dto.event.ShipmentItemAccepted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaHandler {
    private final ShipmentService _shipmentService;

    @Autowired
    public OrderSagaHandler(
            ShipmentService shipmentService
    ) {
        _shipmentService = shipmentService;
    }

    public Object on(@Payload Object command) {
        if (command instanceof AddToShipment addToShipmentCommand) {
            return on(addToShipmentCommand);
        }
        return null;
    }

    public Object on(@Payload AddToShipment command) {
        _shipmentService.createShipment(command);
        return new ShipmentItemAccepted(command.getOrderId());
    }
}
