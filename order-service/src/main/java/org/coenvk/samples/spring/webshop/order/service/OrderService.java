package org.coenvk.samples.spring.webshop.order.service;

import java.util.UUID;
import org.coenvk.samples.spring.toolkit.error.exceptions.ModelNotFound;
import org.coenvk.samples.spring.webshop.common.dto.OrderDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CancelOrder;
import org.coenvk.samples.spring.webshop.common.dto.command.CompleteOrder;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateOrder;
import org.coenvk.samples.spring.webshop.common.dto.enums.OrderStatus;
import org.coenvk.samples.spring.webshop.common.dto.event.OrderCancelled;
import org.coenvk.samples.spring.webshop.common.dto.event.OrderCompleted;
import org.coenvk.samples.spring.webshop.common.dto.event.OrderCreated;
import org.coenvk.samples.spring.webshop.order.mapper.DomainMapper;
import org.coenvk.samples.spring.webshop.order.model.Order;
import org.coenvk.samples.spring.webshop.order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderService {
    private final OrderRepository _orderRepository;
    private final DomainMapper _mapper;
    private final Sinks.Many<Object> _orderSink;
    private final Sinks.Many<Object> _sagaSink;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            DomainMapper mapper,
            Sinks.Many<Object> orderSink,
            Sinks.Many<Object> sagaSink
    ) {
        _orderRepository = orderRepository;
        _mapper = mapper;
        _orderSink = orderSink;
        _sagaSink = sagaSink;
    }

    public OrderDto createOrder(CreateOrder command) {
        var order = _mapper.map(command);
        order.setStatus(OrderStatus.AwaitingValidation);
        order.getOrderLines().forEach(orderLine -> orderLine.setOrder(order));
        var savedOrder = _orderRepository.save(order);
        var event = new OrderCreated(savedOrder.getId(), command.getOrderLines());
        _orderSink.tryEmitNext(event);
        _sagaSink.tryEmitNext(event); // start saga
        return _mapper.map(savedOrder);
    }

    public OrderDto cancelOrder(CancelOrder command) {
        final var orderId = command.getOrderId();
        var order = _orderRepository.findById(orderId)
                .orElseThrow(() -> new ModelNotFound(Order.class, orderId));
        order.setStatus(OrderStatus.Cancelled);
        var savedOrder = _orderRepository.save(order);
        _orderSink.tryEmitNext(new OrderCancelled(orderId));
        return _mapper.map(savedOrder);
    }

    public OrderDto completeOrder(CompleteOrder command) {
        final var orderId = command.getOrderId();
        var order = _orderRepository.findById(orderId)
                .orElseThrow(() -> new ModelNotFound(Order.class, orderId));
        order.setStatus(OrderStatus.Completed);
        var savedOrder = _orderRepository.save(order);
        _orderSink.tryEmitNext(new OrderCompleted(orderId));
        return _mapper.map(savedOrder);
    }

    public OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {
        var order = _orderRepository.findById(orderId)
                .orElseThrow(() -> new ModelNotFound(Order.class, orderId));
        order.setStatus(orderStatus);
        var savedOrder = _orderRepository.save(order);
        return _mapper.map(savedOrder);
    }
}
