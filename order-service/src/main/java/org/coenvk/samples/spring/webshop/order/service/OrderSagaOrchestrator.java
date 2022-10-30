package org.coenvk.samples.spring.webshop.order.service;

import org.coenvk.samples.spring.webshop.common.dto.command.AddToShipment;
import org.coenvk.samples.spring.webshop.common.dto.command.CancelInventoryUpdate;
import org.coenvk.samples.spring.webshop.common.dto.command.CancelOrder;
import org.coenvk.samples.spring.webshop.common.dto.command.CancelPayment;
import org.coenvk.samples.spring.webshop.common.dto.command.CompleteOrder;
import org.coenvk.samples.spring.webshop.common.dto.command.ProcessPayment;
import org.coenvk.samples.spring.webshop.common.dto.command.UpdateInventory;
import org.coenvk.samples.spring.webshop.common.dto.enums.OrderStatus;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdateCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.InventoryUpdated;
import org.coenvk.samples.spring.webshop.common.dto.event.OrderCreated;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCompleted;
import org.coenvk.samples.spring.webshop.common.dto.event.ShipmentItemAccepted;
import org.coenvk.samples.spring.webshop.common.dto.event.ShipmentItemCancelled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaOrchestrator {
    private final OrderService _orderService;

    @Autowired
    public OrderSagaOrchestrator(OrderService orderService) {
        _orderService = orderService;
    }

    public Object on(@Payload final Object event) {
        if (event instanceof OrderCreated orderCreatedEvent) {
            return on(orderCreatedEvent);
        }
        if (event instanceof PaymentCompleted paymentCompletedEvent) {
            return on(paymentCompletedEvent);
        }
        if (event instanceof PaymentCancelled paymentCancelledEvent) {
            return on(paymentCancelledEvent);
        }
        if (event instanceof InventoryUpdated inventoryUpdatedEvent) {
            return on(inventoryUpdatedEvent);
        }
        if (event instanceof InventoryUpdateCancelled inventoryUpdateCancelledEvent) {
            return on(inventoryUpdateCancelledEvent);
        }
        if (event instanceof ShipmentItemAccepted shipmentItemAcceptedEvent) {
            return on(shipmentItemAcceptedEvent);
        }
        if (event instanceof ShipmentItemCancelled shipmentItemCancelledEvent) {
            return on(shipmentItemCancelledEvent);
        }

        if (event instanceof CancelOrder cancelOrderCommand) {
            on(cancelOrderCommand);
        } else if (event instanceof CompleteOrder completeOrderCommand) {
            on(completeOrderCommand);
        }
        return null;
    }

    private Object on(@Payload final OrderCreated event) {
        return ProcessPayment.of(event.orderId(), event.orderLines());
    }

    private Object on(@Payload final PaymentCompleted event) {
        _orderService.updateOrderStatus(event.orderId(), OrderStatus.PaymentProcessed);
        return UpdateInventory.of(event.orderId(), event.orderLines());
    }

    private Object on(@Payload final PaymentCancelled event) {
        return CancelOrder.of(event.orderId());
    }

    private Object on(@Payload final InventoryUpdated event) {
        _orderService.updateOrderStatus(event.orderId(), OrderStatus.StockConfirmed);
        return AddToShipment.of(event.orderId(), event.orderLines().stream()
                .reduce(0, (quantity, orderLine) -> quantity + orderLine.getQuantity(), Integer::sum));
    }

    private Object on(@Payload final InventoryUpdateCancelled event) {
        return CancelPayment.of(event.orderId());
    }

    private Object on(@Payload final ShipmentItemAccepted event) {
        return CompleteOrder.of(event.orderId());
    }

    private Object on(@Payload final ShipmentItemCancelled event) {
        return CancelInventoryUpdate.of(event.orderId());
    }

    private void on(@Payload final CompleteOrder command) {
        _orderService.completeOrder(command);
    }

    private void on(@Payload final CancelOrder command) {
        _orderService.cancelOrder(command);
    }
}
