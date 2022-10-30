package org.coenvk.samples.spring.webshop.order.controller.command;

import javax.validation.Valid;
import org.coenvk.samples.spring.webshop.common.dto.OrderDto;
import org.coenvk.samples.spring.webshop.common.dto.command.CreateOrder;
import org.coenvk.samples.spring.webshop.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderCommandController {
    private final OrderService _service;

    @Autowired
    public OrderCommandController(OrderService service) {
        _service = service;
    }

    @PostMapping("order")
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody CreateOrder command) {
        return ResponseEntity.ok(_service.createOrder(command));
    }
}
