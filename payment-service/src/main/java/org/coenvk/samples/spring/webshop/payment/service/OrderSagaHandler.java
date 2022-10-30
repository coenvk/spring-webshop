package org.coenvk.samples.spring.webshop.payment.service;

import org.coenvk.samples.spring.webshop.common.dto.command.CancelPayment;
import org.coenvk.samples.spring.webshop.common.dto.command.ProcessPayment;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class OrderSagaHandler {
    private final PaymentService _paymentService;

    @Autowired
    public OrderSagaHandler(
            PaymentService paymentService
    ) {
        _paymentService = paymentService;
    }

    public Object on(@Payload Object command) {
        if (command instanceof ProcessPayment processPaymentCommand) {
            return on(processPaymentCommand);
        }
        if (command instanceof CancelPayment cancelPaymentCommand) {
            return on(cancelPaymentCommand);
        }
        return null;
    }

    public Object on(@Payload ProcessPayment command) {
        if (_paymentService.payOrder(command)) {
            return new PaymentCompleted(command.getOrderId(), command.getOrderLines());
        }
        return new PaymentCancelled(command.getOrderId());
    }

    public Object on(@Payload CancelPayment command) {
        _paymentService.undoPayment(command);
        return new PaymentCancelled(command.getOrderId());
    }
}
