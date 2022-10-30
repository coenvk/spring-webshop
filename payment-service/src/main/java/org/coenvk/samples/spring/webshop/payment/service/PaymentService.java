package org.coenvk.samples.spring.webshop.payment.service;

import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;
import org.coenvk.samples.spring.webshop.common.dto.command.CancelPayment;
import org.coenvk.samples.spring.webshop.common.dto.command.ProcessPayment;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.PaymentCompleted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@Slf4j
public class PaymentService {
    private final Sinks.Many<Object> _paymentSink;
    private final SecureRandom _secureRandom;

    @Autowired
    public PaymentService(
            Sinks.Many<Object> paymentSink,
            SecureRandom secureRandom
    ) {
        _paymentSink = paymentSink;
        _secureRandom = secureRandom;
    }

    public void undoPayment(CancelPayment cancelPayment) {
        var orderId = cancelPayment.getOrderId();
        log.info("Undoing processed payment for order {}", orderId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        log.info("Payment cancelled for order {}", orderId);
        _paymentSink.tryEmitNext(new PaymentCancelled(orderId));
    }

    public boolean payOrder(ProcessPayment processPayment) {
        var orderId = processPayment.getOrderId();
        log.info("Payment being processed for order {}", orderId);
        try {
            Thread.sleep(500);
        } catch (InterruptedException ignored) {
        }
        var randomInt = _secureRandom.nextInt(10);
        if (randomInt == 0) { // fail payment
            log.info("Payment failed for order {}", orderId);
            _paymentSink.tryEmitNext(new PaymentCancelled(orderId));
            return false;
        }
        log.info("Payment completed for order {}", orderId);
        _paymentSink.tryEmitNext(new PaymentCompleted(orderId, processPayment.getOrderLines()));
        return true;
    }
}
